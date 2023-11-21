package main;

import model.enums.Status;
import models.Epic;
import models.Subtask;
import models.Task;
import services.history.HistoryManager;
import services.taskmanager.TaskManager;
import util.Managers;

public class Main {
    public static void main(String[] args) {

        TaskManager manager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();



        manager.addNewTask(new Task("ТЗ № 5","Теоретические занятия",Status.NEW));
        manager.addNewTask(new Task("ТЗ № 5","Практические занятия",Status.NEW));

        manager.addNewEpic(new Epic("Основная задача","Пройти теорию 5 спринта",Status.NEW));
        manager.addNewEpic(new Epic("Основная задача","Сдать ТЗ 5 спринта",Status.NEW));

        manager.addNewSubtask(new Subtask("Подзадача №1", "Изучить весь материал",Status.NEW, 3));
        manager.addNewSubtask(new Subtask("Подзадача №2", "Сделать все задания",Status.NEW, 3));
        manager.addNewSubtask(new Subtask("Подзадача №3", "Прочитать доп.литературу",Status.NEW, 3));
        manager.addNewSubtask(new Subtask("Подзадача №1", "Разбить ТЗ на задачи",Status.NEW, 4));

        manager.getTaskById(1);
        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getTaskById(2);

        manager.getEpicById(3);
        manager.getEpicById(4);

        manager.getSubtaskById(5);
        manager.getSubtaskById(6);
        manager.getSubtaskById(7);

        manager.getTaskById(1);
        manager.getTaskById(1);
        manager.getTaskById(2);

        manager.getEpicById(3);
        manager.getEpicById(3);
        manager.getEpicById(3);
        manager.getEpicById(4);

        System.out.println("История просмотра");
        manager.getTasks();
        manager.getEpics();
        manager.getSubtasks();

        System.out.println(historyManager.getHistory());
    }
}
