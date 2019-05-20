package com.example.intel.fireapp.Anggota;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.intel.fireapp.Account.Bantuan;
import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Model.Pemberitahuan;
import com.example.intel.fireapp.Model.TambahGrup;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.Model.transaksi_anggota;
import com.example.intel.fireapp.PengepulKecil.HomePK;
import com.example.intel.fireapp.PengepulKecil.db_ReadAkun;
import com.example.intel.fireapp.PengepulKecil.tambahgrup;
import com.example.intel.fireapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

public class Home_Anggota extends AppCompatActivity {

    NotificationBadge nBadge;
    int a;
    DatabaseReference database;
    TextView nama;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference("pemberitahuan");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__anggota);

        nama = (TextView) findViewById(R.id.nama);

        database = FirebaseDatabase.getInstance().getReference();

        database.child("users").orderByChild("id").equalTo(SaveSharedPreference.getId(getApplicationContext())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    User user = noteDataSnapshot.getValue(User.class);
                    nama.setText(user.getNama()+", "+user.getLevel());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
            }
        });

        nBadge  = (NotificationBadge) findViewById(R.id.badge);
        final List<Pemberitahuan> listInfo= new ArrayList<>();
        ref.child(SaveSharedPreference.getId(getApplicationContext())).orderByChild("status").equalTo("send").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                     nBadge.setNumber((int)dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void info(View view) {
        Intent intent = new Intent(Home_Anggota.this,Infoamasi.class);
        startActivity(intent);
        finish();
    }

    public void bantuan(View view) {
        Intent intent = new Intent(Home_Anggota.this,Bantuan.class);
        startActivity(intent);
    }

    public void akun(View view) {
        Intent intent = new Intent(Home_Anggota.this,db_ReadAkun.class);
        startActivity(intent);
    }

    public void logout(View view) {
        SaveSharedPreference.setLoggedInAnggota(this,false);
        SaveSharedPreference.setId(this,null);
        Intent intent = new Intent(Home_Anggota.this,login.class);
        startActivity(intent);
        finish();
    }

    public void transaksi(View view) {
    }
}
