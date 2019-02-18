package com.photo.share.exception;

public class UserAccountNotFoundException extends Exception {

    public UserAccountNotFoundException() {
        super();
    }

    public UserAccountNotFoundException(String message) {
        super(message);
    }
}
