package com.example.apidemo.Service;

import com.example.apidemo.SignUpPojo.GetProfileModel;
import com.example.apidemo.SignUpPojo.LogoutModel;
import com.example.apidemo.SignUpPojo.Model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService
{
    @FormUrlEncoded
    @POST("user-login")
    Call<Model> callbackLogin(@Field("email") String email,@Field("password") String password);

    @FormUrlEncoded
    @POST("user-signup")
    Call<Model> callbackRegister(@Field("email") String email, @Field("password") String password,@Field("address") String address);

    @FormUrlEncoded
    @POST("user-forget-password")
    Call<Model> callbackForget(@Field("email") String email);

    @FormUrlEncoded
    @POST("logout-user")
    Call<LogoutModel> callLogoutUser(@Field("user_id") String _id);

    @FormUrlEncoded
    @POST("get-user")
    Call<GetProfileModel> callGetUserInformation(@Field("user_id") String _id);
}
