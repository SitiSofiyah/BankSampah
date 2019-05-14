
package com.example.intel.fireapp.PengepulKecil;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Model.Pemberitahuan;
import com.example.intel.fireapp.Model.transaksi_anggota;
import com.example.intel.fireapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class EditTransaksi extends AppCompatActivity {

    EditText harga, ket;
    int hrg;
    int hasil, mask, keluar;
    String id, date;
    private RadioGroup stats;
    private RadioButton masuk, dakel;
    DatabaseReference mdatabaseReference, saldodb, notifdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaksi);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tabungan Sampah");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        harga = (EditText) findViewById(R.id.harga);
        ket = (EditText) findViewById(R.id.ket);
        stats = (RadioGroup) findViewById(R.id.status);
        masuk = (RadioButton) findViewById(R.id.masuk);
        dakel = (RadioButton) findViewById(R.id.keluar);

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final Query query = databaseReference.child("transaksi_anggota").orderByChild("id_trans").equalTo(getIntent().getStringExtra("trans"));
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot transSnapshot : dataSnapshot.getChildren()) {
                    transaksi_anggota trans  = transSnapshot.getValue(transaksi_anggota.class);

                    if(trans.getMasuk()>0){
                        hrg = trans.getMasuk();
                        masuk.setChecked(true);
                        harga.setText(""+trans.getMasuk());
                    }else{
                        hrg=trans.getKeluar();
                        dakel.setChecked(true);
                        harga.setText(""+trans.getKeluar());
                    }
                    ket.setText(trans.getKeterangan().toString());
                    id = trans.getId_user().toString();
                    date = trans.getTanggal().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void simpanTransaksi(View view) {
        final String keterangan = ket.getText().toString();
        final String dana = harga.getText().toString();
        int danaint = Integer.parseInt(dana);
        notifdb = FirebaseDatabase.getInstance().getReference("pemberitahuan");
        String id = notifdb.push().getKey();
        mdatabaseReference = FirebaseDatabase.getInstance().getReference("transaksi_anggota").child(getIntent().getStringExtra("trans"));
        java.text.SimpleDateFormat fomat = new java.text.SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String curdate =  fomat.format(date).toString();
        int selectedId = stats.getCheckedRadioButtonId();
        if(selectedId == masuk.getId()){
            if(hrg>=danaint){
                Toast.makeText(EditTransaksi.this, "satu asli "+hrg+" tambahan "+danaint+" saldo "+getIntent().getIntExtra("saldo",0), Toast.LENGTH_SHORT).show();
                mask = hrg-danaint;
                hasil = getIntent().getIntExtra("saldo",0) - mask;
            } else if(hrg<danaint){
                Toast.makeText(EditTransaksi.this, "dua asli "+hrg+" tambahan "+danaint+" saldo "+getIntent().getIntExtra("saldo",0), Toast.LENGTH_SHORT).show();

                mask = danaint-hrg;
                hasil = getIntent().getIntExtra("saldo",0) + mask;
            }
            transaksi_anggota transag = new transaksi_anggota(getIntent().getStringExtra("trans"),getIntent().getStringExtra("id"),SaveSharedPreference.getId(getApplicationContext()),0,keterangan,danaint,curdate);
            mdatabaseReference.setValue(transag);

        } else if(selectedId==dakel.getId()){

            if (hrg > danaint) {
                keluar = hrg - danaint;
                hasil = Integer.parseInt(getIntent().getStringExtra("saldo")) + keluar;
            } else {
                keluar = danaint - hrg;
                hasil = Integer.parseInt(getIntent().getStringExtra("saldo")) - keluar;
            }

            transaksi_anggota transag = new transaksi_anggota(getIntent().getStringExtra("trans"),getIntent().getStringExtra("id"),SaveSharedPreference.getId(getApplicationContext()),danaint,keterangan,0,curdate);
            mdatabaseReference.setValue(transag);

        }
        Pemberitahuan pemberitahuan = new Pemberitahuan(id, SaveSharedPreference.getId(getApplicationContext()),getIntent().getStringExtra("id"),curdate,"Transaksi anda pada tanggal "+date+" telah diubah oleh admin", "send");
        saldodb = FirebaseDatabase.getInstance().getReference("anggota").child(getIntent().getStringExtra("idGrup")).child(id);
        saldodb.child("saldo").setValue(hasil);

        Toast.makeText(EditTransaksi.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EditTransaksi.this, ListTransaksiAnggota.class);
        intent.putExtra("trans", getIntent().getStringExtra("trans"));
        intent.putExtra("saldo", getIntent().getStringExtra("saldo"));
        intent.putExtra("idGrup", getIntent().getStringExtra("idGrup"));
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }


}
