package com.todolist.models;
import com.todolist.exceptions.TodoListException;
import com.todolist.models.data.*;

import java.util.List;

public interface IToDoListDAO {

    // User methods
    void registerUser(User user) throws TodoListException;
    User signIn(String email, String password) throws TodoListException;

    // Task methods
    List<Task> getUserTasks(int userId);
    Task createOrUpdateTask(Task newTask);
    void removeTask(int taskId);
    Task getTaskById(int taskId);

    boolean toggleDoneTask(int taskId);
}