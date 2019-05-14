package com.example.intel.fireapp.PengepulKecil;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Model.Pemberitahuan;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.Model.transaksi_anggota;
import com.example.intel.fireapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class TransaksiAnggota extends AppCompatActivity {

    public EditText harga, ket;
    DatabaseReference meDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_anggota);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tabungan Sampah");
        setSupportActionBar(toolbar);

        harga = (EditText) findViewById(R.id.harga);
        ket = (EditText) findViewById(R.id.keterangan);
    }

    public void addTransaksi(View view) {



        java.text.SimpleDateFormat fomat = new java.text.SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String curdate =  fomat.format(date).toString();

        final DatabaseReference anggota = FirebaseDatabase.getInstance().getReference("anggota").child(getIntent().getStringExtra("idGrup")).child(getIntent().getStringExtra("id"));
        final DatabaseReference notif = FirebaseDatabase.getInstance().getReference("pemberitahuan");



        final int totalHarga = Integer.parseInt(harga.getText().toString());
        final String keterangan = ket.getText().toString();

        meDatabase = FirebaseDatabase.getInstance().getReference("transaksi_anggota");
        int saldoLama = getIntent().getIntExtra("saldo",0);

        anggota.child("saldo").setValue(saldoLama+totalHarga);
        String id = meDatabase.push().getKey();

        transaksi_anggota ta = new transaksi_anggota(id,getIntent().getStringExtra("id"),SaveSharedPreference.getId(getApplicationContext()),0,keterangan,totalHarga,curdate );

        meDatabase.child(id).setValue(ta);

        String idnotif = notif.push().getKey();
        Pemberitahuan pemberitahuan = new Pemberitahuan(idnotif, SaveSharedPreference.getId(getApplicationContext()),getIntent().getStringExtra("id"),curdate,"Transaksi anda bertambah sebesar Rp. "+totalHarga, "send");

        Intent intent = new Intent(TransaksiAnggota.this,DetailAnggota.class);
        intent.putExtra("idGrup", ""+getIntent().getStringExtra("idGrup"));
        intent.putExtra("id", ""+getIntent().getStringExtra("id"));
        startActivity(intent);
        finish();
    }
}
