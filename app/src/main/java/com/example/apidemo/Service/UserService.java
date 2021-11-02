package com.example.apidemo.Service;

import com.example.apidemo.SignUpPojo.ChangePasswordModel;
import com.example.apidemo.SignUpPojo.GetProfileModel;
import com.example.apidemo.SignUpPojo.LogoutModel;
import com.example.apidemo.SignUpPojo.Model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;

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

    @FormUrlEncoded
    @POST("user-update")
    Call<GetProfileModel> callUpdateTheUserInformation(@Field("address") String address,@Field("device-type")String type);

    @FormUrlEncoded
    @POST("user-change-password")
    Call<ChangePasswordModel> callChangePassword(@Field("old_password") String old_password , @Field("new_password") String new_password);

    @FormUrlEncoded
    @POST("check-email")
    Call<Model> callbackCheckEmail(@Field("email") String email);

    @FormUrlEncoded
    @POST("check-username")
    Call<Model> callbackCheckUsername(@Field("username") String username);
}
