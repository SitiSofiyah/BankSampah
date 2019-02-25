package com.example.intel.fireapp;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    private Button register ;
    private EditText nama, alamat, telp;
    DatabaseReference databasepengepul;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        databasepengepul = FirebaseDatabase.getInstance().getReference("pengepul_kecil");
        register = (Button) findViewById(R.id.register);

        nama = (EditText) findViewById(R.id.nama);
        alamat = (EditText) findViewById(R.id.alamat);
        telp = (EditText) findViewById(R.id.telp);
    }

    public void klik(View view) {
        addPengepul();
    }

    public void addPengepul(){
        String name = nama.getText().toString().trim();
        String address = alamat.getText().toString().trim();
        String telepon = telp.getText().toString().trim();
        if(!TextUtils.isEmpty(name) || !TextUtils.isEmpty(address) || !TextUtils.isEmpty(telepon)){
            String id = databasepengepul.push().getKey();
            pengepul_kecil pengepul = new pengepul_kecil(id, name,address,telepon);
            databasepengepul.child(id).setValue(pengepul);
            Toast.makeText(this,"success added",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"You should complete the textfield",Toast.LENGTH_LONG).show();
        }
    }



}
