package com.domain.smaprtpot.smartpot;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import static com.domain.smaprtpot.smartpot.encoded.getSha256;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText mTextInputLayoutEmail;
    private TextInputEditText mTextInputLayoutPassword;
    private TextInputEditText mTextInputLayoutDeviceToken;
    private TextInputEditText mTextInputLayoutFirstName;
    private TextInputEditText mTextInputLayoutLastName;

    private Button mButtonSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mTextInputLayoutEmail = findViewById(R.id.textInputEditText_email);
        mTextInputLayoutPassword = findViewById(R.id.textInputEditText_password);
        mTextInputLayoutDeviceToken = findViewById(R.id.textInputEditText_deviceToken);
        mTextInputLayoutFirstName = findViewById(R.id.textInputEditText_firstName);
        mTextInputLayoutLastName = findViewById(R.id.textInputEditText_lastName);

        mButtonSignIn = findViewById(R.id.button_signIn);
        mButtonSignIn.setOnClickListener(mOnClickListenerSignIn);

    }

    View.OnClickListener mOnClickListenerSignIn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createAcc(mTextInputLayoutEmail.getText().toString(),
                    mTextInputLayoutPassword.getText().toString(),
                    mTextInputLayoutDeviceToken.getText().toString(),
                    mTextInputLayoutFirstName.getText().toString(),
                    mTextInputLayoutLastName.getText().toString()
            );
        }
    };

    public void createAcc(String email, String password, String deviceToken, String first_name, String last_name) {
        String encryptPassword = getSha256(password).toUpperCase();
        Call<String> call = SmartPot.getInstance().getSmartPotApi().createNewAcc("smartPotClientANDROID",email, encryptPassword, deviceToken, first_name, last_name);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    Toast.makeText(RegisterActivity.this, "New account has been created", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(RegisterActivity.this, "New account don't create", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "There is a mistake", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
