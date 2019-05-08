package com.example.intel.fireapp.PengepulKecil;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.intel.fireapp.Model.Anggota;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DetailAnggota extends AppCompatActivity {

    public TextView nama, jeniskel, alamat, saldo;
    public int saldoo;
    DatabaseReference mdatabaseReference = FirebaseDatabase.getInstance().getReference();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_anggota);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tabungan Sampah");
        setSupportActionBar(toolbar);

        nama = (TextView) findViewById(R.id.nama);
        alamat = (TextView) findViewById(R.id.alamat);
        jeniskel = (TextView) findViewById(R.id.jeniskel);
        saldo = (TextView) findViewById(R.id.saldo);

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final Query query = databaseReference.child("users").orderByChild("id").equalTo(getIntent().getStringExtra("id"));
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);

                    nama.setText(user.getNama().toString());
                    alamat.setText(user.getAlamat().toString());
                    jeniskel.setText(user.getJeniskel().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mdatabaseReference.child("anggota").child(getIntent().getStringExtra("idGrup")).child(getIntent().getStringExtra("id")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                saldoo = dataSnapshot.child("saldo").getValue(Integer.class);
                saldo.setText(""+saldoo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void tambahTransaksi(View view) {
        Intent intents = new Intent(DetailAnggota.this, TransaksiAnggota.class);
        intents.putExtra("idGrup", getIntent().getStringExtra("idGrup").toString());
        intents.putExtra("id", getIntent().getStringExtra("id").toString());
        intents.putExtra("saldo", saldoo);
        startActivity(intents);
    }

    public void lihatTransaksi(View view) {
        Intent intents = new Intent(DetailAnggota.this, ListTransaksiAnggota.class);
        intents.putExtra("idGrup", getIntent().getStringExtra("idGrup").toString());
        intents.putExtra("id", getIntent().getStringExtra("id").toString());
        intents.putExtra("saldo", saldoo);
        startActivity(intents);
    }
}
