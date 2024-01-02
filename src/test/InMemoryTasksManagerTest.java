package test;

import services.taskmanager.InMemoryTasksManager;
import services.taskmanager.TasksManager;

public class InMemoryTasksManagerTest extends TaskManagerTest {
    @Override
    public TasksManager createNewManager() {
        InMemoryTasksManager manager = new InMemoryTasksManager();
        return manager;
    }
}
