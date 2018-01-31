package com.todolist.models;

import com.todolist.exceptions.TodoListException;
import com.todolist.models.data.Task;
import com.todolist.models.data.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.jasypt.util.password.BasicPasswordEncryptor;

import javax.persistence.NoResultException;
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
    public void registerUser(User user) throws TodoListException {

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
                throw new TodoListException("User with that email address already exists");
            }

            // encrypt the password
            String encryptedPassword = passwordEncryptor.encryptPassword(user.getPassword());
            user.setPassword(encryptedPassword);

            // save the user object
            session.save(user);

            // commit transaction
            session.getTransaction().commit();
        }
        catch(HibernateException | TodoListException e) {
            session.getTransaction().rollback();
            throw new TodoListException(e.getMessage());
        }
        finally {
            closeSession(session);
        }
    }

    @Override
    public User signIn(String email, String password) throws TodoListException {

        // create a session
        Session session = factory.getCurrentSession();
        User user;
        try {

            // start a transaction
            session.beginTransaction();

            user = (User) session.createQuery("FROM User u WHERE u.email = :email")
                    .setParameter("email", email)
                    .getSingleResult();

            session.getTransaction().commit();

            if(passwordEncryptor.checkPassword(password, user.getPassword())) {
                return user;
            }

            else {
                throw new TodoListException("Email or Password is incorrect");
            }

        }
        catch(HibernateException e) {
            throw new TodoListException(e.getMessage());
        }
        catch(NoResultException e) {
            throw new TodoListException("User is not exist");
        }
        finally {
            closeSession(session);
        }
    }

    @Override
    public List<Task> getUserTasks(int userId) {
        // create a session
        Session session = factory.getCurrentSession();

        try {
            // start a transaction
            session.beginTransaction();

            List tasks = session.createQuery("FROM Task t WHERE t.userId = :userId ORDER BY t.isDone")
                    .setParameter("userId", userId)
                    .getResultList();

            // commit transaction
            session.getTransaction().commit();

            return (List<Task>) tasks;
        }
        catch(HibernateException e) {
            session.getTransaction().rollback();
            return null;
        }
        finally {
            closeSession(session);
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
            session.getTransaction().rollback();
            return null;
        }
        finally {
            closeSession(session);
        }
    }

    @Override
    public void removeTask(int taskId) {
        // create a session
        Session session = factory.getCurrentSession();

        try {

            // start a transaction
            session.beginTransaction();

            // delete the task object
            session.createQuery("delete from Task where id=:id")
                    .setParameter("id", taskId)
                    .executeUpdate();


            // commit transaction
            session.getTransaction().commit();
        }

        catch(HibernateException e) {
            session.getTransaction().rollback();
        }
        finally {
            closeSession(session);
        }
    }

    @Override
    public Task getTaskById(int taskId) {
        // create a session
        Session session = factory.getCurrentSession();

        try {

            // start a transaction
            session.beginTransaction();

            // delete the task object
            Task theTask = (Task) session.createQuery("FROM Task where id=:id")
                    .setParameter("id", taskId)
                    .getSingleResult();

            // commit transaction
            session.getTransaction().commit();
            return theTask;
        }

        catch(HibernateException | NoResultException e) {
            session.getTransaction().rollback();
            return null;
        }
        finally {
            closeSession(session);
        }
    }

    @Override
    public boolean toggleDoneTask(int taskId) {
        // create a session
        Session session = factory.getCurrentSession();
        Transaction transaction = session.getTransaction();
        try {
            // start a transaction
            session.beginTransaction();

            // delete the task object
            Task theTask = (Task) session.createQuery("FROM Task where id=:id")
                    .setParameter("id", taskId)
                    .getSingleResult();

            theTask.setIsDone(!theTask.getIsDone());

            // commit transaction
            transaction.commit();

            return true;
        }
        catch(HibernateException | NoResultException e) {
            transaction.rollback();
            return false;
        }
        finally {
            closeSession(session);
        }
    }

    private void closeSession(Session session) {
        if(session != null) {
            session.close();
        }
    }
}