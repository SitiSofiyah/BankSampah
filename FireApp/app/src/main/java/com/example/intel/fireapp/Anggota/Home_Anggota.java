package com.example.intel.fireapp.Anggota;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.intel.fireapp.PengepulKecil.HomePK;
import com.example.intel.fireapp.PengepulKecil.tambahgrup;
import com.example.intel.fireapp.R;

public class Home_Anggota extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__anggota);
    }

    public void info(View view) {
        Intent intent = new Intent(Home_Anggota.this,Infoamasi.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity(intent);
        finish();
    }
}
