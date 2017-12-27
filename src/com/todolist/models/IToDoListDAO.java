package com.todolist.models;
import com.todolist.models.data.*;

import java.util.List;

public interface IToDoListDAO {

    // User methods
    boolean registerUser(User user);
    User signIn(String username, String password);

    // Task methods
    List<Task> getUserTasks(int userId);
    void addOrUpdateTask(int userId, Task newTask);
    void removeTask(int userId, int taskId);
}