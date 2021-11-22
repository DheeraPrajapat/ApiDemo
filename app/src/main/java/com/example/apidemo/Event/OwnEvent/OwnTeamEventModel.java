package com.example.apidemo.Event.OwnEvent;

import java.util.List;

public class OwnTeamEventModel
{
    public int success;
    public int code;
    public String message;
    public List<OwnTeamEventBody> body;

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

    public List<OwnTeamEventBody> getBody() {
        return body;
    }

    public void setBody(List<OwnTeamEventBody> body) {
        this.body = body;
    }
}
