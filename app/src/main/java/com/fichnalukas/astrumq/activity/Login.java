package com.fichnalukas.astrumq.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fichnalukas.astrumq.AstrumQ;
import com.fichnalukas.astrumq.R;
import com.fichnalukas.astrumq.helper.Config;
import com.fichnalukas.astrumq.model.Authentication;
import com.fichnalukas.astrumq.model.Salt;
import com.fichnalukas.astrumq.network.ApiTypes;
import com.fichnalukas.astrumq.network.UserApi;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends Base {

    private EditText mEmail;
    private EditText mPassword;
    private Button mLogin;
    private Button mRegister;
    private String txtEmail;
    private String txtPass;
    private Salt salt;
    private Authentication auth;
    private UserApi userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initVariables();
    }

    private void initVariables() {
        mEmail = (EditText) findViewById(R.id.txt_email);
        mPassword = (EditText) findViewById(R.id.txt_password);
        mLogin = (Button) findViewById(R.id.btn_login);
        mRegister = (Button) findViewById(R.id.btn_registration);
        setClickListeners();
    }

    private void setClickListeners() {
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registration.class));
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    sendSaltRequest();
                } else {
                    AstrumQ.showMessage("Please fill out the username and password correctly");
                }
            }
        });
    }

    private void sendSaltRequest() {
        userApi = (UserApi) AstrumQ.getApiOfType(ApiTypes.USER_API);
        Call<Salt> saltCall = userApi.getUserSalt(txtEmail);
        saltCall.enqueue(new Callback<Salt>() {
            @Override
            public void onResponse(Call<Salt> call, Response<Salt> response) {
                int statusCode = response.code();
                salt = response.body();
                sendLoginRequest();
            }

            @Override
            public void onFailure(Call<Salt> call, Throwable t) {
                AstrumQ.showLog(t.getMessage());
            }
        });
    }

    private void sendLoginRequest() {
        String pass = salt.getSalt() + hashPassword(txtPass);
        Call<Authentication> authenticationCall = userApi.loginUser(txtEmail, pass);
        authenticationCall.enqueue(new Callback<Authentication>() {
            @Override
            public void onResponse(Call<Authentication> call, Response<Authentication> response) {
                auth = response.body();
                SharedPreferences.Editor editor = AstrumQ.getSharedPreferences().edit();
                editor.putString(Config.USER_EMAIL, txtEmail);
                editor.putInt(Config.USER_ID, auth.getId());
                editor.putString(Config.USER_TOKEN, auth.getToken());
                editor.apply();
                // TODO: 29.03.2016 intent na vypis prispevku
            }

            @Override
            public void onFailure(Call<Authentication> call, Throwable t) {
                AstrumQ.showLog(t.getMessage());
            }
        });
    }

    private String hashPassword(String pass) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(pass.getBytes("UTF-8"));
            return new String(hash, "UTF-8");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean validateInput() {
        txtEmail = mEmail.getText().toString().trim();
        txtPass = mPassword.getText().toString().trim();
        return txtPass.length() >= 6 &&
                !Pattern.compile("\\s").matcher(txtPass).find() &&
                isEmailValid(txtEmail);
    }

    private boolean isEmailValid(String email) {
        return !"".equals(email) && Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(email).matches();
    }
}
