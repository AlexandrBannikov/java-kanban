package services.taskmanager;

import models.Epic;
import models.Subtask;
import models.Task;
import model.enums.Status;
import services.history.HistoryManager;
import util.Managers;

import java.util.*;
/*
Добавьте метод getHistory() в  TaskManager и реализуйте его — он должен возвращать последние 10 просмотренных задач.
Просмотром будем считаться вызов у менеджера методов получения задачи по идентификатору  — getTask(),
 getSubtask() и getEpic(). От повторных просмотров избавляться не нужно.
 */
/*
    Динамический полиморфизм это переопределение метода
    Статический полиморфизм это изменение параметров метода - перегрузка метода
    Классический полиморфизм это способность объекта принимать другой объект или объект другого подтипа.
 */

public class InMemoryTasksManager implements TasksManager {
    protected final HashMap<Integer, Task> tasks = new HashMap<>();// Параметрический полиморфизм
    //способность принимать любого вида объекты и использовать их
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    final HistoryManager historyManager = Managers.getDefaultHistory();

    /*
    Получить список всех задач, Task, Epic, Subtask.
    Возвращаем список значений задач.
     */
    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(int epicID) {
        ArrayList<Subtask> tasks = new ArrayList<>();
        Epic epic = epics.get(epicID);
        if (epic == null) {
            System.out.println("Не найдена задача!");
        }
        assert epic != null;
        for (int id : epic.getSubtaskIds()) {
            tasks.add(subtasks.get(id));
        }
        return tasks;
    }

    /*
    Получить задачу по id
     */
    @Override
    public Task getTaskById(int id) {
        if (tasks.containsKey(id)) {
            historyManager.addTask(tasks.get(id));
            return tasks.get(id);
        } else return null;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        if (subtasks.containsKey(id)) {
            historyManager.addTask(subtasks.get(id));
            return subtasks.get(id);
        } else return null;
    }

    @Override
    public Epic getEpicById(int id) {
        if (epics.containsKey(id)) {
            historyManager.addTask(epics.get(id));
            return epics.get(id);
        } else return null;
    }

    /*
    Добавить задачу в HashMap
     */
    @Override
    public Task addNewTask(Task task) {
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Epic addNewEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        return epic;
    }

    /*
    Метод добавления новой подзадачи
     */
    @Override
    public Integer addNewSubtask(Subtask subtask) {
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return null;
        }
        int id = subtask.getId();
        subtask.setId(id);
        subtasks.put(id, subtask);
        epic.addSubtaskId(subtask.getId());
        updateEpicStatus(epicId);
        return id;
    }

    /*
    Обновить статус Epic
     */
    @Override
    public void updateEpicStatus(int epicID) {
        Epic epic = epics.get(epicID);// это из эпика вытащили нужный объект эпика который приходит в параметрах
        HashSet<Status> setStatusEpic = new HashSet<>();
        if (epic.getSubtaskIds() == null || epic.getSubtaskIds().isEmpty()) { // проверили если список пуст то установили статус NEW
            epic.setStatus(Status.NEW);
        } else {
            for (Integer i : epic.getSubtaskIds()) {
                Subtask subtask = subtasks.get(i);
                setStatusEpic.add(subtask.getStatus());
            }
            if (setStatusEpic.size() == 1 && setStatusEpic.contains(Status.NEW)) {
                epic.setStatus(Status.NEW);
            } else if (setStatusEpic.size() == 1 && setStatusEpic.contains(Status.DONE)) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }

        }
    }

    /*
    Обновить задачи
     */
    @Override
    public void updateTask(Task task) {
        int id = task.getId();
        Task saveTask = tasks.get(id);
        if (saveTask == null) {
            return;
        }
        tasks.put(id, task);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        int id = subtask.getId();
        int epicID = subtask.getEpicId();
        Subtask saveSubtask = subtasks.get(id);
        if (saveSubtask == null) {
            return;
        }
        Epic epic = epics.get(epicID);
        if (epic == null) {
            return;
        }
        subtasks.put(id, subtask);
        updateEpicStatus(epicID);
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic saveEpic = epics.get(epic.getId());
        saveEpic.setName(epic.getName());
        saveEpic.setDescription(epic.getDescription());
    }

    /*
    Удалить задачу по id
     */
    @Override
    public void deleteTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            tasks.remove(id);
            historyManager.remove(id);
            System.out.println("Задача удалена!");
        } else {
            System.out.println("Нет такой задачи!");
        }
    }

    @Override
    public void deleteEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
                historyManager.remove(id);
            }
            historyManager.remove(id);
            System.out.println("Epic удален!");
        } else {
            System.out.println("Нет такой задачи!");
        }
    }

    @Override
    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            int epicID = subtask.getEpicId();
            Epic epic = epics.get(epicID);
            if (epic != null) {
                epic.getSubtaskIds().remove(Integer.valueOf(id));
            }
        }
        subtasks.remove(id);
        historyManager.remove(id);
    }

    /*
    Очистить HashMap, удалить все задачи
     */
    @Override
    public void deleteTasks() {
        for (Task task : tasks.values()) {
            historyManager.remove(task.getId());
        }
        tasks.clear();
    }

    @Override
    public void deleteEpics() {
        for (Epic epic : epics.values()) {
            historyManager.remove(epic.getId());
        }
        subtasks.clear();
        epics.clear();
    }

    @Override
    public void deleteSubtasks() {
        for (Subtask subtask : subtasks.values()) {
            historyManager.remove(subtask.getId());
        }
        subtasks.clear();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}