package com.example.intel.fireapp.Account;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.intel.fireapp.Anggota.Home_Anggota;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.PengepulKecil.HomePK;
import com.example.intel.fireapp.R;
import com.example.intel.fireapp.TukangRombeng.Home_tr;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.nio.file.FileVisitResult;
import java.util.ArrayList;
import java.util.List;

public class register extends AppCompatActivity {

     private Button regis;
     private Spinner levell, jekel;
     private EditText nama, telp, alamat, password;
     private DatabaseReference meDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // Get reference of widgets from XML layout
        nama = (EditText) findViewById(R.id.nama) ;
        telp = (EditText) findViewById(R.id.telp) ;
        alamat = (EditText) findViewById(R.id.alamat) ;
        password = (EditText) findViewById(R.id.password) ;
         levell = (Spinner) findViewById(R.id.level);
         jekel = (Spinner) findViewById(R.id.jk);
        regis = (Button) findViewById(R.id.register);

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                String user = nama.getText().toString();
                final Query query = databaseReference.child("users").orderByChild("nama").equalTo(user);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            Toast.makeText(register.this, "Username sudah tersedia", Toast.LENGTH_LONG).show();
                        }else {
                            createAccount();
                            Toast.makeText(register.this, "Register Berhasil", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(register.this,login.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public void createAccount(){

        final String name = nama.getText().toString();
        final String address = alamat.getText().toString();
        final String telpon = telp.getText().toString();
        final String pass = password.getText().toString();
        final String level = levell.getSelectedItem().toString();
        final String jk = jekel.getSelectedItem().toString();

        meDatabase = FirebaseDatabase.getInstance().getReference("users");

        String id = meDatabase.push().getKey();



        User user = new User(id, name,address, telpon, jk, level, pass);

        meDatabase.child(id).setValue(user);


    }
}




