package main;

import business.Epic;
import business.Subtask;
import business.Task;
import models.enums.Status;
import services.manager.TaskManager;
import util.Managers;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        Task task1 = new Task(1,"Test Task #1", "Финальное задание ТЗ №4", Status.NEW);
        Task task2 = new Task(1,"Test Task #2", "Сдать финальное задание ТЗ №4", Status.NEW);

        int idTask1 = manager.addNewTask(task1);
        int idTask2 = manager.addNewTask(task2);
        String desc = task2.getDescription();
        System.out.println(desc);
        System.out.println(idTask2);

        Epic epic1 = new Epic("Test Epic #1", "Финальное задание ТЗ №4", Status.NEW);
        Epic epic2 = new Epic("Test Epic #2", "Сдать финальное задание ТЗ №4", Status.NEW);
        Epic epic3 = new Epic("Test Epic #3", "Провести работу над ошибками ТЗ №4", Status.NEW);
        int idEpic1 = manager.addNewEpic(epic1);
        int idEpic2 = manager.addNewEpic(epic2);
        int idEpic3 = manager.addNewEpic(epic3);


        Subtask subtask1 = new Subtask("Test Subtask #1", "Разбить задачу", Status.NEW,3);
        Subtask subtask2 = new Subtask("Test Subtask #2", "Работать с мелкими задачами", Status.NEW, 4);
        Subtask subtask3 = new Subtask("Test Subtask #2", "Работать с задачами", Status.NEW, 5);
        int idSubtask1 = manager.addNewSubtask(subtask1);
        int idSubtask2 = manager.addNewSubtask(subtask2);
        int idSubtask3 = manager.addNewSubtask(subtask3);

        Task taskUpdate = manager.getTask(idTask1);
        taskUpdate.setStatus(Status.IN_PROGRESS);
        System.out.println(taskUpdate.getStatus());
        manager.updateTask(task1);
        manager.getTask(idTask1);
        manager.getEpic(idEpic1);
        manager.getEpic(idEpic2);
        manager.getSubtask(idSubtask1);
        manager.getSubtask(idSubtask2);
        manager.getSubtask(idSubtask3);
        manager.getEpic(idEpic1);
        manager.getEpic(idEpic1);
        manager.getEpic(idEpic1);
        manager.getEpic(idEpic1);
        manager.getEpic(idEpic3);
        System.out.println("Обновляем ...");
        Subtask updateSub = manager.getSubtask(idSubtask3);
        updateSub.setStatus(Status.DONE);
        System.out.println(updateSub.getStatus());


        for (Task task : manager.getHistory()) {
            System.out.println("История просмотров " + task);
        }
        manager.deleteTaskById(3);
        manager.deleteTaskById(7);
    }
}
