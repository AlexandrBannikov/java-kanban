package services.manager;

import business.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private List<Task> historyList = new ArrayList<>();

    @Override
    public void addTask(Task task) {
        if (historyList.size() >= 10) {
            historyList.remove(0);
        }
        historyList.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return historyList;
    }
}
