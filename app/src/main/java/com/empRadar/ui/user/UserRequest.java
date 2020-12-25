package com.empRadar.ui.user;

public class UserRequest {
    String id;
    String name;
    String status;
    String  message;

    public UserRequest() {
    }

    public UserRequest(String id, String name, String status, String message) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

