package com.photo.share.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for rest operation status.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {

    public enum Status {ERROR, SUCCESS};

    @JsonProperty("status")
    private Status status;

    @JsonProperty("message")
    private String message;

    public Message(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
