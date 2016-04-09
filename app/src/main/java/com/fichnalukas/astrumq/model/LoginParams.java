package com.fichnalukas.astrumq.model;

import com.google.gson.annotations.SerializedName;

public class LoginParams {

    @SerializedName("email")
    String mEmail;

    @SerializedName("password")
    String mPass;

    public LoginParams(String email, String pass) {
        this.mEmail = email;
        this.mPass = pass;
    }
}