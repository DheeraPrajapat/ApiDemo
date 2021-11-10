package com.example.apidemo.PojoClasses.GetPost;

import com.example.apidemo.PojoClasses.GetPost.GetAllPostBody;

import java.util.List;

public class GetAllPostModel
{
    public int success;
    public int code;
    public String message;
    public List<GetAllPostBody> body;

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

    public List<GetAllPostBody> getBody() {
        return body;
    }

    public void setBody(List<GetAllPostBody> body) {
        this.body = body;
    }
}
