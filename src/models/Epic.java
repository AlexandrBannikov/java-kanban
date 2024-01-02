package models;

import model.enums.Status;
import model.enums.TasksType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/*
Объект Epic(основная задача, большая задача) наследуется от Task, вызывает его конструктор и содержит
список Integer своих подзадач.
 */

public class Epic extends Task {

    // Список ID Subtask
    private List<Integer> subtaskIds = new ArrayList<>();
    private LocalDateTime endTime;

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
                "," + description +
                "," + duration +
                "," + startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void deleteSubtask() {
        subtaskIds.clear();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Epic epic = (Epic) o;
        return id == epic.id && name.equals(epic.name) && description.equals(epic.description)
                && status.equals(epic.status) && Objects.equals(subtaskIds, epic.subtaskIds);
    }
}
