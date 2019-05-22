package com.example.intel.fireapp.TukangRombeng;

import android.content.Intent;
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
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.PengepulKecil.HomePK;
import com.example.intel.fireapp.PengepulKecil.ListTransaksiAll;
import com.example.intel.fireapp.PengepulKecil.db_ReadAkun;
import com.example.intel.fireapp.PengepulKecil.tambahgrup;
import com.example.intel.fireapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home_tr extends AppCompatActivity {

    private DatabaseReference database;
    TextView nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tr);

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
    }

    public void bantuan(View view) {
        Intent intent = new Intent(Home_tr.this,Bantuan.class);
        startActivity(intent);
    }

    public void logout(View view) {
        SaveSharedPreference.setLoggedInTR(this,false);
        SaveSharedPreference.setId(this,null);
        Intent intent = new Intent(Home_tr.this,login.class);
        startActivity(intent);
        finish();
    }

    public void sampah(View view) {
        Intent intent = new Intent(Home_tr.this,ListJualSampah.class);
        startActivity(intent);
    }

    public void transaksi(View view) {
        Intent intent = new Intent(Home_tr.this,ListTransaksi.class);
        startActivity(intent);
    }

    public void akun(View view) {
        Intent intent = new Intent(Home_tr.this,db_ReadAkun.class);
        startActivity(intent);
    }
}
