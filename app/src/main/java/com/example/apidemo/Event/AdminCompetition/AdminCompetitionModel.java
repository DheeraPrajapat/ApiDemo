package com.example.apidemo.Event.AdminCompetition;

import java.util.Date;
import java.util.List;

public class AdminCompetitionModel
{
    public int success;
    public int code;
    public String message;
    public List<AdminCompetitionBody> body;

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

    public List<AdminCompetitionBody> getBody() {
        return body;
    }

    public void setBody(List<AdminCompetitionBody> body) {
        this.body = body;
    }
}
