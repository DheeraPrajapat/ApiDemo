package com.example.apidemo.Team.CreateEventPojo;

public class CreateEventModel
{
    public int success;
    public int code;
    public String message;
    public CreateEventBody body;

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

    public CreateEventBody getBody() {
        return body;
    }

    public void setBody(CreateEventBody body) {
        this.body = body;
    }
}
