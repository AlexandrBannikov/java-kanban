import business.Epic;
import business.Subtask;
import business.Task;
import models.enums.Status;
import services.manager.TaskManager;

public class Main {

    public static void main(String[] args) {

        TaskManager manager = new TaskManager();

        // Создание
        Task task1 = new Task("business.Task #1, финальное задание Яндекс практикум", "Task1 description", Status.NEW);
        Task task2 = new Task("business.Task #2", "Task2 description", Status.NEW);
        int taskId1 = manager.addNewTask(task1);
        int taskId2 = manager.addNewTask(task2);

        Epic epic1 = new Epic("business.Epic #1", "Epic1 description", Status.NEW);
        Epic epic2 = new Epic("business.Epic #2", "Epic2 description",Status.NEW);
        int epicId1 = manager.addNewEpic(epic1);
        int epicId2 = manager.addNewEpic(epic2);

        Subtask subtask1 = new Subtask("business.Subtask #1 - 1", "business.Subtask 1 description", Status.NEW, epicId1);
        Subtask subtask2 = new Subtask("business.Subtask #2 - 1", "business.Subtask 2 description", Status.NEW, epicId1);
        Subtask subtask3 = new Subtask("business.Subtask #3 - 2", "business.Subtask 3 description", Status.NEW, epicId2);

        Integer subtaskId1 = manager.addNewSubtask(subtask1);
        Integer subtaskId2 = manager.addNewSubtask(subtask2);
        Integer subtaskId3 = manager.addNewSubtask(subtask3);

        for (Task managerTask : manager.getTasks()) {
            System.out.println( "manager " + managerTask);
        }

        for (Epic epic : manager.getEpics()) {
            System.out.println("business.Epic " + epic);
        }

        for (Epic epic : manager.getEpics()) {
            System.out.println("business.Epic " + epic.getDescription());
        }

        for (Epic epic : manager.getEpics()) {
            System.out.println("business.Epic " + epic.getId());
        }

        for (Subtask subtask : manager.getSubtasks()) {
            System.out.println(subtask);
        }

        // Обновление
        Task task = manager.getTask(taskId2);
        task.setStatus(Status.DONE);
        manager.updateTask(task);
        System.out.println("CHANGE STATUS: Task2 NEW -> DONE");
        System.out.println("Задачи");
        for (Task managerTask : manager.getTasks()) {
            System.out.println( "manager " + managerTask);
        }

        for (Task id : manager.getTasks()) {
            System.out.println("ID " + id.getId());
        }

        // удаление по id
        manager.deleteTask(1);
        for (Task taskId : manager.getTasks()) {
            System.out.println("После удаления " + taskId);
        }
        // удаление всех задач
        manager.deleteTasks();
    }
}
