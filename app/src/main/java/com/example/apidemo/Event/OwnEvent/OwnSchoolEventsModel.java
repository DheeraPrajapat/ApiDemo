package com.example.apidemo.Event.OwnEvent;

import java.util.List;

public class OwnSchoolEventsModel
{
    public int success;
    public int code;
    public String message;
    public List<OwnSchoolEventBody> body;

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

    public List<OwnSchoolEventBody> getBody() {
        return body;
    }

    public void setBody(List<OwnSchoolEventBody> body) {
        this.body = body;
    }
}
