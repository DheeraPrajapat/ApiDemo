package com.example.apidemo.Service;

import com.example.apidemo.Event.AdminCompetition.AdminCompetitionModel;
import com.example.apidemo.Event.OwnEvent.OwnEventModel;
import com.example.apidemo.Event.OwnEvent.OwnSchoolEventsModel;
import com.example.apidemo.Event.OwnEvent.OwnTeamEventModel;
import com.example.apidemo.PojoClasses.CreatePost.UserPostModel;
import com.example.apidemo.Team.CreateTeamPojo.CreateTeamModel;
import com.example.apidemo.Team.DeleteTeamPojo.DeleteModel;
import com.example.apidemo.Team.GetAllTeams.GetAllTeamModel;
import com.example.apidemo.Team.UpdateTeamInfo.UpdateModel;
import com.example.apidemo.Team.UserTeamPojo.UserTeamBody;
import com.example.apidemo.Team.UserTeamPojo.UserTeamModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TeamService
{
    @FormUrlEncoded
    @POST("get-user-team")
    Call<UserTeamModel> callbackGetUserTeam(@Field("user_id") String userId);

    @GET("get-all-teams")
    Call<GetAllTeamModel> callbackGetAllTeam();

    @FormUrlEncoded
    @POST("create-team")
    Call<CreateTeamModel> callbackCreateTeam(@Field("title") String title, @Field("admin_id") int admin_id , @Field("description") String description);

    @FormUrlEncoded
    @POST("delete-team")
    Call<DeleteModel> callDeleteTeam(@Field("team_id") String teamId, @Field("admin_id") int admin_id);

    @FormUrlEncoded
    @POST("update-team")
    Call<UpdateModel> callUpdateTeam(@Field("team_id") int team_id , @Field("title") String title , @Field("description") String description);

    @FormUrlEncoded
    @POST("create-event")
    Call<CreateTeamModel> callbackCreateEvent(@Field("title") String title,@Field("user_id") int user_id , @Field("team_id") int team_id,
                                                @Field("start_time") String startTime,@Field("end_time") String endTime ,@Field("description") String description,
                                              @Field("event_type") int event_type , @Field("color") String color);

    @GET("admin-competitions")
    Call<AdminCompetitionModel> callbackGetAdminCompetition();

    @FormUrlEncoded
    @POST("school-events")
    Call<OwnSchoolEventsModel> callOwnSchoolEventModel(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("team-events")
    Call<OwnTeamEventModel> callOwnTeamEventModel(@Field("team_id") int teamId);
}
