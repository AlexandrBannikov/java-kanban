package test;

import model.enums.Status;
import models.Epic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import services.taskmanager.CSVManager;
import services.taskmanager.FileBackedTasksManager;

import java.io.File;

import static services.taskmanager.FileBackedTasksManager.loadFromFile;

public class FileBackedTaskManagerTest extends TaskManagerTest {
    CSVManager csvManager;
    @Override
    public FileBackedTasksManager createNewManager() {
        FileBackedTasksManager manager = new FileBackedTasksManager(new File("resources/file.csv"));
        return manager;
    }
    @Test
    public void emptyTaskList(){
        taskManager.deleteTasks();
        taskManager.deleteEpics();
        taskManager.deleteSubtasks();
        File file = new File("file.csv");
        FileBackedTasksManager manager2  = loadFromFile(file);
        Assertions.assertTrue(manager2.getEpics().equals(taskManager.getEpics()));
    }

    @Test
    public void loadFromFileWithOneEpicNoSubtasksNoHistory() {
        Epic epic = new Epic("e", "e", Status.NEW);
        taskManager.addNewEpic(epic);
        File file = new File("file.csv");
        FileBackedTasksManager manager2  = loadFromFile(file);
        Assertions.assertTrue(manager2.getEpics().equals(taskManager.getEpics()));
    }

    @Test
    public void loadFromFileWithOneEpicNoSubtasksWithHistory() {
        Epic epic = new Epic("e", "e", Status.NEW);
        taskManager.addNewEpic(epic);
        taskManager.getEpicById(epic.getId());
        File file = new File("file.csv");
        FileBackedTasksManager manager2  = loadFromFile(file);
        Assertions.assertTrue(manager2.getEpics().equals(taskManager.getEpics()));
    }
}
