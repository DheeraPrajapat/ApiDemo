package com.example.apidemo.Interfaces;

import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SignInInterface
{
    @FormUrlEncoded
    @POST("http://18.168.7.167:3004/api-docs/#/user/login")
    public void login(
            @Field("email")String email,
            @Field("password")String password
    );
}
