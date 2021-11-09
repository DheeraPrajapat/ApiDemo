package com.example.apidemo.SignUpPojo;

public class UserPostModel
{
    public int success;
    public int code;
    public String message;
    public UserPostBody body;

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

    public UserPostBody getBody() {
        return body;
    }

    public void setBody(UserPostBody body) {
        this.body = body;
    }
}
