package test;

import model.enums.Status;
import models.Epic;
import models.Subtask;
import models.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.taskmanager.TasksManager;

import java.util.ArrayList;
import java.util.List;

public abstract class TaskManagerTest <T extends TasksManager> {
    public abstract T createNewManager();
    public static TasksManager taskManager;

    @BeforeEach
    public void beforeEach(){
        taskManager = createNewManager();
    }

    @Test
    public void getTasksWithEmptyTaskList(){
        Assertions.assertTrue(taskManager.getTasks().isEmpty());
    }

    @Test
    public void getTasksWithTasks(){
        Task task = new Task("t", "t", Status.NEW);
        Task task2 = new Task("e", "e", Status.NEW);
        taskManager.addNewTask(task);
        taskManager.addNewTask(task2);
        ArrayList<Task> list = new ArrayList<Task>();
        list.add(task);
        list.add(task2);
        Assertions.assertTrue(taskManager.getTasks().equals(list));
    }

    @Test
    public void deleteTasks(){
        Task task = new Task("t", "t", Status.NEW);
        Task task2 = new Task("t1", "t2", Status.NEW);
        taskManager.addNewTask(task);
        taskManager.addNewTask(task2);
        taskManager.deleteTasks();
        Assertions.assertTrue(taskManager.getTasks().isEmpty(), "Задачи не удалены");
    }

    @Test
    public void deleteSubtasksNoTasks(){
        taskManager.deleteSubtasks();;
        Assertions.assertTrue(taskManager.getSubtasks().isEmpty(), "Подзадачи не удалены");
    }

    @Test
    public void deleteSubtasksWithTasks(){
        Epic epic = new Epic("e", "e", Status.NEW);
        Subtask subtask1 = new Subtask("s1", "s1", Status.NEW, epic.getId());
        Subtask subtask2 = new Subtask("s2", "s2", Status.NEW, epic.getId());
        taskManager.addNewEpic(epic);
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.deleteSubtasks();
        Assertions.assertTrue(taskManager.getSubtasks().isEmpty(), "Подзадачи не удалены");
    }

    @Test
    public void deleteSubtasksNoSubtasks(){
        taskManager.deleteSubtasks();
        Assertions.assertTrue(taskManager.getSubtasks().isEmpty(), "Подзадачи не удалены");
    }

    @Test
    public void deleteEpics(){
        Epic epic = new Epic("e", "e", Status.NEW);
        Subtask subtask1 = new Subtask("s1", "s1", Status.NEW, epic.getId());
        Subtask subtask2 = new Subtask("s2", "s2", Status.NEW, epic.getId());
        taskManager.addNewEpic(epic);
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.deleteEpics();
        taskManager.deleteSubtasks();
        Assertions.assertTrue(taskManager.getSubtasks().isEmpty(), "Подзадачи не удалены");
        Assertions.assertTrue(taskManager.getEpics().isEmpty(), "Эпики не удалены");
    }

    @Test
    public void getTaskById(){
        Task task = new Task("t", "t", Status.NEW);
        taskManager.addNewTask(task);
        Task task2 = taskManager.getTaskById(task.getId());
        Assertions.assertNotNull(task2, "null вместо задачи");
        Assertions.assertEquals(task, task2, "Задачи не равны");
    }

    @Test
    public void getSubtaskById(){
        Epic epic = new Epic("e", "e", Status.NEW);
        Subtask subtask1 = new Subtask("s1", "s1", Status.NEW, epic.getId());
        taskManager.addNewEpic(epic);
        taskManager.addNewSubtask(subtask1);
        Subtask subtask2 = taskManager.getSubtaskById(subtask1.getId());
        Assertions.assertNotNull(subtask2, "null вместо задачи");
        Assertions.assertEquals(subtask1, subtask2, "Подзадачи не равны");
    }

    @Test
    public void getEpicById(){
        Epic epic = new Epic("e", "e", Status.NEW);
        taskManager.addNewEpic(epic);
        Epic epic2 = taskManager.getEpicById(epic.getId());
        Assertions.assertNotNull(epic2, "null вместо эпика");
        Assertions.assertEquals(epic, epic2, "Эпики не равны");
    }

    @Test
    public void getTaskByIdIfEmptyTaskList(){
        Task task = taskManager.getTaskById(1);
        Assertions.assertNull(task, "Задачи не удалены");
    }

    @Test
    public void getSubtaskByIdIfEmptySubtaskList(){
        Subtask subtask = taskManager.getSubtaskById(1);
        Assertions.assertNull(subtask, "Подзадачи не удалены");
    }

    @Test
    public void getEpicByIdIfEmptyEpicList(){
        Epic epic = taskManager.getEpicById(1);
        Assertions.assertNull(epic, "Эпики не удалены");
    }

    @Test
    public void getTaskByIdIfWrongId(){
        Task task = taskManager.getTaskById(-1);
        Assertions.assertNull(task, "Задачи не удалены");
    }

    @Test
    public void getSubtaskByIdIfWrongId(){
        Subtask subtask = taskManager.getSubtaskById(-1);
        Assertions.assertNull(subtask, "Подзадачи не удалены");
    }

    @Test
    public void getEpicByIdIfWrongId(){
        Epic epic = taskManager.getEpicById(-1);
        Assertions.assertNull(epic, "Эпики не удалены");
    }

    @Test
    public void updateTask(){
        Task task = new Task("t", "t", Status.NEW);
        taskManager.addNewTask(task);
        task.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(task);
        Assertions.assertTrue(taskManager.getTaskById(task.getId()).getStatus().equals(Status.IN_PROGRESS),
                "Задача не обновилась");
    }

    @Test
    public void updateSubtask(){
        Epic epic = new Epic("e", "e", Status.NEW);
        Subtask subtask1 = new Subtask("s1", "s1", Status.NEW, epic.getId());
        taskManager.addNewEpic(epic);
        taskManager.addNewSubtask(subtask1);
        subtask1.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(subtask1);
        Assertions.assertTrue(taskManager.getSubtaskById(subtask1.getId()).getStatus().equals(Status.IN_PROGRESS),
                "Подзадача не обновилась");
    }

    @Test
    public void updateEpic(){
        Epic epic = new Epic("e", "e", Status.NEW);
        Subtask subtask1 = new Subtask("s1", "s1", Status.NEW, epic.getId());
        taskManager.addNewEpic(epic);
        taskManager.addNewSubtask(subtask1);
        subtask1.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubtask(subtask1);
        Assertions.assertTrue(taskManager.getEpicById(epic.getId()).getStatus().equals(Status.IN_PROGRESS),
                "Эпик не обновился");
    }

    @Test
    public void addTask(){
        Task task = new Task("t", "t", Status.NEW);
        taskManager.addNewTask(task);
        Task task1 = taskManager.getTaskById(task.getId());
        Assertions.assertNotNull(task1, "Задача не найдена");
        Assertions.assertEquals(task, task1, "Задачи не совпадают");
    }

    @Test
    public void addSubtask(){
        Epic epic = new Epic("e", "e", Status.NEW);
        Subtask subtask = new Subtask("s1", "s1", Status.NEW, epic.getId());
        taskManager.addNewEpic(epic);
        taskManager.addNewSubtask(subtask);
        Subtask subtask1 = taskManager.getSubtaskById(subtask.getId());
        Assertions.assertNotNull(subtask, "Задача не найдена");
        Assertions.assertEquals(subtask, subtask1, "Задачи не совпадают");
    }

    @Test
    public void addEpic(){
        Epic epic = new Epic("e", "e", Status.NEW);
        Subtask subtask = new Subtask("s1", "s1", Status.NEW, epic.getId());
        taskManager.addNewEpic(epic);
        taskManager.addNewSubtask(subtask);
        Epic epic1 = taskManager.getEpicById(epic.getId());
        Assertions.assertNotNull(epic, "Задача не найдена");
        Assertions.assertEquals(epic, epic1, "Задачи не совпадают");
    }

    @Test
    public void removeTaskById(){
        Task task = new Task("t", "t", Status.NEW);
        taskManager.addNewTask(task);
        taskManager.deleteTaskById(task.getId());
        Assertions.assertNull(taskManager.getTaskById(task.getId()), "Задача не удалена");
    }

    @Test
    public void removeSubtaskById(){
        Epic epic = new Epic("e", "e", Status.NEW);
        Subtask subtask1 = new Subtask("s1", "s1", Status.NEW, epic.getId());
        taskManager.addNewEpic(epic);
        taskManager.addNewSubtask(subtask1);
        taskManager.deleteSubtaskById(subtask1.getId());
        Assertions.assertNull(taskManager.getSubtaskById(subtask1.getId()), "Подзадача не удалена");
    }

    @Test
    public void removeEpicById(){
        Epic epic = new Epic("e", "e", Status.NEW);
        Subtask subtask1 = new Subtask("s1", "s1", Status.NEW, epic.getId());
        taskManager.addNewEpic(epic);
        taskManager.addNewSubtask(subtask1);
        taskManager.deleteEpicById(epic.getId());
        Assertions.assertNull(taskManager.getEpicById(epic.getId()), "Эпик не удален");
    }

    @Test
    public void getSubtasksOfEpic(){
        Epic epic = new Epic("e", "e", Status.NEW);
        Subtask subtask1 = new Subtask("s1", "s1", Status.NEW, epic.getId());
        taskManager.addNewEpic(epic);
        taskManager.addNewSubtask(subtask1);
        ArrayList<Subtask> list = taskManager.getSubtaskOfEpic(epic);
        ArrayList<Subtask> list1 = new ArrayList<>();
        list1.add(subtask1);
        Assertions.assertNotNull(list1, "null вместо списка подзадач");
        Assertions.assertEquals(list, list1, "Списки подзадач не совпадают");
    }

    @Test
    public void getHistory(){
        Epic epic = new Epic("e", "e", Status.NEW);
        Subtask subtask1 = new Subtask("s1", "s1", Status.NEW, epic.getId());
        taskManager.addNewEpic(epic);
        taskManager.addNewSubtask(subtask1);
        taskManager.getEpicById(epic.getId());
        taskManager.getSubtaskById(subtask1.getId());
        List<Task> list = taskManager.getHistory();
        List<Task> list1 = List.of(epic, subtask1);
        Assertions.assertEquals(list, list1, "История не соответствует образцу");
    }
}
