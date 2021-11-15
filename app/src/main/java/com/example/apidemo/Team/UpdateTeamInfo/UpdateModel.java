package com.example.apidemo.Team.UpdateTeamInfo;

import java.util.List;

public class UpdateModel
{
    public int success;
    public int code;
    public String message;
    public List<Integer> body;

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

    public List<Integer> getBody() {
        return body;
    }

    public void setBody(List<Integer> body) {
        this.body = body;
    }
}
