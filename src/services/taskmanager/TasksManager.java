package services.taskmanager;

import models.Epic;
import models.Subtask;
import models.Task;

import java.util.ArrayList;
import java.util.List;
/*
    Интерфейс TaskManager.
    Для этого добавьте метод getHistory() в  TaskManager и реализуйте его —
    он должен возвращать последние 10 просмотренных задач.
    Просмотром будем считаться вызов у менеджера методов получения задачи по идентификатору  — getTask(),
     getSubtask() и getEpic(). От повторных просмотров избавляться не нужно.
 */

public interface TasksManager {
    /*
    Получить список всех задач, все задачи.
     */
    List<Task> getTasks();
    List<Subtask> getSubtasks();
    List<Epic> getEpics();
    ArrayList<Subtask> getEpicSubtasks(int epicID);
    ArrayList<Subtask> getSubtaskOfEpic(Epic epic);

    /*
    Получить задачу по id
     */
    Task getTaskById(int id);

    Subtask getSubtaskById(int id);

    Epic getEpicById(int id);

    /*
    Добавить задачу в Map
     */
    int addNewTask(Task task);

    int addNewEpic(Epic epic);

    int addNewSubtask(Subtask subtask);

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

    void deleteEpicById(int id);
//
    void deleteSubtaskById(int id);

    /*
    Очистить Map, удалить все задачи
     */

    void deleteTasks();
    void deleteEpics();

    void deleteSubtasks();
    List<Task> getHistory();

}
