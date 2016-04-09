package com.fichnalukas.astrumq.network;

import com.fichnalukas.astrumq.AstrumQ;
import com.fichnalukas.astrumq.model.Authentication;
import com.fichnalukas.astrumq.model.LoginParams;
import com.fichnalukas.astrumq.model.Salt;

import retrofit2.Call;
import retrofit2.Retrofit;

public class UserApi extends RetrofitApi {

    private UserService service;

    public UserApi() {
        Retrofit restAdapter =
                AstrumQ.getRestAdapter();
        service = restAdapter.create(UserService.class);
    }

    public Call<Salt> getUserSalt(String email) {
        return service.getSalt(email);
    }

    public Call<Authentication> loginUser(String email, String pass) {
        return service.login(new LoginParams(email, pass));
    }
}