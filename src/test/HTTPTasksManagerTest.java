package test;

import httpmanager.HTTPTasksManager;
import model.enums.Status;
import models.Epic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.Managers;

import java.io.IOException;
import java.net.URISyntaxException;

class HTTPTasksManagerTest extends TaskManagerTest<HTTPTasksManager> {
    @Override
    public HTTPTasksManager createNewManager() {
        HTTPTasksManager manager;
        try {
            manager = (HTTPTasksManager) Managers.getDefault();
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return manager;
    }
    @Test
    public void emptyTaskList() {
        taskManager.deleteTasks();
        taskManager.deleteEpics();
        taskManager.deleteSubtasks();
        HTTPTasksManager manager2 = new HTTPTasksManager("http://localhost:8080");
        Assertions.assertEquals(manager2.getEpics(), taskManager.getEpics());
    }
    @Test
    public void loadFromServerWithOneEpicNoSubtasks() {
        Epic epic = new Epic("e", "e", Status.New);
        taskManager.addNewEpic(epic);
        HTTPTasksManager manager2 = new HTTPTasksManager("http://localhost:8080");
        Assertions.assertEquals(manager2.getEpics(), taskManager.getEpics());
    }

    @Test
    public void loadFromFileWithOneEpicNoSubtasksWithHistory() {
        Epic epic = new Epic("e", "e", Status.New);
        taskManager.addNewEpic(epic);
        taskManager.getEpicById(epic.getId());
        HTTPTasksManager manager2 = new HTTPTasksManager("http://localhost:8080");
        Assertions.assertEquals(manager2.getEpics(), taskManager.getEpics());
    }

}