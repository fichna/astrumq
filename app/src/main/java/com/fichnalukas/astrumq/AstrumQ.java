package com.fichnalukas.astrumq;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.fichnalukas.astrumq.network.ApiTypes;
import com.fichnalukas.astrumq.network.RetrofitApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AstrumQ extends Application {

    private static AstrumQ mInstance;
    private static SharedPreferences mSharedPrefs;

    public AstrumQ() {
    }

    public static AstrumQ getInstance() {
        return mInstance;
    }

    public static void showMessage(String message) {
        Toast.makeText(mInstance.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public static void showLog(String message) {
        Log.d("AstrumQ", message);
    }

    public static Retrofit getRestAdapter() {
        String BASE_URL = "http://private-anon-97fdd031c-aqhr.apiary-mock.com/api/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL);
        return builder.build();
    }

    public static RetrofitApi getApiOfType(ApiTypes type) {
        return type.getApiType();
    }

    public static SharedPreferences getSharedPreferences() {
        return mSharedPrefs;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mSharedPrefs = getApplicationContext().getSharedPreferences("AstrumQ", MODE_PRIVATE);
    }
}
