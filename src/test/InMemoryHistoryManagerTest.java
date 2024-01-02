package test;

import model.enums.Status;
import models.Epic;
import models.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.history.HistoryManager;
import util.Managers;

import java.util.List;

public class InMemoryHistoryManagerTest {
    public static HistoryManager historyManager;

    @BeforeEach
    public void beforeEach(){
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    public void emptyHistory(){
        final List<Task> history = historyManager.getHistory();
        Assertions.assertTrue(history.isEmpty(), "История не пустая.");
    }

    @Test
    public void addTask(){
        Task task = new Task("t", "t", Status.NEW);
        historyManager.addTask(task);
        final List<Task> history = historyManager.getHistory();
        Assertions.assertEquals(1, history.size());
    }

    @Test
    public void deletion(){
        Task task = new Task("t", "t", Status.NEW);
        Task task2 = new Task("t2", "t2", Status.NEW);
        Epic epic = new Epic("t2", "t2", Status.NEW);
        Task task3 = new Task("t3", "t3", Status.NEW);
        Task task4 = new Task("t4", "t4", Status.NEW);
        historyManager.addTask(task);
        historyManager.addTask(task2);
        historyManager.addTask(epic);
        historyManager.addTask(task3);
        historyManager.addTask(task4);
        Assertions.assertEquals(5, historyManager.getHistory().size(), "История не содержит 5 записей");
        historyManager.remove(epic.getId());
        Assertions.assertEquals(4, historyManager.getHistory().size(), "Удаление из середины не выполнено");
        historyManager.remove(task.getId());
        Assertions.assertEquals(3, historyManager.getHistory().size(), "Удаление из начала не выполнено");
        historyManager.remove(task4.getId());
        Assertions.assertEquals(2, historyManager.getHistory().size(), "Удаление с конца не выполнено");
        List<Task> list = historyManager.getHistory();
        Assertions.assertEquals(list, List.of(task2,task3));
    }

}
