package com.photo.share.exception;

/**
 * Custom exception thrown when user account is not found.
 */
public class UserAccountNotFoundException extends Exception {

    public UserAccountNotFoundException() {
        super();
    }

    public UserAccountNotFoundException(String message) {
        super(message);
    }
}
