package com.example.intel.fireapp.PengepulKecil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Account.register;
import com.example.intel.fireapp.R;

public class HomePK extends AppCompatActivity {

    private Button btgrup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pk);
    }


    public void grup(View view) {
        Intent intent = new Intent(HomePK.this,tambahgrup.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity(intent);
        finish();
    }

    public void order(View view) {
    }

    public void transaksi(View view) {
    }
}
