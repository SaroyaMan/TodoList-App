package com.todolist.models;

import com.todolist.models.data.Task;
import com.todolist.models.data.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HibernateToDoListDAO implements IToDoListDAO {

    // Hibernate variables
    private Configuration config;
    private SessionFactory factory;

    private static HibernateToDoListDAO instance;

    private HibernateToDoListDAO() {
        // create Hibernate configuration object
        config = new Configuration().configure();

        // create session factory
        factory = config
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Task.class)
                .buildSessionFactory();
    }

    public static HibernateToDoListDAO getInstance() {
        return instance == null ? instance = new HibernateToDoListDAO() : instance;
    }

    @Override
    public User registerUser(User user) {

        // create a session
        Session session = factory.getCurrentSession();

        try {

            // start a transaction
            session.beginTransaction();

            // validate the email address before saving
            List users = session.createQuery("FROM User u WHERE u.email = :email")
                    .setParameter("email", user.getEmail())
                    .getResultList();

            if(!users.isEmpty()) {
                throw new HibernateException("User with that email address already exists");
            }

            // save the user object
            System.out.println("Saving the User...");
            session.save(user);

            // commit transaction
            session.getTransaction().commit();

            return user;
        }
        catch(HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }

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
    public Task addOrUpdateTask(Task newTask) {
        // create a session
        Session session = factory.getCurrentSession();

        try {
            // start a transaction
            session.beginTransaction();

            // save the task object
            System.out.println("Saving the Task...");
            session.saveOrUpdate(newTask);

            // commit transaction
            session.getTransaction().commit();

            return newTask;
        }
        catch(HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public void removeTask(int taskId) {

    }
}
