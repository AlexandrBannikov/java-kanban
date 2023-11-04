package services.manager;

import business.Task;

import java.util.List;

public interface HistoryManager {
    List<Task> getHistory();
    void addTask(Task task);
}