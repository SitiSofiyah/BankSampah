package com.example.intel.fireapp;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.
        app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.intel.fireapp.PengepulKecil.ListPenawaran;
import com.example.intel.fireapp.PengepulKecil.ListTransaksiJualSampah;
import com.example.intel.fireapp.TukangRombeng.ListJualSampah;

public class MenuPenjualan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_penjualan);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tabungan Sampah");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void dijual(View view) {
        Intent intent = new Intent(MenuPenjualan.this, ListTransaksiJualSampah.class);
        startActivity(intent);
    }
    public void wait(View view) {
        Intent intent = new Intent(MenuPenjualan.this, sampahMenunggu.class);
        startActivity(intent);
    }
    public void deal(View view) {
        Intent intent = new Intent(MenuPenjualan.this, selesaiSampah.class);
        startActivity(intent);
    }
}
