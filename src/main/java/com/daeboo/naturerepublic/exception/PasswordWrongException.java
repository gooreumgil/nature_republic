package com.daeboo.naturerepublic.exception;

public class PasswordWrongException extends RuntimeException {

    public PasswordWrongException() {
        super("Password is wrong");
    }
}
