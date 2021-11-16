package com.example.apidemo.Friend.FriendList.RequestListPojo;

import java.util.List;

public class RequestModel {
    public int success;
    public int code;
    public String message;
    public List<RequestBody> body;


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

    public List<RequestBody> getBody() {
        return body;
    }

    public void setBody(List<RequestBody> body) {
        this.body = body;
    }
}
