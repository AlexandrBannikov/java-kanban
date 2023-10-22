package business;

import models.enums.Status;

import java.util.Objects;

public class Subtask extends Task {
    private final Integer epicId;

    public int getEpicId() {
        return epicId;
    }

    public Subtask(int id, String name, String description, Status status, Integer epicId) {
        super(id, name, description, status);
        this.epicId = epicId;
    }
    public Subtask(String name, String description, Status status, Integer epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return Objects.equals(epicId, subtask.epicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    @Override
    public String toString() {
        return "business.Subtask{" +
                "epicId=" + epicId +
                '}';
    }
}