package com.example.intel.fireapp.Account;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Anggota.Home_Anggota;
import com.example.intel.fireapp.Model.TambahGrup;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.PengepulKecil.HomePK;
import com.example.intel.fireapp.PengepulKecil.db_ReadAkun;
import com.example.intel.fireapp.R;
import com.example.intel.fireapp.TukangRombeng.Home_tr;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.text.TextUtils.isEmpty;

public class Update extends AppCompatActivity {

    private EditText ed_Nama, ed_Alamat, ed_Telp, ed_Password;
    private Button bt_update;
    private DatabaseReference mDatabase,database;
    String nama ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        ed_Nama = (EditText) findViewById(R.id.ed_nama);
        ed_Alamat = (EditText) findViewById(R.id.ed_alamat);
        ed_Telp = (EditText) findViewById(R.id.ed_telp);
        ed_Password = (EditText) findViewById(R.id.ed_password);
        bt_update = (Button) findViewById(R.id.update);

        database = FirebaseDatabase.getInstance().getReference();

        database.child("users").orderByChild("id").equalTo(SaveSharedPreference.getId(getApplicationContext())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    User user = noteDataSnapshot.getValue(User.class);
                    ed_Nama.setText(user.getNama());
                    ed_Alamat.setText(user.getAlamat());
                    ed_Telp.setText(user.getTelp());
                    ed_Password.setText(user.getPassword());
                    nama = user.getNama().toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getInstance().getReference("users").child(SaveSharedPreference.getId(getApplicationContext()));


        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.child("users").orderByChild("nama").equalTo(ed_Nama.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                            if(ed_Nama.getText().toString().isEmpty() || ed_Alamat.getText().toString().isEmpty() ||
                                    ed_Password.getText().toString().isEmpty() || ed_Telp.getText().toString().isEmpty())
                            {
                                Toast.makeText(Update.this, "Lengkapi data diri anda", Toast.LENGTH_LONG).show();
                            }else if(dataSnapshot.exists()&&!(ed_Nama.getText().toString().equals(nama))){
                                Toast.makeText(Update.this, "Username sudah ada", Toast.LENGTH_LONG).show();
                            }else{
                                mDatabase.child("nama").setValue(ed_Nama.getText().toString());
                                mDatabase.child("password").setValue(ed_Password.getText().toString());
                                mDatabase.child("alamat").setValue(ed_Alamat.getText().toString());
                                mDatabase.child("telp").setValue(ed_Telp.getText().toString());

                                Toast.makeText(Update.this, "Data berhasil di Edit", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(Update.this, db_ReadAkun.class);
                                startActivity(intent);
                            }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());
                    }
                });
            }
        });
    }

}



