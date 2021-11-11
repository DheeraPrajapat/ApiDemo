package com.example.apidemo.PojoClasses.GetAllComments;

import com.example.apidemo.PojoClasses.GetProfile.GetProfileBody;

import java.util.Date;

public class GetAllCommentsBody
{
    public int _id;
    public int feed_id;
    public int commenter_id;
    public String comment;
    public String total_likes;
    public String total_comments;
    public Date createdAt;
    public Date updatedAt;
    public GetProfileBody user;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(int feed_id) {
        this.feed_id = feed_id;
    }

    public int getCommenter_id() {
        return commenter_id;
    }

    public void setCommenter_id(int commenter_id) {
        this.commenter_id = commenter_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTotal_likes() {
        return total_likes;
    }

    public void setTotal_likes(String total_likes) {
        this.total_likes = total_likes;
    }

    public String getTotal_comments() {
        return total_comments;
    }

    public void setTotal_comments(String total_comments) {
        this.total_comments = total_comments;
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
