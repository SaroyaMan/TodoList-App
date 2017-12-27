package com.todolist.models;

import com.todolist.models.data.Task;
import com.todolist.models.data.User;

import java.util.List;

public class HibernateToDoListDAO implements IToDoListDAO {

    private static HibernateToDoListDAO instance;

    private HibernateToDoListDAO() {}

    public static HibernateToDoListDAO getInstance() {
        return instance == null ? instance = new HibernateToDoListDAO() : instance;
    }

    @Override
    public boolean registerUser(User user) {
        return false;
    }

    @Override
    public User signIn(String username, String password) {
        return null;
    }

    @Override
    public List<Task> getUserTasks(int userId) {
        return null;
    }

    @Override
    public void addOrUpdateTask(int userId, Task newTask) {

    }

    @Override
    public void removeTask(int userId, int taskId) {

    }
}
