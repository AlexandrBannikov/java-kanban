package models;

import model.enums.Status;
import model.enums.TasksType;

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

    @Override
    public String toString() {
        return id +
                "," + TasksType.SUBTASK +
                "," + name +
                "," + status +
                "," + description +
                "," + epicId;
    }
}
