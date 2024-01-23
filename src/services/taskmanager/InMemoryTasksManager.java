package services.taskmanager;

import models.Epic;
import models.Subtask;
import models.Task;
import model.enums.Status;
import services.history.HistoryManager;
import util.Managers;

import java.time.LocalDateTime;
import java.util.*;
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
    TreeSet<Task> taskTreeSet = new TreeSet<>();

    public TreeSet<Task> getPrioritizedTasks() {
        return taskTreeSet;
    }
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
    public int addNewTask(Task task) {
        if (task.getId() == null) {
            return 0;
        }
        if (timeValidation(task)) {
            tasks.put(task.getId(), task);
            taskTreeSet.add(task);
            return task.getId();
        }
        return -1;
    }

    @Override
    public int addNewEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    /*
    Метод добавления новой задачи
     */
    @Override
    public int addNewSubtask(Subtask subtask) {
        if (subtask.getId() == null) {
            return 0;
        }
        if (timeValidation(subtask)) {
            int epicID = subtask.getEpicId();
            Epic epic = epics.get(epicID);
            if (epics.containsKey(epicID)) {
                subtasks.put(subtask.getId(), subtask);
                epic.addSubtaskId(subtask.getId());
                taskTreeSet.add(subtask);
                updateEpicStatus(epic.getId());
                updateEpicTimeParams(epic.getId());
                updateEpic(epic);
                return subtask.getId();
            }
        }
        return -1;
    }

    /*
    Обновить статус Epic
     */
    @Override
    public void updateEpicStatus(int epicID) {
        Epic epic = epics.get(epicID);// это из эпика вытащили нужный объект эпика который приходит в параметрах
        HashSet<Status> setStatusEpic = new HashSet<>();
        if (epic.getSubtaskIds() == null || epic.getSubtaskIds().isEmpty()) { // проверили если список пуст то установили статус NEW
            epic.setStatus(Status.New);
        } else {
            for (Integer i : epic.getSubtaskIds()) {
                Subtask subtask = subtasks.get(i);
                setStatusEpic.add(subtask.getStatus());
            }
            if (setStatusEpic.size() == 1 && setStatusEpic.contains(Status.New)) {
                epic.setStatus(Status.New);
            } else if (setStatusEpic.size() == 1 && setStatusEpic.contains(Status.Done)) {
                epic.setStatus(Status.Done);
            } else {
                epic.setStatus(Status.In_progress);
            }

        }
    }

    /*
    Обновить задачи
     */
    @Override
    public void updateTask(Task task) {
        taskTreeSet.remove(task);
        if (tasks.containsValue(task)) {
            tasks.put(task.getId(), task);
        }
        taskTreeSet.add(task);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        taskTreeSet.remove(subtask);
        if (subtasks.containsValue(subtask)) {
            subtasks.put(subtask.getId(), subtask);
        }
        taskTreeSet.add(subtask);
        updateEpicStatus(epics.get(subtask.getEpicId()).getId());
        updateEpicTimeParams(epics.get(subtask.getEpicId()).getId());
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsValue(epic)) {
            updateEpicStatus(epic.getId());
            epics.put(epic.getId(), epic);
        }
    }

    /*
    Удалить задачу по id
     */
    @Override
    public void deleteTaskById(int id) {
        if (taskTreeSet.contains(tasks.get(id))) {
            taskTreeSet.remove(tasks.get(id));
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
                taskTreeSet.remove(subtasks.get(subtaskId));
                subtasks.remove(subtaskId);
                historyManager.remove(id);
            }
            epics.remove(epic.getId());
            historyManager.remove(id);
            System.out.println("Epic удален!");
        } else {
            System.out.println("Нет такой задачи!");
        }
    }

    @Override
    public void deleteSubtaskById(int id) {
        historyManager.remove(id);
        if (subtasks.containsKey(id)) {
            epics.get(subtasks.get(id).getEpicId()).getSubtaskIds().remove((Integer) id);
            subtasks.remove(id);
        }
    }

    /*
    Очистить HashMap, удалить все задачи
     */
    @Override
    public void deleteTasks() {
        for (Task task : tasks.values()) {
            historyManager.remove(task.getId());
            taskTreeSet.remove(task);
        }
        tasks.clear();
    }

    @Override
    public void deleteEpics() {
        for (Epic epic : epics.values()) {
            historyManager.remove(epic.getId());
        }
        for (Subtask value : subtasks.values()) {
            historyManager.remove(value.getId());
            taskTreeSet.remove(value);
        }
        subtasks.clear();
        epics.clear();
    }

    @Override
    public void deleteSubtasks() {
        for (Epic value : epics.values()) {
            value.deleteSubtask();
            updateEpicStatus(value.getId());
            updateEpicTimeParams(value.getId());
        }
        for (Subtask value : subtasks.values()) {
            historyManager.remove(value.getId());
            taskTreeSet.remove(value);
        }
        subtasks.clear();
    }

    @Override
    public ArrayList<Subtask> getSubtaskOfEpic(Epic epic) {
        ArrayList<Subtask> list = new ArrayList<>();
        for (Integer subtaskId : epic.getSubtaskIds()) {
            list.add(subtasks.get(subtaskId));
        }
        return list;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
    private boolean timeValidation(Task task1) {
        for (Task task2 : taskTreeSet) {
            if (!(task1.getStartTime().isAfter(task2.getStartTime().plusMinutes(task2.getDuration()))
                    || task1.getStartTime().plusMinutes(task1.getDuration()).isBefore(task2.getStartTime()))) {
                if (!Objects.equals(task1.getId(), task2.getId())) {
                    return false;
                }
            }
        }
        return true;
    }
    protected void updateEpicTimeParams(int epicID) {
        int epicDuration = 0;
        Epic epic = epics.get(epicID);
        LocalDateTime startTime = LocalDateTime.of(2200,1,1,0,0);
        for (Integer subtaskId : epic.getSubtaskIds()) {
            epicDuration += subtasks.get(subtaskId).getDuration();
            if (subtasks.get(subtaskId).getStartTime().isBefore(startTime)) {
                startTime = subtasks.get(subtaskId).getStartTime();
            }
        }
        epic.setDuration(epicDuration);
        epic.setStartTime(startTime);
        epic.setEndTime(startTime.plusMinutes(epicDuration));
    }
}