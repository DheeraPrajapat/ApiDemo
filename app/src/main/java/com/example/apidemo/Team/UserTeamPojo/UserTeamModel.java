package com.example.apidemo.Team.UserTeamPojo;

public class UserTeamModel
{
    public int success;
    public int code;
    public String message;
    public UserTeamBody body;

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

    public UserTeamBody getBody() {
        return body;
    }

    public void setBody(UserTeamBody body) {
        this.body = body;
    }
}
