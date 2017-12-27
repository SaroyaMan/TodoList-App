package com.todolist.tests;

import com.todolist.models.data.Task;
import com.todolist.models.data.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {


    public static void main(final String[] args) throws Exception {

        // create Hibernate configuration object
        Configuration config = new Configuration().configure();


        // create session factory
        SessionFactory factory = config
                .addAnnotatedClass(User.class).addAnnotatedClass(Task.class)
                .buildSessionFactory();

        // create a session
        Session session = factory.getCurrentSession();


        //use the session object to save java object
        try {
            //create a User object
            System.out.println("Creating new user object");
            User tempUser = new User();
            tempUser.setEmail("stankovic100@gmail.com");
            tempUser.setPassword("321321");
            tempUser.setFirstName("Yoav");
            tempUser.setLastName("Saroya");

            //start a transaction
            session.beginTransaction();

            //save the user object
            System.out.println("Saving the User...");
            session.save(tempUser);

            //commit transaction
            session.getTransaction().commit();

            System.out.println("Done!");
        }
        finally {
            factory.close();
        }
    }
}