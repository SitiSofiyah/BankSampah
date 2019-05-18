package com.example.intel.fireapp.PengepulKecil;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Model.Anggota;
import com.example.intel.fireapp.Model.Pemberitahuan;
import com.example.intel.fireapp.Model.TambahGrup;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.Model.transaksi_anggota;
import com.example.intel.fireapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TambahTransaksiAll extends AppCompatActivity {

    TextView identitas;
    RadioButton masuk, keluar;
    RadioGroup jenis;
    EditText harga, keterangan;
    DatabaseReference meDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_transaksi_all);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Transaksi Anggota");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        identitas = (TextView) findViewById(R.id.identitas);
        masuk = (RadioButton) findViewById(R.id.masuk);
        keluar = (RadioButton) findViewById(R.id.keluar);
        jenis = (RadioGroup) findViewById(R.id.jenis);
        harga = (EditText) findViewById(R.id.harga);
        keterangan = (EditText) findViewById(R.id.keterangan);

        identitas.setText(getIntent().getStringExtra("nama")+", "+getIntent().getStringExtra("alamat"));

    }

    public void addTransaksi(View view) {
        java.text.SimpleDateFormat fomat = new java.text.SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String curdate =  fomat.format(date).toString();

        final DatabaseReference anggota = FirebaseDatabase.getInstance().getReference("anggota").child(getIntent().getStringExtra("idGrup")).child(getIntent().getStringExtra("id"));
        final DatabaseReference notif = FirebaseDatabase.getInstance().getReference("pemberitahuan");

        int selectedId = jenis.getCheckedRadioButtonId();

        final int totalHarga = Integer.parseInt(harga.getText().toString());
        final String ket = keterangan.getText().toString();

        meDatabase = FirebaseDatabase.getInstance().getReference("transaksi_anggota");
        String id = meDatabase.push().getKey();
        String idnotif = notif.push().getKey();
        int saldoLama = getIntent().getIntExtra("saldo",0);
        transaksi_anggota ta;
        Pemberitahuan pemberitahuan;
        if(selectedId == masuk.getId()){
            anggota.child("saldo").setValue(saldoLama+totalHarga);
            ta = new transaksi_anggota(id,getIntent().getStringExtra("id"),SaveSharedPreference.getId(getApplicationContext()),0,ket,totalHarga,curdate );
            pemberitahuan = new Pemberitahuan(idnotif, SaveSharedPreference.getId(getApplicationContext()),getIntent().getStringExtra("id"),curdate,"Saldo anda bertambah sebesar Rp. "+totalHarga, "send");
        } else{
            anggota.child("saldo").setValue(saldoLama-totalHarga);
            ta = new transaksi_anggota(id,getIntent().getStringExtra("id"),SaveSharedPreference.getId(getApplicationContext()),totalHarga,ket,0,curdate );
            pemberitahuan = new Pemberitahuan(idnotif, SaveSharedPreference.getId(getApplicationContext()),getIntent().getStringExtra("id"),curdate,"Saldo anda berkurang sebesar Rp. "+totalHarga, "send");
        }

        meDatabase.child(id).setValue(ta);
        notif.child(getIntent().getStringExtra("id")).child(idnotif).setValue(pemberitahuan);

        Intent intent = new Intent(TambahTransaksiAll.this,ListTransaksiAll.class);
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
                Intent intent = new Intent(TambahTransaksiAll.this, db_ReadAkun.class);
                startActivity(intent);
                return true;

            case R.id.help:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            case R.id.out:
                SaveSharedPreference.setLoggedInPK(getApplicationContext(), false);
                SaveSharedPreference.setId(getApplicationContext(), null);
                Intent intents = new Intent(TambahTransaksiAll.this, login.class);
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
