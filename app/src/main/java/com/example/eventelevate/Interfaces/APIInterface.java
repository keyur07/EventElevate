package com.example.eventelevate.Interfaces;

import com.example.eventelevate.Model.ContactModel;
import com.example.eventelevate.Model.LocationModel;
import com.example.eventelevate.Model.LoginModel;
import com.example.eventelevate.Model.SettingModel;
import com.example.eventelevate.Model.SignupModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @FormUrlEncoded
    @POST("user/login")
    Call<LoginModel> VerifiedLoginData(
            @Field("username") String username,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("user/signup")
    Call<SignupModel> CreateUser(@Field("firstname") String firstname,
                                 @Field("lastname") String lastname,
                                 @Field("email") String email,
                                 @Field("password") String password,
                                 @Field("deviceid") String deviceid);

    @GET("cities")
    Call<LocationModel> GetLocationList(@Query("page") Integer number, @Header("Authorization") String authHeader);

    @GET("admin/setting")
    Call<SettingModel> GetApplicationSetting();

    @FormUrlEncoded
    @POST("admin/contactus")
    Call<ContactModel> SendUserContactMessage(@Field("userid") String userid,
                                              @Field("name") String name,
                                              @Field("email") String email,
                                              @Field("subject") String subject,
                                              @Field("message") String mesage);
}
