package com.example.apidemo.Friend.FriendList.Pojo;

import java.util.List;

public class FriendListModel
{
    public int success;
    public int code;
    public String message;
    public List<FriendListBody> body;

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

    public List<FriendListBody> getBody() {
        return body;
    }

    public void setBody(List<FriendListBody> body) {
        this.body = body;
    }
}
