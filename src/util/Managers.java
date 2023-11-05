package util;

import services.history.HistoryManager;
import services.history.InMemoryHistoryManager;
import services.taskmanager.InMemoryTaskManager;
import services.taskmanager.TaskManager;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
