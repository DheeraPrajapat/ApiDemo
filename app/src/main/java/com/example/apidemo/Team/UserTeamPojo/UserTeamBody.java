package com.example.apidemo.Team.UserTeamPojo;

import com.example.apidemo.PojoClasses.GetProfile.GetProfileBody;

import java.util.Date;

public class UserTeamBody
{
    public int _id;
    public String title;
    public int admin_id;
    public String image;
    public String description;
    public int visibility;
    public String region;
    public Date createdAt;
    public Date updatedAt;
    public GetProfileBody user;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
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
}
