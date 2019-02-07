package com.domain.smaprtpot.smartpot;

import com.domain.smaprtpot.smartpot.model.Pot;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SmartPotApi {
    @POST("auth")
    Call<String> getAuth(@Header("Email") String email, @Header("Password") String password);

    @GET("allDev")
    Call<List<Pot>> getAlldev(@Header("Authorization") String userToken);

    @POST("createAcc")
    Call<String> createNewAcc(
            @Header("secret") String secret,
            @Header("Email") String email,
            @Header("Password") String password,
            @Header("deviceToken") String deviceToken,
            @Header("First_name") String firstName,
            @Header("Last_name") String lastName
    );
    @GET("dev")
    Call<List<Pot>> getDev(
            @Header("Authorization") String userToken,
            @Header("deviceToken") String deviceToken
    );
    @POST("regPot")
    Call<String> regPot(
            @Header("deviceToken") String deviceToken,
            @Header("Authorization") String userToken
    );
    @POST("delPot")
    Call<String> delPot(
            @Header("Authorization") String userToken,
            @Header("deviceToken") String deviceToken,
            @Header("Password") String password
    );
    @POST("setName")
    Call<String> setName(
            @Header("Authorization") String userToken,
            @Header("deviceToken") String deviceToken,
            @Header("name") String name
    );
    @POST("setSleepMode")
    Call<String> setSleepMode(
            @Header("Authorization") String userToken,
            @Header("deviceToken") String deviceToken,
            @Header("sleepMode") int value
    );
    @POST("setWateringPeriod")
    Call<String> setWateringPeriod(
            @Header("Authorization") String userToken,
            @Header("deviceToken") String deviceToken,
            @Header("wateringPeriod") int value
    );
    @POST("setThresholdSM")
    Call<String> setThresholdSM(
            @Header("Authorization") String userToken,
            @Header("deviceToken") String deviceToken,
            @Header("thresholdSM") int value
    );

}
