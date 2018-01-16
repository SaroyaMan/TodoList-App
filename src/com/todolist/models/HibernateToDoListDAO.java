package com.todolist.models;

import com.todolist.models.data.Task;
import com.todolist.models.data.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jasypt.util.password.BasicPasswordEncryptor;

import java.util.List;

public class HibernateToDoListDAO implements IToDoListDAO {

    // Hibernate variables
    private Configuration config;
    private SessionFactory factory;

    // Password encryptor
    private BasicPasswordEncryptor passwordEncryptor;

    private static HibernateToDoListDAO instance;

    private HibernateToDoListDAO() {

        // create Hibernate configuration object
        config = new Configuration().configure();

        // create session factory
        factory = config
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Task.class)
                .buildSessionFactory();

        passwordEncryptor = new BasicPasswordEncryptor();
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

            // encrypt the password
            String encryptedPassword = passwordEncryptor.encryptPassword(user.getPassword());
            user.setPassword(encryptedPassword);

            // save the user object
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
    public User signIn(String email, String password) {

        // create a session
        Session session = factory.getCurrentSession();
        User user;
        try {

            user = (User) session.createQuery("FROM User u WHERE u.email = :email")
                    .setParameter("email", email)
                    .getSingleResult();


            if(user != null && passwordEncryptor.checkPassword(password, user.getPassword())) {
                return user;
            }
            else {
                // password incorrect
                return null;
            }
        }
        catch(HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Task> getUserTasks(int userId) {
        // create a session
        Session session = factory.getCurrentSession();

        try {
            // start a transaction
            session.beginTransaction();

            List tasks = session.createQuery("FROM Task t WHERE t.userId = :userId")
                    .setParameter("userId", userId)
                    .getResultList();

            // commit transaction
            session.getTransaction().commit();

            return (List<Task>) tasks;
        }
        catch(HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public Task createOrUpdateTask(Task newTask) {
        // create a session
        Session session = factory.getCurrentSession();

        try {
            // start a transaction
            session.beginTransaction();

            // save the task object
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
    public boolean removeTask(Task task) {
        // create a session
        Session session = factory.getCurrentSession();

        try {
            // start a transaction
            session.beginTransaction();

            // save the task object
            session.delete(task);

            // commit transaction
            session.getTransaction().commit();

            return true;
        }

        catch(HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }
    }
}
