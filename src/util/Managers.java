package util;

import services.manager.HistoryManager;
import services.manager.InMemoryHistoryManager;
import services.manager.InMemoryTaskManager;
import services.manager.TaskManager;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
