package com.todolist.controllers;

import com.todolist.errors.TodoListException;
import com.todolist.models.HibernateToDoListDAO;
import com.todolist.models.IToDoListDAO;
import com.todolist.models.data.Task;
import com.todolist.models.data.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = {"/registerUser", "/loginUser", "/logoutUser"})

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
            case "/registerUser":
                registerUser(req, res);
                break;

            case "/loginUser":
                loginUser(req, res);
                break;

            case "/logoutUser":
                logout(req, res);
                break;

            default: res.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void registerUser(HttpServletRequest req, HttpServletResponse res) {

        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");

            User newUser = new User(email, password, firstName, lastName);

            toDoListDAO.registerUser(newUser);

            res.setHeader("IS_VERIFY", "1");
        }
        catch (TodoListException e) {
            res.setHeader("IS_VERIFY", "0");
            res.setHeader("ERROR", e.getMessage());
        }
    }

    private void loginUser(HttpServletRequest req, HttpServletResponse res) {

        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            boolean isRememberMe = Boolean.parseBoolean(req.getParameter("rememberMe"));

            User signedUser = toDoListDAO.signIn(email, password);

            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(isRememberMe? 60 * 60 * 24 * 365 : 0);
            session.setAttribute("iTaskAppUser", signedUser);

            res.setHeader("IS_VERIFY", "1");
        }
        catch (TodoListException e) {
            res.setHeader("IS_VERIFY", "0");
            res.setHeader("ERROR", e.getMessage());
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession();
        session.invalidate();
    }

    private void redirectToHomePage(HttpServletRequest req, HttpServletResponse res) {
        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        try {
            dispatcher.forward(req, res);
        }
        catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}