package com.example.apidemo.PojoClasses.GetAllComments;

import com.example.apidemo.PojoClasses.GetProfile.GetProfileBody;

import java.util.Date;
import java.util.List;

public class GetAllCommentsModel {
    public int success;
    public int code;
    public String message;
    public List<GetAllCommentsBody> body;

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

    public List<GetAllCommentsBody> getBody() {
        return body;
    }

    public void setBody(List<GetAllCommentsBody> body) {
        this.body = body;
    }
}
