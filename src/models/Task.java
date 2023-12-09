package models;

import model.enums.Status;
import model.enums.TasksType;

import java.util.Objects;
/*
Объект Task(задача), содержит в себе идентификационный номер, имя, описание, и статус самой задачи.
Создается через Task task = new Task();
 */

public class Task {
    protected static int Id;
    protected Integer id;
    protected String name;
    protected String description;
    protected Status status;


    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.id = generateId();
        this.status = status;
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
        return Objects.equals(id, task.id) && Objects.equals(name, task.name)
                && Objects.equals(description, task.description) && status == task.status;
    }


    @Override
    public String toString() { // Динамический полиморфизм - вызов переопределенных методов, переопределение методов.
        return  id +
                "," + TasksType.TASK +
                "," + name +
                "," + status +
                "," + description;
    }
    private int generateId() {
        Id++;
        return Id;
    }
}
