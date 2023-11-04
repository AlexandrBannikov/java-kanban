package services.manager;

import business.Epic;
import business.Subtask;
import business.Task;

import java.util.ArrayList;
import java.util.List;
/*
    Класс TaskManager интерфейс.
    Для этого добавьте метод getHistory() в  TaskManager и реализуйте его —
    он должен возвращать последние 10 просмотренных задач.
    Просмотром будем считаться вызов у менеджера методов получения задачи по идентификатору  — getTask(),
     getSubtask() и getEpic(). От повторных просмотров избавляться не нужно.
 */

public interface TaskManager {
    /*
    Получить список всех задач, все задачи.
     */
    List<Task> getTasks();
    List<Subtask> getSubtasks();
    List<Epic> getEpics();
    ArrayList<Subtask> getEpicSubtasks(int epicID);

    /*
    Получить задачу по id
     */
    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpic(int id);

    /*
    Добавить задачу в Map
     */
    int addNewTask(Task task);

    int addNewEpic(Epic epic);

    Integer addNewSubtask(Subtask subtask);

    /*
    Обновить статус Epic
     */
    void updateEpicStatus(int epicID);
    /*
    Обновить задачи
     */
    void updateTask(Task task);
    void updateSubtask(Subtask subtask);
    void updateEpic(Epic epic);
    /*
    Удалить задачу по id
     */
    void deleteTaskById(int id);

//    void deleteEpic(int id);
//
//    void deleteSubtask(int id);

    /*
    Очистить Map, удалить все задачи
     */

    void deleteTasks();
    void deleteEpics();

    void deleteSubtasks();
    List<Task> getHistory();
}
