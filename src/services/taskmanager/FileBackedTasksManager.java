package services.taskmanager;

import exception.ManagerSaveException;
import model.enums.Status;
import models.Epic;
import models.Subtask;
import models.Task;
import services.history.HistoryManager;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;


public class FileBackedTasksManager extends InMemoryTasksManager {

    File bootFile;
    public FileBackedTasksManager(File file) {
        this.bootFile = file;
    }

    public static void main(String[] args) {
        FileBackedTasksManager manager = new FileBackedTasksManager(new File("resources/file.csv"));
        Task task1 = new Task("Task №1", "Задача №1", Status.NEW);
        Task task2 = new Task("Task №2", "Задача №2", Status.NEW);
        Task task3 = new Task("Task №3", "Задача №3", Status.NEW);
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.addNewTask(task3);
        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getTaskById(3);
        System.out.println(manager.getHistory());
        Epic epic1 = new Epic("Epic №1", "Большая задача 1", Status.NEW);
        Epic epic2 = new Epic("Epic №2", "Большая задача 2", Status.NEW);
        manager.addNewEpic(epic1);
        manager.addNewEpic(epic2);
        manager.getEpicById(3);
        Subtask subtask1 = new Subtask("Subtask №1", "Подзадача №1", Status.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("Subtask №2", "Подзадача №2", Status.NEW, epic2.getId());
        manager.addNewSubtask(subtask1);
        manager.addNewSubtask(subtask2);
        manager.getSubtaskById(4);
        manager = FileBackedTasksManager.loadFromFile(new File("resources/file.csv"));
        System.out.println(manager.getHistory());
    }

    private void save() {
        String title = "id,type,name,status,description,epic";
        try {
            PrintWriter writer = new PrintWriter(bootFile);
            writer.println(title);
            for (Task task : tasks.values()) {
                String s = task.toString();
                writer.println(s);
            }
            for (Epic epic : epics.values()) {
                String s = epic.toString();
                writer.println(s);
            }
            for (Subtask subtask : subtasks.values()) {
                String s = subtask.toString();
                writer.println(s);
            }
            writer.println(historyToString(historyManager));
            writer.close();
        } catch (IOException e) {
            throw new ManagerSaveException("Файл не сохранен!" + e.getMessage());
        }
    }
    public static FileBackedTasksManager loadFromFile(File file) {
        try {
            FileBackedTasksManager loadedManager = new FileBackedTasksManager(file);
            String contentWithTitle = Files.readString(Path.of(file.getPath()));
            String content = contentWithTitle.substring(38);
            String[] line = content.split("\r\n");
            for (int i = 0; i < line.length - 2; i++) {
                String str = line[i];
                String[] substring = str.split(",");
                switch (substring[1]) {
                    case "TASK":
                        Task task = loadedManager.fromString(str);
                        assert task != null;
                        loadedManager.tasks.put(task.getId(), task);
                        break;
                    case "EPIC":
                        Epic epic = (Epic) loadedManager.fromString(str);
                        assert epic != null;
                        loadedManager.epics.put(epic.getId(), epic);
                        break;
                    case "SUBTASK":
                        Subtask subtask = (Subtask) loadedManager.fromString(str);
                        assert subtask != null;
                        loadedManager.subtasks.put(subtask.getId(), subtask);
                        break;
                }
            }
            return loadedManager;
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки!" + e.getMessage());
        }
    }
    public Task fromString(String string) {
        String[] parts = string.split(",");
        switch (parts[1]) {
            case "TASK":
                Task task = new Task(parts[2], parts[4], Status.valueOf(parts[3]));
                task.setId(parseInt(parts[0]));
                return task;
            case "EPIC":
                Epic epic = new Epic(parts[2], parts[4], Status.valueOf(parts[3]));
                epic.setId(parseInt(parts[0]));
                return epic;
            case "SUBTASK":
                Subtask subtask = new Subtask(parts[2], parts[4], Status.valueOf(parts[3]), parseInt(parts[5]));
                subtask.setId(parseInt(parts[0]));
                return subtask;
        }
        return null;
    }
    /*
    Вот что про в ТЗ написано про методы и параметы.

    Напишите статические методы static String historyToString(HistoryManager manager)
    и static List<Integer> historyFromString(String value)
    для сохранения и восстановления менеджера истории из CSV.
     */
    static String historyToString(HistoryManager manager) { //
        StringBuilder sb = new StringBuilder();
        for(Task task: manager.getHistory()){
            sb.append(task.getId());
            sb.append(",");
        }
        if(!sb.isEmpty()) {
            sb.setLength(sb.length() - 1);
        }
        return String.valueOf(sb);
    }
     static List<Integer> historyFromString(String str) {
        String[] history = str.split(",");
        List<Integer> listResult = new ArrayList<>();
        for (String item: history) {
            //if (item.matches("\\d"))
                listResult.add(parseInt(item));
        }
        return listResult;
    }
    //========================== override ================================================

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = super.getSubtaskById(id);
        save();
        return subtask;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public Task addNewTask(Task task) {
        Task t = super.addNewTask(task);
        save();
        return t;
    }

    @Override
    public Epic addNewEpic(Epic epic) {
        Epic e = super.addNewEpic(epic);
        save();
        return e;
    }

    @Override
    public Integer addNewSubtask(Subtask subtask) {
        if (subtask != null) {
            int s = super.addNewSubtask(subtask);
            save();
            return s;
        } else {
            return null;
        }
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteSubtaskById(int id) {
        super.deleteSubtaskById(id);
        save();
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public void deleteSubtasks() {
        super.deleteSubtasks();
        save();
    }
}
