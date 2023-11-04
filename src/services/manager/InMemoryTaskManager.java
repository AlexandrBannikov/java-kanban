package services.manager;

import business.Epic;
import business.Subtask;
import business.Task;
import models.enums.Status;
import util.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();// Параметрический полиморфизм
    //способность принимать любого вида объекты и использовать их
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private int generatorID = 0;

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
    public Task getTask(int id) {
        final Task task = tasks.get(id);
        historyManager.addTask(task);
        return task;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        historyManager.addTask(subtask);
        return subtask;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        historyManager.addTask(epic);
        return epic;
    }

    /*
    Добавить задачу в HashMap
     */
    @Override
    public int addNewTask(Task task) {
        int id = ++generatorID;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    @Override
    public int addNewEpic(Epic epic) {
        int id = ++generatorID;
        epic.setId(id);
        epics.put(id, epic);
        return id;
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
        int id = ++generatorID;
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
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            System.out.println("task delete...");
        } else if (epics.containsKey(id)) {
            List<Integer> stList = epics.get(id).getSubtaskIds();
            if (!stList.isEmpty()) {
                for (Integer i : stList) {
                    subtasks.remove(i);
                    System.out.println("epic delete...");
                }
            }
        } else if (subtasks.containsKey(id)) {
            int eId = subtasks.get(id).getEpicId();
            subtasks.remove(id);
            System.out.println("subtask delete...");
            epics.get(eId).removeSubtaskId(id);
            updateEpicStatus(eId);
        }
    }

    /*
    Очистить HashMap, удалить все задачи
     */
    @Override
    public void deleteTasks() {
        tasks.clear();
    }

    @Override
    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteSubtasks() {
        subtasks.clear();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}