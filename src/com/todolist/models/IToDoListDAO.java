package com.todolist.models;
import com.todolist.models.data.*;

import java.util.List;

public interface IToDoListDAO {

    // User methods
    User registerUser(User user);
    User signIn(String username, String password);

    // Task methods
    List<Task> getUserTasks(int userId);
    Task addOrUpdateTask(Task newTask);
    void removeTask(int taskId);
}