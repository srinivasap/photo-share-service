package com.photo.share.exception;

/**
 * Custom exception thrown on invalid operations.
 * For example, attempted to delete photos of other users.
 */
public class InvalidOperaiton extends Exception {

    public InvalidOperaiton() {
        super();
    }

    public InvalidOperaiton(String message) {
        super(message);
    }
}
