package com.example.apidemo.Team.CreateTeamPojo;

public class CreateTeamModel
{
    public int success;
    public int code;
    public String message;
    public CreateTeamBody body;

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

    public CreateTeamBody getBody() {
        return body;
    }

    public void setBody(CreateTeamBody body) {
        this.body = body;
    }
}
