package test;

import model.enums.Status;
import models.Epic;
import models.Subtask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.taskmanager.TasksManager;
import util.Managers;

import java.io.IOException;
import java.net.URISyntaxException;

class EpicTest {
    public static TasksManager taskManager;

    @BeforeEach
    public void createNewManager() throws IOException, URISyntaxException, InterruptedException {
        taskManager = Managers.getDefault();
    }

    @Test
    public void statusDefinitionTestEmptyMapOfSubtasks(){
        Epic epic = new Epic("e", "e", Status.New);
        taskManager.addNewEpic(epic);
        Assertions.assertTrue(taskManager.getSubtasks().isEmpty());
        Assertions.assertEquals(epic.getStatus(), Status.New);
    }

    @Test
    public void statusDefinitionTestAllSubtasksNew(){
        Epic epic = new Epic("e", "e", Status.New);
        taskManager.addNewEpic(epic);
        Assertions.assertEquals(epic.getStatus(), Status.New);
    }

    @Test
    public void statusDefinitionTestAllSubtasksDone(){
        Epic epic = new Epic("e", "e", Status.New);
        taskManager.addNewEpic(epic);
        Subtask subtask1 = new Subtask("s1", "s1", Status.Done, epic.getId());
        Subtask subtask2 = new Subtask("s2", "s2", Status.Done, epic.getId());
        Subtask subtask3 = new Subtask("s3", "s3", Status.Done, epic.getId());
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);
        Assertions.assertEquals(epic.getStatus(), Status.Done);
    }

    @Test
    public void statusDefinitionTestDifferentSubtaskStatuses(){
        Epic epic = new Epic("e", "e", Status.New);
        taskManager.addNewEpic(epic);
        Subtask subtask1 = new Subtask("s1", "s1", Status.Done, epic.getId());
        Subtask subtask2 = new Subtask("s2", "s2", Status.New, epic.getId());
        Subtask subtask3 = new Subtask("s3", "s3", Status.Done, epic.getId());
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);
        Assertions.assertEquals(epic.getStatus(), Status.Done);
    }

    @Test
    public void statusDefinitionTestAllSubtasksInProgress(){
        Epic epic = new Epic("e", "e", Status.New);
        taskManager.addNewEpic(epic);
        Subtask subtask1 = new Subtask("s1", "s1", Status.In_progress, epic.getId());
        Subtask subtask2 = new Subtask("s2", "s2", Status.In_progress, epic.getId());
        Subtask subtask3 = new Subtask("s3", "s3", Status.In_progress, epic.getId());
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);
        Assertions.assertEquals(epic.getStatus(), Status.In_progress);
    }
}