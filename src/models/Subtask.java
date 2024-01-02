package models;

import model.enums.Status;
import model.enums.TasksType;

import java.time.LocalDateTime;

public class Subtask extends Task {
    private int epicId;

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public Subtask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, Status status, int epicId, int duration, LocalDateTime startTime) {
        super(name, description, status, duration, startTime);
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return id +
                "," + TasksType.SUBTASK +
                "," + name +
                "," + status +
                "," + description +
                "," + duration +
                "," + startTime +
                "," + epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return id == subtask.id && name.equals(subtask.name) && description.equals(subtask.description)
                && status.equals(subtask.status) && epicId == subtask.epicId && duration == subtask.duration &&
                startTime.equals(subtask.startTime);
    }
}
