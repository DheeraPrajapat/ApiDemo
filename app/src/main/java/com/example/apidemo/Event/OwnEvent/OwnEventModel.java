package com.example.apidemo.Event.OwnEvent;

public class OwnEventModel {
    public int success;
    public int code;
    public String message;
    public OwnEventBody body;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OwnEventBody getBody() {
        return body;
    }

    public void setBody(OwnEventBody body) {
        this.body = body;
    }
}
