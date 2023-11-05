package main;

import models.Epic;
import models.Subtask;
import models.Task;
import model.enums.Status;
import services.taskmanager.TaskManager;
import util.Managers;

public class Main {
    /*
    Доброго времени суток! А как можно с вами связаться, вы есть в пачке? Просто бывает иногда не совсем понятно что
    надо сделать... Вот например с логами, вроде бы есть и создание объекта и удаление, или я что-то не так делаю...
    И по методу получения задачи по id, вроде бы и понятно что надо сделать, сделал метод, но меня самого не устраивает
    результат, возвращение null, если задачи такой нет по id. Поэтому и хочется напрямую задать вам вопрос.
    Спасибо.
     */

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        Task task1 = new Task(1,"Test Task #1", "Финальное задание ТЗ №4", Status.NEW);
        Task task2 = new Task(2,"Test Task #2", "Сдать финальное задание ТЗ №4", Status.NEW);

        int idTask1 = manager.addNewTask(task1);
        int idTask2 = manager.addNewTask(task2);
        String desc = task2.getDescription();
        System.out.println(desc);
        System.out.println(idTask2 + " IDtask");

        Epic epic1 = new Epic("Test Epic #1", "Финальное задание ТЗ №4", Status.NEW);
        Epic epic2 = new Epic("Test Epic #2", "Сдать финальное задание ТЗ №4", Status.NEW);
        Epic epic3 = new Epic("Test Epic #3", "Провести работу над ошибками ТЗ №4", Status.NEW);
        int idEpic1 = manager.addNewEpic(epic1);
        int idEpic2 = manager.addNewEpic(epic2);
        int idEpic3 = manager.addNewEpic(epic3);


        Subtask subtask1 = new Subtask("Test Subtask #1", "Разбить задачу", Status.NEW,3);
        Subtask subtask2 = new Subtask("Test Subtask #2", "Работать с мелкими задачами", Status.NEW, 4);
        Subtask subtask3 = new Subtask("Test Subtask #3", "Работать с задачами", Status.NEW, 5);
        int idSubtask1 = manager.addNewSubtask(subtask1);
        int idSubtask2 = manager.addNewSubtask(subtask2);
        int idSubtask3 = manager.addNewSubtask(subtask3);

        Task taskUpdate = manager.getTaskById(idTask1);
        taskUpdate.setStatus(Status.IN_PROGRESS);
        System.out.println(taskUpdate.getStatus());
        manager.updateTask(task1);
        manager.getTaskById(idTask1);
        manager.getEpicById(idEpic1);
        manager.getEpicById(idEpic2);
        manager.getSubtaskById(idSubtask1);
        manager.getSubtaskById(idSubtask2);
        manager.getSubtaskById(idSubtask3);
        manager.getEpicById(idEpic1);
        manager.getEpicById(idEpic1);
        manager.getEpicById(idEpic1);
        manager.getEpicById(idEpic1);
        manager.getEpicById(idEpic3);
        manager.getTaskById(idTask2);
        System.out.println("Обновляем ...");
        Subtask updateSub = manager.getSubtaskById(idSubtask3);
        updateSub.setStatus(Status.DONE);
        System.out.println(updateSub.getStatus());


        for (Task task : manager.getHistory()) {
            System.out.println("История просмотров " + task + " " + manager.getHistory().size());
        }
        manager.deleteTaskById(3);

        System.out.println();
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }
        System.out.println();
        for (Epic epic : manager.getEpics()) {
            System.out.println(epic);
        }
        System.out.println();
        for (Subtask subtask : manager.getSubtasks()) {
            System.out.println(subtask);
        }
        manager.deleteEpics();
        System.out.println(manager.getEpicById(5));
        System.out.println(manager.getTaskById(1));

    }
}
