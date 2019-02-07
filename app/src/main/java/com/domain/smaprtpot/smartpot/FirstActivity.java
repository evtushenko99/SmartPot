package com.domain.smaprtpot.smartpot;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.domain.smaprtpot.smartpot.model.Pot;
import com.google.android.material.textfield.TextInputEditText;


import java.util.List;

import static com.domain.smaprtpot.smartpot.encoded.getSha256;


public class FirstActivity extends AppCompatActivity {

    private SmartPotApi mSmartPotApi;


    private Button mButtonSignIn;
    private Button mButtonRegistration;
    public TextInputEditText mUserEmail;
    public TextInputEditText mUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        mButtonSignIn = findViewById(R.id.sign_in);
        mButtonRegistration = findViewById(R.id.Registration);
        mUserEmail = findViewById(R.id.email);
        mUserPassword = findViewById(R.id.password);
        //Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-smartpot.ddns.net:4444/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mSmartPotApi = retrofit.create(SmartPotApi.class);
        SmartPot.getInstance().setSmartPotApi(mSmartPotApi);
        //
        mButtonSignIn.setOnClickListener(mOnCLickSignIn);
        mButtonRegistration.setOnClickListener(mOnClickListenerRegister);

    }

    View.OnClickListener mOnClickListenerRegister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(FirstActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener mOnCLickSignIn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String getEm = mUserEmail.getText().toString();
            String getPasswd = mUserPassword.getText().toString();
            postAuth(getEm, getPasswd);
        }
    };

    public void postAuth(String user_email, String user_password) {
        String encryptPassword = getSha256(user_password).toUpperCase();
        Call<String> call = SmartPot.getInstance().getSmartPotApi().getAuth(user_email, encryptPassword);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String postResponse = response.body();
                    SmartPot.getInstance().setUserToken(postResponse);
                    proceedToMain();
                } else {
                    Toast.makeText(FirstActivity.this, "code: " + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(FirstActivity.this, "Ошибка в POST, Code: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void proceedToMain() {
        Intent intent = new Intent(this, Activity_Pots.class);
        startActivity(intent);
    }
}
