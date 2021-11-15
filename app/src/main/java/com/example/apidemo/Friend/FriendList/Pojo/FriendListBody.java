package com.example.apidemo.Friend.FriendList.Pojo;

import com.example.apidemo.PojoClasses.GetProfile.GetProfileBody;

import java.util.Date;

public class FriendListBody
{
    public int _id;
    public int user_id;
    public int friend_id;
    public int status;
    public Date createdAt;
    public Date updatedAt;
    public GetProfileBody user;

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

    public int getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
}
