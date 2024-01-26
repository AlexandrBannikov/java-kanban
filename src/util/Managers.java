package util;

import httpmanager.HTTPTasksManager;
import services.history.HistoryManager;
import services.history.InMemoryHistoryManager;
import services.taskmanager.TasksManager;

import java.io.IOException;
import java.net.URISyntaxException;

public class Managers {

    public static TasksManager getDefault() throws IOException, URISyntaxException, InterruptedException {
        return new HTTPTasksManager("http://localhost:8080");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
