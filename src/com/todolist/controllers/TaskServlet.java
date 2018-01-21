package com.todolist.controllers;

import com.todolist.models.HibernateToDoListDAO;
import com.todolist.models.IToDoListDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TaskServlet", urlPatterns = {"/task"})
public class TaskServlet extends HttpServlet {

    private IToDoListDAO toDoListDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        toDoListDAO = HibernateToDoListDAO.getInstance();
    }

    // will be used for create or update a task
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        // Get parameters from request
        String name = req.getParameter("name");
        String desc = req.getParameter("description");

        // Create the Task Object
    }

    // will be used for get tasks
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }

    // Will be used for delete a task
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res)
            throws NumberFormatException {
        int taskId = Integer.parseInt(req.getParameter("taskId"));
        toDoListDAO.removeTask(taskId);
    }
}
