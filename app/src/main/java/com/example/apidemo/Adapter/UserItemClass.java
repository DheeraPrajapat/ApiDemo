package com.example.apidemo.Adapter;

public class UserItemClass
{
    private String full_name;
    private String device_type;

    public UserItemClass(String full_name, String device_type) {
        this.full_name = full_name;
        this.device_type = device_type;
    }

    public UserItemClass() {
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }
}
