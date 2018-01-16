package com.todolist.errors;

public class TodoListException extends Exception {

    // Parameterless Constructor
    public TodoListException() {}

    // Constructor that accepts a message
    public TodoListException(String message) {
        super(message);
    }
}
