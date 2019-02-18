package com.photo.share.exception;

/**
 * Custom exception thrown when photo is not found.
 */
public class PhotoNotFoundException extends Exception {

    public PhotoNotFoundException() {
        super();
    }

    public PhotoNotFoundException(String message) {
        super(message);
    }

}
