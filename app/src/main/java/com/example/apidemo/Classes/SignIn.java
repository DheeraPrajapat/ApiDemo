package com.example.apidemo.Classes;

public class SignIn
{
    private String email;
    private String password;

    public SignIn(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
