package com.todolist.controllers;

import com.todolist.errors.TodoListException;
import com.todolist.models.HibernateToDoListDAO;
import com.todolist.models.IToDoListDAO;
import com.todolist.models.data.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserServlet", urlPatterns = {"/register", "/signin"})

public class UserServlet extends HttpServlet {

    private IToDoListDAO toDoListDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        toDoListDAO = HibernateToDoListDAO.getInstance();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        String urlPattern = req.getRequestURI();

        switch(urlPattern) {
            case "/register":
                registerUser(req, res);
                break;

            case "/signin":
                loginUser(req, res);
                break;

            default: res.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void registerUser(HttpServletRequest req, HttpServletResponse res) throws IOException {

        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");

            User newUser = new User(email, password, firstName, lastName);

            newUser = toDoListDAO.registerUser(newUser);

            req.setAttribute("USER", newUser);

            RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
            dispatcher.forward(req, res);
        }
        catch (TodoListException | ServletException e) {
            e.printStackTrace();
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void loginUser(HttpServletRequest req, HttpServletResponse res) throws IOException {

        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            User signedUser = toDoListDAO.signIn(email, password);

            req.setAttribute("USER", signedUser);

            RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
            dispatcher.forward(req, res);
        }
        catch (ServletException e) {
            e.printStackTrace();
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
