package com.todolist.controllers;

import com.todolist.models.HibernateToDoListDAO;
import com.todolist.models.IToDoListDAO;
import com.todolist.models.data.Task;
import com.todolist.models.data.User;

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

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        createOrUpdateTask(req, res);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException{
        getTaskAndEditOrCreate(req, res);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) {
        toggleTaskStatus(req, res);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) {
        removeTask(req, res);
    }

    private void getTaskAndEditOrCreate(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("iTaskAppUser");
        int taskId = -1;
        Task theTask = null;
        try {
            taskId = Integer.parseInt(req.getParameter("taskId"));
        }
        catch(NumberFormatException ignored) {}

        if(taskId != -1) {
            theTask = toDoListDAO.getTaskById(taskId);
        }
        if(theTask == null || theTask.getUserId() != user.getId()) {
            theTask = Task.getDefaultTask();
        }

        req.setAttribute("USER", user);
        req.setAttribute("TASK", theTask);
        req.getRequestDispatcher("task_form.jsp").forward(req, res);
    }

    private void toggleTaskStatus(HttpServletRequest req, HttpServletResponse res) {

    }

    private void createOrUpdateTask(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        // Get parameters from request
        String name = req.getParameter("name");
        String desc = req.getParameter("description");
        int userId = Integer.parseInt(req.getParameter("userId"));
        int taskId = -1;
        try {
            taskId = Integer.parseInt(req.getParameter("taskId"));
        }
        catch(NumberFormatException ignored) {}

        // Create the Task Object
        Task task = new Task(userId, name, desc);
        if(taskId != -1) {
            task.setId(taskId);
        }

        toDoListDAO.createOrUpdateTask(task);

        res.sendRedirect("home");
    }

    private void removeTask(HttpServletRequest req, HttpServletResponse res) {
        try {
            int taskId = Integer.parseInt(req.getParameter("taskId"));
            toDoListDAO.removeTask(taskId);
//            res.sendRedirect("home");
        }
        catch(NumberFormatException ignored) {}
    }
}