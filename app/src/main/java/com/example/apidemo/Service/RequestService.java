package com.example.apidemo.Service;


import com.example.apidemo.Friend.FriendList.Pojo.FriendListModel;
import com.example.apidemo.PojoClasses.RequestClasses.SendRequestModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RequestService
{
    @FormUrlEncoded
    @POST("send-request")
    Call<SendRequestModel> callbackSendRequest(@Field("user_id") int user_id,@Field("friend_id") int friend_id);

    @FormUrlEncoded
    @POST("get-friendlist")
    Call<FriendListModel> callbackGetFriendList(@Field("user_id") int user_id);
}
