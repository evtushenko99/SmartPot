package com.domain.smaprtpot.smartpot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.domain.smaprtpot.smartpot.model.Pot;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import static com.domain.smaprtpot.smartpot.encoded.getSha256;

public class OnePot extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    private TextView mNameOfPOt;
    private TextView mDeviceToken;
    private TextView mAirTemperature;
    private TextView mSoilMeisture;
    private TextView mWateringFrequency;
    private TextView mSleepMode;
    private TextView mThresholdSM;
    private String mNameOfPotString;

    private ImageButton mImageButtonForName;
    private ImageButton mImageButtonSetSleepMode;
    private ImageButton mImageButtonWateringPeriod;
    private ImageButton mImageButtonThresholdSM;

    private List<Pot> mPot;

    private AlertDialog dialog;

    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton;
    private View mView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_pot);
        Bundle bundle = getIntent().getExtras();
        mNameOfPotString = bundle.get("deviceToken").toString();

        mNameOfPOt = findViewById(R.id.textView_NameOfPot);
        mDeviceToken = findViewById(R.id.textView_DeviceToken);
        mAirTemperature = findViewById(R.id.textView_Airtemperature);
        mSoilMeisture = findViewById(R.id.textView_SoilMeisture);
        mWateringFrequency = findViewById(R.id.textView_WateringFrequency);
        mSleepMode = findViewById(R.id.textView_SleepMode);
        mThresholdSM = findViewById(R.id.textView_ThresholdOfSM);

        mImageButtonForName = findViewById(R.id.imageButton_ForName);
        mImageButtonSetSleepMode = findViewById(R.id.imageButton_SendData);
        mImageButtonWateringPeriod = findViewById(R.id.imageButton_WateringEvery);
        mImageButtonThresholdSM = findViewById(R.id.imageButton_ThresholdOfSM);


        getDev();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mImageButtonForName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(OnePot.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_set_name1, null);
                final TextInputEditText mNewNameOfPot = mView.findViewById(R.id.textInputLayout_dialod_NewNameOfPoT);
                mNewNameOfPot.setText(mNameOfPOt.getText());
                Button mChange = mView.findViewById(R.id.button_ChangeName);

                mChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mNewNameOfPot.getText().toString().isEmpty()) {
                            setName(mNewNameOfPot.getText().toString());
                        }
                    }
                });
                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();
            }
        });
        mImageButtonSetSleepMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(OnePot.this);
                mView1 = getLayoutInflater().inflate(R.layout.dialog_set_sleep_mode, null);
                mRadioGroup = mView1.findViewById(R.id.radioGroup);
                Button mSave_SM = mView1.findViewById(R.id.button_save_sleep_mode);
                mSave_SM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int radioID = mRadioGroup.getCheckedRadioButtonId();
                        mRadioButton = mView1.findViewById(radioID);
                        startSleepMode(mRadioButton);
                    }
                });
                mBuilder.setView(mView1);
                dialog = mBuilder.create();
                dialog.show();
            }
        });
        mImageButtonWateringPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(OnePot.this);
                mView1 = getLayoutInflater().inflate(R.layout.dialog_set_watering_period, null);
                mRadioGroup = mView1.findViewById(R.id.radioGroup_WP);
                Button mSave_SM = mView1.findViewById(R.id.button_save_watering_period);
                mSave_SM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int radioID = mRadioGroup.getCheckedRadioButtonId();
                        mRadioButton = mView1.findViewById(radioID);
                        startWateringPeriod(mRadioButton);
                    }
                });
                mBuilder.setView(mView1);
                dialog = mBuilder.create();
                dialog.show();
            }
        });
        mImageButtonThresholdSM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(OnePot.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_set_threshold_sm, null);
                final TextInputEditText mNewThresholdSM = mView.findViewById(R.id.textInputLayout_dialod_setthresholdSM);
                mNewThresholdSM.setText(mThresholdSM.getText());
                Button mChange = mView.findViewById(R.id.button_set_threshold_sm);
                SeekBar mSeekBar = mView.findViewById(R.id.seekBar);
                mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        mNewThresholdSM.setText(String.valueOf(progress));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                         //&& Integer.parseInt(mNewThresholdSM.getText().toString()) >=0 && Integer.parseInt(mNewThresholdSM.getText().toString()) <=100
                    }
                });
                mChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mNewThresholdSM.getText().toString().isEmpty())  {
                            setThresholdSM(Integer.parseInt(mNewThresholdSM.getText().toString()));
                        }else Toast.makeText(OnePot.this, "Введите число от 0 до 100 " , Toast.LENGTH_SHORT).show();
                    }
                });
                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();
            }
        });


    }

    public void startSleepMode(RadioButton radioButton) {
        if (radioButton != null) {
            if (radioButton.getId() == R.id.radioButton) {
                setSleepMode(0);
            } else if (radioButton.getId() == R.id.radioButton2) {
                setSleepMode(1);
            } else {
                setSleepMode(2);
            }
        }
    }
    public void startWateringPeriod(RadioButton radioButton) {
        if (radioButton != null) {
            if (radioButton.getId() == R.id.radioButton_WP_1) {
                setWateringPeriod(0);
            } else if (radioButton.getId() == R.id.radioButton_WP_2) {
                setWateringPeriod(1);
            } else if (radioButton.getId() == R.id.radioButton_WP_3) {
                setWateringPeriod(2);
            } else  setWateringPeriod(3);
        }
    }
    /*public void checkButton(View v) {
        int radioID = mRadioGroup.getCheckedRadioButtonId();
        mRadioButton = v.findViewById(radioID);
        Toast.makeText(OnePot.this, "Selected radio button: " + mRadioButton.getText(), Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_pot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_pot:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(OnePot.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_del_pot, null);
                final TextInputEditText mDelPot = mView.findViewById(R.id.textInputLayout_dialod_delPot);
                Button mChange = mView.findViewById(R.id.button_DelPot);

                mChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mDelPot.getText().toString().isEmpty()) {
                            delPot(getSha256(mDelPot.getText().toString()));
                        }
                    }
                });
                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void getDev() {
        Call<List<Pot>> call = SmartPot.getInstance().getSmartPotApi().getDev(SmartPot.getInstance().getUserToken(), mNameOfPotString);
        call.enqueue(new Callback<List<Pot>>() {
            @Override
            public void onResponse(Call<List<Pot>> call, Response<List<Pot>> response) {
                if (response.isSuccessful()) {
                    mPot = response.body();
                    mNameOfPOt.setText(mPot.get(0).getNameOfPot());
                    mDeviceToken.setText(mPot.get(0).getDeviceToken());
                    mAirTemperature.setText(mPot.get(0).getTempC().toString()+"'C");
                    mSoilMeisture.setText(mPot.get(0).getSm().toString());

                    if (mPot.get(0).getWateringPeriod() == 0) {
                        mWateringFrequency.setText("24 hours");
                    } else if (mPot.get(0).getWateringPeriod() == 1) {
                        mWateringFrequency.setText("48 hours");
                    } else if (mPot.get(0).getWateringPeriod() == 2) {
                        mWateringFrequency.setText("72 hours");
                    } else mWateringFrequency.setText("96 hours");

                    if (mPot.get(0).getSleepMode() == 0) {
                        mSleepMode.setText("1 hour");
                    } else if (mPot.get(0).getSleepMode() == 1) {
                        mSleepMode.setText("2 hours");
                    } else {
                        mSleepMode.setText("4 hours");
                    }

                    mThresholdSM.setText(mPot.get(0).getThresholdSM().toString());
                } else {
                    Toast.makeText(OnePot.this, "Код ошибки: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pot>> call, Throwable t) {
                Toast.makeText(OnePot.this, t.getMessage() + " ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setName(String name) {
        Call<String> call = SmartPot.getInstance().getSmartPotApi().setName(SmartPot.getInstance().getUserToken(), mPot.get(0).getDeviceToken(), name);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(OnePot.this, "Успешно изменилось ", Toast.LENGTH_SHORT).show();
                    getDev();
                    dialog.cancel();

                } else {
                    Toast.makeText(OnePot.this, "Код ошибки: " + response.code(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(OnePot.this, t.getMessage() + " ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void delPot(String password) {
        Call<String> call = SmartPot.getInstance().getSmartPotApi().delPot(SmartPot.getInstance().getUserToken(), mPot.get(0).getDeviceToken(), password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(OnePot.this, "Горшок успешно удален: ", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                    Intent intent = new Intent(OnePot.this, Activity_Pots.class);
                    startActivityForResult(intent, ADD_NOTE_REQUEST);

                } else {
                    Toast.makeText(OnePot.this, "Код ошибки: " + response.code(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(OnePot.this, t.getMessage() + " ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setSleepMode(int type) {
        Call<String> call = SmartPot.getInstance().getSmartPotApi().setSleepMode(SmartPot.getInstance().getUserToken(), mPot.get(0).getDeviceToken(), type);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(OnePot.this, "Успешно изменилось", Toast.LENGTH_SHORT).show();
                    getDev();
                    dialog.cancel();

                } else {
                    Toast.makeText(OnePot.this, "Код ошибки: " + response.code(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(OnePot.this, t.getMessage() + " ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setWateringPeriod(int type) {
        Call<String> call = SmartPot.getInstance().getSmartPotApi().setWateringPeriod(SmartPot.getInstance().getUserToken(), mPot.get(0).getDeviceToken(), type);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(OnePot.this, "Успешно изменилось", Toast.LENGTH_SHORT).show();
                    getDev();
                    dialog.cancel();

                } else {
                    Toast.makeText(OnePot.this, "Код ошибки: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(OnePot.this, t.getMessage() + " ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setThresholdSM(int type) {
        Call<String> call = SmartPot.getInstance().getSmartPotApi().setThresholdSM(SmartPot.getInstance().getUserToken(), mPot.get(0).getDeviceToken(), type);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(OnePot.this, "Успешно изменилось", Toast.LENGTH_SHORT).show();
                    getDev();
                    dialog.cancel();

                } else {
                    Toast.makeText(OnePot.this, "Код ошибки: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(OnePot.this, t.getMessage() + " ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
