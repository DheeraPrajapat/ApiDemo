package com.example.apidemo.Service;

import com.example.apidemo.PojoClasses.CreatePostComment.CommentModel;
import com.example.apidemo.PojoClasses.GetAllComments.GetAllCommentsModel;
import com.example.apidemo.PojoClasses.GetPost.GetAllPostModel;
import com.example.apidemo.PojoClasses.SearchUser.SearchModel;
import com.example.apidemo.SignUpPojo.ChangePasswordModel;
import com.example.apidemo.PojoClasses.GetProfile.GetProfileModel;
import com.example.apidemo.SignUpPojo.LikeModel;
import com.example.apidemo.SignUpPojo.LogoutModel;
import com.example.apidemo.SignUpPojo.Model;
import com.example.apidemo.PojoClasses.CreatePost.UserPostModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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

    @FormUrlEncoded
    @POST("search-user")
    Call<SearchModel> callbackGetUserByName(@Field("text") String name);

    @FormUrlEncoded
    @POST("create-feed")
    Call<UserPostModel> callbackCreatePost(@Field("user_id") String _id , @Field("text") String text);

    @GET("get-feeds")
    Call<GetAllPostModel> callbackGetAllPost();

    @FormUrlEncoded
    @POST("get-a-feed")
    Call<GetAllPostModel> callbackGetSingleFeed(@Field("feed_id") Integer id);
    @FormUrlEncoded
    @POST("like")
    Call<LikeModel> callbackLike(@Field("feed_id") int feed_id , @Field("user_id") int user_id , @Field("status") int status);

    @FormUrlEncoded
    @POST("add-feed-comment")
    Call<CommentModel> callbackPostComment(@Field("feed_id") int feed_id, @Field("commenter_id") int commenter_id , @Field("comment") String comment);

    @FormUrlEncoded
    @POST("get-feed-comments")
    Call<GetAllCommentsModel> callbackGetAllComments(@Field("feed_id") int feed_id , @Field("page") int page ,@Field("limit") int limit);

}
