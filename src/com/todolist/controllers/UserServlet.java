package com.todolist.controllers;

import com.todolist.exceptions.TodoListException;
import com.todolist.models.HibernateToDoListDAO;
import com.todolist.models.IToDoListDAO;
import com.todolist.models.data.Task;
import com.todolist.models.data.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = {"/registerUser", "/loginUser", "/logoutUser", "/home", "/login"})

public class UserServlet extends HttpServlet {

    private IToDoListDAO toDoListDAO;
    private final int ONE_YEAR = 60 * 60 * 24 * 365;

    @Override
    public void init() throws ServletException {
        super.init();
        toDoListDAO = HibernateToDoListDAO.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String urlPattern = req.getRequestURI();

        switch (urlPattern) {
            case "/home":
                initUser(req, res);
                break;

            case "/login":
                moveToLoginPage(req, res);
                break;

            default: break;
        }
    }

    private void initUser(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        HttpSession ses = req.getSession();

        User user = null;
        List<Task> taskList = null;

        if(ses.getAttribute("iTaskAppUser") != null) {
            user = (User) ses.getAttribute("iTaskAppUser");
            taskList = HibernateToDoListDAO.getInstance().getUserTasks(user.getId());
        }
        if(user == null) {
            res.sendRedirect("login");
            return;
        }

        req.setAttribute("USER" , user);
        req.setAttribute("TASKS", taskList);

        req.getRequestDispatcher("index.jsp").forward(req, res);
    }

    private void moveToLoginPage(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("iTaskAppFullName")) {
                    String fullName = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    req.setAttribute("FULL_NAME",fullName);
                    break;
                }
            }
        }
        req.getRequestDispatcher("login.jsp").forward(req, res);
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
            session.setMaxInactiveInterval(isRememberMe? ONE_YEAR : 0);
            session.setAttribute("iTaskAppUser", signedUser);

            String fullName = signedUser.getFirstName() + " " + signedUser.getLastName();
            Cookie newCookie = new Cookie("iTaskAppFullName", URLEncoder.encode(fullName, "UTF-8"));
            res.addCookie(newCookie);
            res.setHeader("IS_VERIFY", "1");
        }
        catch (TodoListException | UnsupportedEncodingException e) {
            res.setHeader("IS_VERIFY", "0");
            res.setHeader("ERROR", e.getMessage());
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession();
        session.invalidate();
    }
}