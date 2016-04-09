package com.fichnalukas.astrumq.network;

import com.fichnalukas.astrumq.model.Authentication;
import com.fichnalukas.astrumq.model.LoginParams;
import com.fichnalukas.astrumq.model.Salt;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @GET("login/salt")
    Call<Salt> getSalt(@Query("email") String email);

    @POST("login")
    Call<Authentication> login(@Body LoginParams params);

}
