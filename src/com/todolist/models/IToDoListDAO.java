package com.todolist.models;
import com.todolist.errors.TodoListException;
import com.todolist.models.data.*;

import java.util.List;

public interface IToDoListDAO {

    // User methods
    void registerUser(User user) throws TodoListException;
    User signIn(String email, String password) throws TodoListException;

    // Task methods
    List<Task> getUserTasks(int userId);
    Task createOrUpdateTask(Task newTask);
    boolean removeTask(Task taskId);

    User getUserByToken(String token) throws TodoListException;
}