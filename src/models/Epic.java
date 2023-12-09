package models;

import model.enums.Status;
import model.enums.TasksType;

import java.util.List;
/*
Объект Epic(основная задача, большая задача) наследуется от Task, вызывает его конструктор и содержит
список Integer своих подзадач.
 */

public class Epic extends Task {

    // Список ID Subtask
    private List<Integer> subtaskIds;

    /*
    получить все номера задач
     */
    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    /*
    Конструктор для Epic
     */
    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public void setSubtaskIds(List<Integer> subtaskIds) {
        this.subtaskIds = subtaskIds;
    }

    public Status getCurrentStatus() {
        return status;
    }

    /*
    Добавить подзадачу по id.
     */
    public void addSubtaskId(int id) {
        if (subtaskIds == null) {
            return;
        }
        subtaskIds.add(id);
    }

    @Override
    public String toString() {
        return id +
                "," + TasksType.EPIC +
                "," + name +
                "," + status +
                "," + description;
    }
}
