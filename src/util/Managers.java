package util;

import services.history.HistoryManager;
import services.history.InMemoryHistoryManager;
import services.taskmanager.FileBackedTasksManager;
import services.taskmanager.TasksManager;

import java.io.File;

public class Managers {
    public static TasksManager getDefault() {
        return new FileBackedTasksManager(new File("resources/file.csv"));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}
