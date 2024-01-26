package models;

import model.enums.Status;
import model.enums.TasksType;
import java.time.LocalDateTime;
import java.util.Objects;
/*
Объект Task(задача), содержит в себе идентификационный номер, имя, описание, и статус самой задачи.
Создается через Task task = new Task();
 */

public class Task implements Comparable<Task> {
    protected static int Id;
    protected Integer id;
    protected String name;
    protected String description;
    protected Status status;
    protected int duration;
    protected LocalDateTime startTime;
    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.id = generateId();
        this.status = status;
        this.startTime = LocalDateTime.of(1970,1,1,0,0);
    }
    public Task(String name, String description, Status status, int duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.id = generateId();
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public Integer getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && name.equals(task.name) && description.equals(task.description) && status.equals(task.status)
                && duration == task.duration && startTime.equals(task.startTime);
    }


    @Override
    public String toString() { // Динамический полиморфизм - вызов переопределенных методов, переопределение методов.
        return  id +
                "," + TasksType.Task +
                ", " + name +
                ", " + status +
                ", " + description +
                ", " + duration +
                ", " + startTime.toString();
    }
    private int generateId() {
        Id++;
        return Id;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration);
    }
    public Integer getDuration() {
        return duration;
    }
    @Override
    public int compareTo(Task o) {
        if (this.startTime.isBefore(o.startTime)) {
            return -1;
        } else if (this.startTime.isAfter(o.startTime)) {
            return 1;
        } else {
            return 0;
        }
    }
}
