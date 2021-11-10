package com.example.apidemo.PojoClasses.SearchUser;

import com.example.apidemo.PojoClasses.SearchUser.SearchBody;

import java.util.List;

public class SearchModel
{
    public int success;
    public int code;
    public String message;
    public List<SearchBody> body;

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

    public List<SearchBody> getBody() {
        return body;
    }

    public void setBody(List<SearchBody> body) {
        this.body = body;
    }
}
