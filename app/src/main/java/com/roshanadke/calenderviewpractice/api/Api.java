package com.roshanadke.calenderviewpractice.api;

import com.roshanadke.calenderviewpractice.model.CalenderResponse;
import com.roshanadke.calenderviewpractice.model.RecordsList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("calender_data.php")
    Call<CalenderResponse> sendUserId(
            @Field("user_id") String user_id,
            @Field("firstDay") String f_date,
            @Field("lastDay") String l_date,
            @Field("department") String department

    );


    @FormUrlEncoded
    @POST("user_summary")
    Call<RecordsList> sendDate(
            @Field("user_id") String user_id,
            @Field("date") String date,
            @Field("department") String department


    );


}
