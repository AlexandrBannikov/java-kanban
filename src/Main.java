public class Main {

    public static void main(String[] args) {

        TaskManager manager = new TaskManager();

        // Создание
        Task task1 = new Task("Task #1, финальное задание Яндекс практикум", "Task1 description", "NEW");
        Task task2 = new Task("Task #2", "Task2 description", "NEW");
        final int taskId1 = manager.addNewTask(task1);
        final int taskId2 = manager.addNewTask(task2);

        Epic epic1 = new Epic("Epic #1", "Epic1 description","NEW");
        Epic epic2 = new Epic("Epic #2", "Epic2 description","NEW");
        final int epicId1 = manager.addNewEpic(epic1);
        final int epicId2 = manager.addNewEpic(epic2);

        Subtask subtask1 = new Subtask("Subtask #1 - 1", "Subtask 1 description", "NEW", epicId1);
        Subtask subtask2 = new Subtask("Subtask #2 - 1", "Subtask 2 description", "NEW", epicId1);
        Subtask subtask3 = new Subtask("Subtask #3 - 2", "Subtask 3 description", "NEW", epicId2);

        final Integer subtaskId1 = manager.addNewSubtask(subtask1);
        final Integer subtaskId2 = manager.addNewSubtask(subtask2);
        final Integer subtaskId3 = manager.addNewSubtask(subtask3);

        for (Task managerTask : manager.getTasks()) {
            System.out.println( "manager " + managerTask);
        }

        for (Epic epic : manager.getEpics()) {
            System.out.println("Epic " + epic);
        }

        for (Epic epic : manager.getEpics()) {
            System.out.println("Epic " + epic.getDescription());
        }

        for (Epic epic : manager.getEpics()) {
            System.out.println("Epic " + epic.getId());
        }

        for (Subtask subtask : manager.getSubtasks()) {
            System.out.println(subtask);
        }

        // Обновление
        final Task task = manager.getTask(taskId2);
        task.setStatus("DONE");
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
