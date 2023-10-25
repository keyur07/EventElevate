package com.example.eventelevate.Interfaces;

import com.example.eventelevate.Model.AboutUsModel;
import com.example.eventelevate.Model.ContactModel;
import com.example.eventelevate.Model.EventtypeModel;
import com.example.eventelevate.Model.Location.MainLoction;
import com.example.eventelevate.Model.LocationModel;
import com.example.eventelevate.Model.LoginModel;
import com.example.eventelevate.Model.ServiceModel;
import com.example.eventelevate.Model.ServiceProviderModel;
import com.example.eventelevate.Model.SettingModel;
import com.example.eventelevate.Model.SignupModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Call<MainLoction> GetLocationList();

    @GET("admin/setting")
    Call<SettingModel> GetApplicationSetting();

    @GET("event/eventtype/getalleventtype")
    Call<EventtypeModel> GetEventType();

    @GET("event/service/getallservices")
    Call<ServiceModel> GetServiceType();

    @FormUrlEncoded
    @POST("event/service/getservicebytablename")
    Call<ServiceProviderModel> GetServicelistbytable(@Field("table_name") String tablename);

    @Multipart
    @POST("event/service/additem")
    Call<SignupModel> CreatePost(
            @Part("title") RequestBody title,
            @Part("paymenttype") RequestBody paymentType,
            @Part("price") RequestBody price,
            @Part("description") RequestBody description,
            @Part("terms") RequestBody terms,
            @Part("location") RequestBody location,
            @Part("servicename") RequestBody servicename,
            @Part List<MultipartBody.Part> images
    );


    @GET("admin/aboutus")
    Call<AboutUsModel> GetAboutDetails();

    @FormUrlEncoded
    @POST("admin/contactus")
    Call<ContactModel> SendUserContactMessage(@Field("userid") String userid,
                                              @Field("name") String name,
                                              @Field("email") String email,
                                              @Field("subject") String subject,
                                              @Field("message") String mesage);
}
