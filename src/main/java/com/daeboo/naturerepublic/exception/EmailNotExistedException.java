package com.daeboo.naturerepublic.exception;

public class EmailNotExistedException extends RuntimeException {

    public EmailNotExistedException(String email) {
        super(email);
    }
}
