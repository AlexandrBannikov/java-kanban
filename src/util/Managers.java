package util;

import services.history.HistoryManager;
import services.history.InMemoryHistoryManager;
import services.taskmanager.InMemoryTaskManager;
import services.taskmanager.TaskManager;
/*
        Привет, мой многоуважаемый ревьюер !
        Соррян за прошлое потраченное время проверки этой не до работы...
        Все это делала моя собака породы Бигль по кличке Party Time или просто Пати)))
        Но я с ней поговорил,
        она все поняла, и без спроса больше не будет лезть в чужой код...
        пусть свой пишет)))
 */

public class Managers {
    private static final HistoryManager defaultHistoryManager = new InMemoryHistoryManager();
    private static final TaskManager defaultTaskManager = new InMemoryTaskManager(defaultHistoryManager);

    public static TaskManager getDefault() {
        return defaultTaskManager;
    }
    public static HistoryManager getDefaultHistory() {
        return defaultHistoryManager;
    }
}
