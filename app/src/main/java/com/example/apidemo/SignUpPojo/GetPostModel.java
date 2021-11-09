package com.example.apidemo.SignUpPojo;

import java.util.Date;

public class GetPostModel
{
    public int _id;
    public int user_id;
    public String text;
    public int total_comments;
    public int total_likes;
    public Object files;
    public String region;
    public Date createdAt;
    public Date updatedAt;
    public GetProfileBody user;
    public int isLike;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTotal_comments() {
        return total_comments;
    }

    public void setTotal_comments(int total_comments) {
        this.total_comments = total_comments;
    }

    public int getTotal_likes() {
        return total_likes;
    }

    public void setTotal_likes(int total_likes) {
        this.total_likes = total_likes;
    }

    public Object getFiles() {
        return files;
    }

    public void setFiles(Object files) {
        this.files = files;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public GetProfileBody getUser() {
        return user;
    }

    public void setUser(GetProfileBody user) {
        this.user = user;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }
}
