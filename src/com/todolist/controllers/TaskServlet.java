package com.todolist.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TaskServlet", urlPatterns = {"/task"})
public class TaskServlet extends HttpServlet {

    // will be used for create or update a task
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        // Get parameters from request

        // Create the Task Object
    }

    // will be used for get tasks task
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }

    // Will be used for delete a task
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }
}
