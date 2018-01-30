package com.todolist.models.data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tasks", schema = "todolist1")

public class Task {
    // Used for Null-Object pattern
    private static Task defaultTask = new Task(-1, -1, "", "", false);
    private int id;
    private int userId;
    private String name;
    private String description;
    private boolean isDone;

    public Task(int userId, String name, String description, boolean isDone) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.isDone = isDone;
    }

    public Task(int id, int userId, String name, String description, boolean isDone) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.isDone = isDone;
    }

    public Task() {}

    public static Task getDefaultTask() {
        return defaultTask;
    }

    @Basic
    @Column(name = "isDone")
    public boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "userId")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    @Basic
//    @Column(name = "isDone")
//    public boolean getIsDone() {
//        return isDone;
//    }
//
//    public void set(String description) {
//        this.description = description;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                userId == task.userId &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                isDone == task.isDone;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, name, description);
    }

    @Override
    public String toString() {
        return getId() + ", " + getUserId() + "," + getName() + ", " + getDescription();
    }
}
