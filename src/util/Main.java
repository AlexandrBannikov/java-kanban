package util;

import model.enums.Status;
import models.Epic;
import models.Subtask;
import models.Task;
import services.taskmanager.FileBackedTasksManager;

import java.io.File;

public class Main {
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
}
