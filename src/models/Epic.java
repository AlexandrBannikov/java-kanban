package models;

import model.enums.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/*
Объект Epic(основная задача, большая задача) наследуется от Task, наследует его конструктор и содержит
список Integer своих подзадач.
 */

public class Epic extends Task {

    // Список ID Subtask
    protected List<Integer> subtaskIds;

    /*
    Конструктор для Epic
     */

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }

    /*
    Конструктор для Subtask
    */
    public Epic(String name, String description, Status status) {
        super(name, description, status);
        subtaskIds = new ArrayList<>();
    }
    @Override
    public boolean isEpic() {
        return true;
    }

    /*
    Добавить подзадачу по id.
     */
    public void addSubtaskId(int id) {
        subtaskIds.add(id);
    }

    /*
    получить все номера задач
     */
    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    /*
    очистить список задач
     */
    public void cleanSubtaskIds() {
        subtaskIds.clear();
    }

    /*
    удалить subtask по id
     */
    public void removeSubtaskId(int id) {
        subtaskIds.remove(Integer.valueOf(id));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtaskIds, epic.subtaskIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskIds);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtaskIds=" + subtaskIds +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
