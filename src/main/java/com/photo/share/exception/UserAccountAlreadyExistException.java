package com.photo.share.exception;

/**
 * When user account already exists with user_id
 */
public class UserAccountAlreadyExistException extends Exception {

    public UserAccountAlreadyExistException() {
        super();
    }

    public UserAccountAlreadyExistException(String message) {
        super(message);
    }

}
