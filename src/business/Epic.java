package business;

import models.enums.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    protected List<Integer> subtaskIds;

    /*
    Конструктор для эпиков
     */

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }

    /*
            Конструктор для сабтасков
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
        return "business.Epic{" +
                "subtaskIds=" + subtaskIds +
                '}';
    }
}
