package com.example.eventelevate.Interfaces;

import com.example.eventelevate.Model.AboutUsModel;
import com.example.eventelevate.Model.CityModel;
import com.example.eventelevate.Model.ContactModel;
import com.example.eventelevate.Model.DocumentsModel;
import com.example.eventelevate.Model.DocumentsUploadModel;
import com.example.eventelevate.Model.EventtypeModel;
import com.example.eventelevate.Model.LoginModel;
import com.example.eventelevate.Model.MyServiceModel;
import com.example.eventelevate.Model.PackageModel;
import com.example.eventelevate.Model.ProviderProfileModel;
import com.example.eventelevate.Model.ServiceModel;
import com.example.eventelevate.Model.ServiceProviderModel;
import com.example.eventelevate.Model.SettingModel;
import com.example.eventelevate.Model.SignupModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {
    @FormUrlEncoded
    @POST("user/getuser")
    Call<LoginModel> GetUserDetailsById(
            @Field("user_id") String userId);

    @FormUrlEncoded
    @POST("user/login")
    Call<LoginModel> VerifiedLoginData(
            @Field("username") String username,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("user/google")
    Call<LoginModel> GetUserByEmailId(
            @Field("email") String username);

    @FormUrlEncoded
    @POST("user/events")
    Call<MyServiceModel> GetAllPostedService(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("user/signup")
    Call<SignupModel> CreateUser(@Field("firstname") String firstname,
                                 @Field("lastname") String lastname,
                                 @Field("email") String email,
                                 @Field("password") String password,
                                 @Field("deviceid") String deviceid);

    @GET("Controller/Api/LocationController.json")
    Call<CityModel> GetLocationList();

    @GET("admin/setting")
    Call<SettingModel> GetApplicationSetting();

    @GET("event/eventtype/getalleventtype")
    Call<EventtypeModel> GetEventType();

    @GET("event/eventtype/getallpackages")
    Call<PackageModel> GetAllPackages();

    @GET("event/service/getallservices")
    Call<ServiceModel> GetServiceType();

    @FormUrlEncoded
    @POST("event/service/getservicebytablename")
    Call<ServiceProviderModel> GetServicelistbytable(@Field("service_id") String tablename);

    @FormUrlEncoded
    @POST("event/service/getproviderdata")
    Call<ProviderProfileModel> GetAllDetailsOfProvider(@Field("user_id") String  userID,
                                                       @Field("service_id") String serviceId);

    @FormUrlEncoded
    @POST("user/update")
    Call<SignupModel> UpdateUserProfile(
            @Field("user_id") String userId,
            @Field("first_name") String firstName,
            @Field("last_name") String lastName
    );

    @Multipart
    @POST("event/service/additem")
    Call<SignupModel> CreatePost(
            @Part("userid") RequestBody userID,
            @Part("title") RequestBody title,
            @Part("paymenttype") RequestBody paymentType,
            @Part("price") RequestBody price,
            @Part("description") RequestBody description,
            @Part("terms") RequestBody terms,
            @Part("location") RequestBody location,
            @Part("service_id") RequestBody servicename,
            @Part List<MultipartBody.Part> images
    );

    @Multipart
    @POST("user/documents")
    Call<DocumentsUploadModel> UploadDocuments(
            @Part("user_id") RequestBody userId,
            @Part MultipartBody.Part image,
            @Part("documenttype") RequestBody documentType
    );

    @Multipart
    @POST("event/service/update")
    Call<SignupModel> UpdatePost(
            @Part("userid") RequestBody userID,
            @Part("title") RequestBody title,
            @Part("paymenttype") RequestBody paymentType,
            @Part("price") RequestBody price,
            @Part("description") RequestBody description,
            @Part("terms") RequestBody terms,
            @Part("location") RequestBody location,
            @Part("item_id") RequestBody serviceId
    );

    @Multipart
    @POST("event/service/delete")
    Call<SignupModel> DeletePost(
            @Part("userid") String userID,
            @Part("item_id") String serviceId
    );

    @FormUrlEncoded
    @POST("user/status")
    Call<DocumentsModel> GetStatusofDocuments(
            @Field("user_id") String userID
    );


    @Multipart
    @POST("event/eventtype/addnewpackage")
    Call<SignupModel> CreatePackage(
            @Part("userid") RequestBody userID,
            @Part("title") RequestBody title,
            @Part("paymenttype") RequestBody paymentType,
            @Part("price") RequestBody price,
            @Part("description") RequestBody description,
            @Part("terms") RequestBody terms,
            @Part("location") RequestBody location,
            @Part("eventtype") RequestBody servicename,
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
