package test;

import model.enums.Status;
import models.Epic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import services.taskmanager.FileBackedTasksManager;

import java.io.File;

import static services.taskmanager.FileBackedTasksManager.loadFromFile;

public class FileBackedTaskManagerTest extends TaskManagerTest {
    //CSVManager csvManager;
    @Override
    public FileBackedTasksManager createNewManager() {
        FileBackedTasksManager manager = new FileBackedTasksManager(new File("resources/file.csv"));
        return manager;
    }
    @Test
    public void whenEmptyTaskList(){
        taskManager.deleteTasks();
        taskManager.deleteEpics();
        taskManager.deleteSubtasks();
        File file = new File("resources/file.csv");
        FileBackedTasksManager manager2  = loadFromFile(file);
        Assertions.assertEquals(manager2.getEpics(), taskManager.getEpics());
    }

    @Test
    public void loadFromFileWithOneEpicNoSubtasksNoHistory() {
        Epic epic = new Epic("e", "e", Status.New);
        taskManager.addNewEpic(epic);
        taskManager.deleteEpics();
        File file = new File("resources/file.csv");
        FileBackedTasksManager manager2  = loadFromFile(file);
        Assertions.assertEquals(manager2.getEpics(), taskManager.getEpics());
    }

    @Test
    public void loadFromFileWithOneEpicNoSubtasksWithHistory() {
        Epic epic = new Epic("e", "e", Status.New);
        taskManager.addNewEpic(epic);
        taskManager.getEpicById(epic.getId());
        taskManager.deleteEpics();
        File file = new File("resources/file.csv");
        FileBackedTasksManager manager2  = loadFromFile(file);
        Assertions.assertEquals(manager2.getEpics(), taskManager.getEpics());
    }
}
