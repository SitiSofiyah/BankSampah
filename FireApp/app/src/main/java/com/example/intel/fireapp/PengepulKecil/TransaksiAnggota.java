package com.example.intel.fireapp.PengepulKecil;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.intel.fireapp.Account.Bantuan;
import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Model.Pemberitahuan;
import com.example.intel.fireapp.Model.TransaksiKeTR;
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
    RadioButton masuk, keluar;
    RadioGroup jenis;
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
        masuk = (RadioButton) findViewById(R.id.masuk);
        keluar = (RadioButton) findViewById(R.id.keluar);
        jenis = (RadioGroup) findViewById(R.id.jenis);
    }

    public void addTransaksi(View view) {
        int selectedId = jenis.getCheckedRadioButtonId();

        java.text.SimpleDateFormat fomat = new java.text.SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String curdate =  fomat.format(date).toString();

        final DatabaseReference anggota = FirebaseDatabase.getInstance().getReference("anggota").child(getIntent().getStringExtra("idGrup")).child(getIntent().getStringExtra("id"));
        final DatabaseReference notif = FirebaseDatabase.getInstance().getReference("pemberitahuan");

        final int totalHarga = Integer.parseInt(harga.getText().toString());
        final String keterangan = ket.getText().toString();

        meDatabase = FirebaseDatabase.getInstance().getReference("transaksi_anggota");
        int saldoLama = getIntent().getIntExtra("saldo",0);
        String id = meDatabase.push().getKey();

        transaksi_anggota ta = null;

        String idnotif = notif.push().getKey();
        Pemberitahuan pemberitahuan = null;

        if(selectedId == masuk.getId()){
            anggota.child("saldo").setValue(saldoLama+totalHarga);
            ta = new transaksi_anggota(id,getIntent().getStringExtra("id"),SaveSharedPreference.getId(getApplicationContext()),0,ket.getText().toString(),totalHarga,curdate );
            pemberitahuan = new Pemberitahuan(idnotif, SaveSharedPreference.getId(getApplicationContext()),getIntent().getStringExtra("id"),curdate,"Saldo anda bertambah sebesar Rp. "+totalHarga, "send");
            meDatabase.child(id).setValue(ta);
            notif.child(getIntent().getStringExtra("id")).child(idnotif).setValue(pemberitahuan);
        } else{
            if(saldoLama-totalHarga < 0){
                Toast.makeText(TransaksiAnggota.this,"Saldo anda tidak mencukupi !",Toast.LENGTH_LONG).show();
            }else{
                anggota.child("saldo").setValue(saldoLama-totalHarga);
                ta = new transaksi_anggota(id,getIntent().getStringExtra("id"),SaveSharedPreference.getId(getApplicationContext()),totalHarga,ket.getText().toString(),0,curdate );
                pemberitahuan = new Pemberitahuan(idnotif, SaveSharedPreference.getId(getApplicationContext()),getIntent().getStringExtra("id"),curdate,"Saldo anda berkurang sebesar Rp. "+totalHarga, "send");
                meDatabase.child(id).setValue(ta);
                notif.child(getIntent().getStringExtra("id")).child(idnotif).setValue(pemberitahuan);
            }
        }

        Intent intent = new Intent(TransaksiAnggota.this,DetailAnggota.class);
        intent.putExtra("idGrup", ""+getIntent().getStringExtra("idGrup"));
        intent.putExtra("id", ""+getIntent().getStringExtra("id"));
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.akun:
                Intent intent = new Intent(TransaksiAnggota.this, db_ReadAkun.class);
                startActivity(intent);
                return true;

            case R.id.help:
                Intent intentt = new Intent(TransaksiAnggota.this, Bantuan.class);
                startActivity(intentt);
                return true;

            case R.id.out:
                SaveSharedPreference.setLoggedInPK(getApplicationContext(), false);
                SaveSharedPreference.setId(getApplicationContext(), null);
                Intent intents = new Intent(TransaksiAnggota.this, login.class);
                startActivity(intents);
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
