package test;

import model.enums.Status;
import models.Epic;
import models.Subtask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.taskmanager.TasksManager;
import util.Managers;

class EpicTest {

    public static TasksManager taskManager;

    @BeforeEach
    public void createNewManager(){
        taskManager = Managers.getDefault();
    }

    @Test
    public void statusDefinitionTestEmptyMapOfSubtasks(){
        Epic epic = new Epic("e", "e", Status.NEW);
        taskManager.addNewEpic(epic);
        Assertions.assertTrue(taskManager.getSubtasks().isEmpty());
        Assertions.assertTrue(epic.getStatus().equals(Status.NEW));
    }

    @Test
    public void statusDefinitionTestAllSubtasksNew(){
        Epic epic = new Epic("e", "e", Status.NEW);
        taskManager.addNewEpic(epic);
        Subtask subtask1 = new Subtask("s1", "s1", Status.NEW, epic.getId());
        Subtask subtask2 = new Subtask("s2", "s2", Status.NEW, epic.getId());
        Subtask subtask3 = new Subtask("s3", "s3", Status.NEW, epic.getId());
        Assertions.assertTrue(epic.getStatus().equals(Status.NEW));
    }

    @Test
    public void statusDefinitionTestAllSubtasksDone(){
        Epic epic = new Epic("e", "e", Status.NEW);
        taskManager.addNewEpic(epic);
        Subtask subtask1 = new Subtask("s1", "s1", Status.DONE, epic.getId());
        Subtask subtask2 = new Subtask("s2", "s2", Status.DONE, epic.getId());
        Subtask subtask3 = new Subtask("s3", "s3", Status.DONE, epic.getId());
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);
        Assertions.assertTrue(epic.getStatus().equals(Status.DONE));
    }

    @Test
    public void statusDefinitionTestDifferentSubtaskStatuses(){
        Epic epic = new Epic("e", "e", Status.NEW);
        taskManager.addNewEpic(epic);
        Subtask subtask1 = new Subtask("s1", "s1", Status.DONE, epic.getId());
        Subtask subtask2 = new Subtask("s2", "s2", Status.NEW, epic.getId());
        Subtask subtask3 = new Subtask("s3", "s3", Status.DONE, epic.getId());
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);
        Assertions.assertTrue(epic.getStatus().equals(Status.IN_PROGRESS));
    }

    @Test
    public void statusDefinitionTestAllSubtasksInProgress(){
        Epic epic = new Epic("e", "e", Status.NEW);
        taskManager.addNewEpic(epic);
        Subtask subtask1 = new Subtask("s1", "s1", Status.IN_PROGRESS, epic.getId());
        Subtask subtask2 = new Subtask("s2", "s2", Status.IN_PROGRESS, epic.getId());
        Subtask subtask3 = new Subtask("s3", "s3", Status.IN_PROGRESS, epic.getId());
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);
        Assertions.assertTrue(epic.getStatus().equals(Status.IN_PROGRESS));
    }
}