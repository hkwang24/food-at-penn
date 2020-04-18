package com.foodatpenn.Retrofit;

import io.reactivex.Observable;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IMyService {
    @POST("register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("email") String email,
                                    @Field("name") String name,
                                    @Field("password") String password,
                                    @Field("year") String year,
                                    @Field("phone") String phone);

    @POST("login")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("email") String email,
                                    @Field("password") String name);

    @POST("contains")
    @FormUrlEncoded
    Observable<String> contains(@Field("email") String email);

    @POST("getUser")
    @FormUrlEncoded
    Observable<String> getUser(@Field("email") String email);

    @POST("modify")
    @FormUrlEncoded
    Observable<String> modifyUser(@Field("email") String email,
                                    @Field("name") String name,
                                    @Field("year") String year,
                                    @Field("phone") String phone);

}
