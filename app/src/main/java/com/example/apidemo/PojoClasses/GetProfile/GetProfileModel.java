package com.example.apidemo.PojoClasses.GetProfile;

import com.example.apidemo.PojoClasses.GetProfile.GetProfileBody;

public class GetProfileModel
{
    public int success;
    public int code;
    public String message;
    public GetProfileBody body;

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

    public GetProfileBody getBody() {
        return body;
    }

    public void setBody(GetProfileBody body) {
        this.body = body;
    }
}
