package com.todolist.models;
import com.todolist.models.data.*;

import java.util.List;

public interface IToDoListDAO {

    // User methods
    User registerUser(User user);
    User signIn(String email, String password);

    // Task methods
    List<Task> getUserTasks(int userId);
    Task createOrUpdateTask(Task newTask);
    boolean removeTask(Task taskId);
}