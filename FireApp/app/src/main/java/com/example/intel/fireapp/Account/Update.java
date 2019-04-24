package com.example.intel.fireapp.Account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Anggota.Home_Anggota;
import com.example.intel.fireapp.Model.TambahGrup;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.PengepulKecil.HomePK;
import com.example.intel.fireapp.R;
import com.example.intel.fireapp.TukangRombeng.Home_tr;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Update extends AppCompatActivity {

    private EditText ed_Nama, ed_Alamat, ed_Telp, ed_Password;
    private Button bt_update;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        ed_Nama = (EditText) findViewById(R.id.ed_nama);
        ed_Alamat = (EditText) findViewById(R.id.ed_alamat);
        ed_Telp = (EditText) findViewById(R.id.ed_telp);
        ed_Password = (EditText) findViewById(R.id.ed_password);
        bt_update = (Button) findViewById(R.id.update);


        mDatabase = FirebaseDatabase.getInstance().getInstance().getReference();

        final Query query = mDatabase.child("users").orderByChild("id").equalTo(SaveSharedPreference.getId(getApplicationContext()));
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                User akun = dataSnapshot.getValue(User.class);

                        ed_Nama.setText(akun.getNama().toString());
                        ed_Alamat.setText(akun.getAlamat().toString());
                        ed_Telp.setText(akun.getTelp().toString());
                        ed_Password.setText(akun.getPassword().toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Canceled",Toast.LENGTH_SHORT).show();
            }
        });


        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


}
