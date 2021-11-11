package com.example.apidemo.PojoClasses.CreatePostComment;

public class CommentModel
{
    public int success;
    public int code;
    public String message;
    public CommentBody body;

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

    public CommentBody getBody() {
        return body;
    }

    public void setBody(CommentBody body) {
        this.body = body;
    }
}
