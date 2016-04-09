package com.fichnalukas.astrumq.network;

public enum ApiTypes {

    USER_API(new UserApi());

    private final RetrofitApi instance;

    ApiTypes(RetrofitApi instance) {
        this.instance = instance;
    }

    public RetrofitApi getApiType() {
        return instance;
    }
}
