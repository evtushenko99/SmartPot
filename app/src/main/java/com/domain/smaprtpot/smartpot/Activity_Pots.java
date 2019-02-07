package com.domain.smaprtpot.smartpot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.domain.smaprtpot.smartpot.model.Pot;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class Activity_Pots extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    private ArrayList<itemForPot> mItemForPots;
    private RecyclerView mRecyclerView;
    private ItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Pot> mPots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pots);
        getAllDev();

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_pot);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Activity_Pots.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_add_pot, null);
                final TextInputEditText mDeviceToken = mView.findViewById(R.id.textInputLayoutDeviceToken);
                Button mAdd = mView.findViewById(R.id.button_AddPot);

                mAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mDeviceToken.getText().toString().isEmpty()) {
                            regPot(mDeviceToken.getText().toString());
                        }
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

    }


    public void insertItem(int position) {
        mItemForPots.add(position, new itemForPot(/*R.drawable.ic_android, */"New Item at Position" + position/*, "This is line 2"*/));
        mAdapter.notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mItemForPots.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void changeItem(int position, String text) {
        mItemForPots.get(position).changeText1(text);
        mAdapter.notifyItemChanged(position);
    }

    public void createExampleList() {
        mItemForPots = new ArrayList<>();
        for (Pot pot : mPots) {
            mItemForPots.add(new itemForPot(pot.getNameOfPot()));
        }
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ItemAdapter(mItemForPots);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClivkListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(Activity_Pots.this, OnePot.class);
                intent.putExtra("deviceToken", mPots.get(position).getDeviceToken());
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }

            //@Override
            //public void onDeleteClick(int position) {
                //removeItem(position);
            //}
        });
    }

    public void getAllDev() {
        Call<List<Pot>> call = SmartPot.getInstance().getSmartPotApi().getAlldev(SmartPot.getInstance().getUserToken());
        call.enqueue(new Callback<List<Pot>>() {
            @Override
            public void onResponse(Call<List<Pot>> call, Response<List<Pot>> response) {
                if (response.isSuccessful()) {
                    mPots = response.body();
                    if (mPots.size() != 0) {
                        createExampleList();
                        buildRecyclerView();
                    }
                } else {
                    Toast.makeText(Activity_Pots.this, "Код ошибки: " + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Pot>> call, Throwable t) {
                Toast.makeText(Activity_Pots.this, t.getMessage() + " ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void regPot(String deviceToken) {
        Call<String> call = SmartPot.getInstance().getSmartPotApi().regPot(deviceToken, SmartPot.getInstance().getUserToken());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    getAllDev();
                    Toast.makeText(Activity_Pots.this, "Успешно добавилось: ", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Activity_Pots.this, "Этот девайс уже занят. Код ошибки: " + response.code(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Activity_Pots.this, t.getMessage() + " ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
