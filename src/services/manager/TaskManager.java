package services.manager;

import business.Epic;
import business.Subtask;
import business.Task;
import models.enums.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int generatorID = 0;
    /*
    Получить список всех задач, все задачи.
     */
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }
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
    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    /*
    Добавить задачу в мапу
     */
    public int addNewTask(Task task) {
        int id = ++generatorID;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    public int addNewEpic(Epic epic) {
        int id = ++generatorID;
        epic.setId(id);
        epics.put(id, epic);
        return id;
    }

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
    Обновить статус business.Epic
     */
    public void updateEpicStatus(int epicID) {
        Epic epic = epics.get(epicID);// это из эпика вытащили нужный объект эпика который приходит в параметрах
        HashSet<Status> setStatusEpic = new HashSet<>();
        if (epic.getSubtaskIds() == null || epic.getSubtaskIds().isEmpty()) { // проверили если список пуст то установили статус NEW
            epic.setStatus(Status.NEW);
            return;
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
    public void updateTask(Task task) {
        int id = task.getId();
        Task saveTask = tasks.get(id);
        if (saveTask == null) {
            return;
        }
        tasks.put(id, task);
    }
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

    public void updateEpic(Epic epic) {
        Epic saveEpic = epics.get(epic.getId());
        saveEpic.setName(epic.getName());
        saveEpic.setDescription(epic.getDescription());
    }

    /*
    Удалить задачу по id
     */
    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public void deleteEpic(int id) {
        Epic epic = epics.remove(id);
        for (Integer subtaskId : epic.getSubtaskIds()) {
            subtasks.remove(subtaskId);
        }
    }

    public void deleteSubtask(int id) {
        subtasks.remove(id);
    }

    /*
    Очистить мапу, удалить все задачи
     */

    public void deleteTasks() {
        tasks.clear();
    }
    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void deleteSubtasks() {
        subtasks.clear();
    }
}
