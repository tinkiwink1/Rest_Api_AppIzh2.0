package ru.kata.spring.boot_security.demo.exception_handlers;

public class NotSuchFoundUserException extends RuntimeException {
    private String message;
    public NotSuchFoundUserException(String message) {
        super(message);
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
