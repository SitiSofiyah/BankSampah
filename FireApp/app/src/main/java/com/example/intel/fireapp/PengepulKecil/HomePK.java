package com.example.intel.fireapp.PengepulKecil;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;

import com.example.intel.fireapp.Account.Bantuan;
import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Model.Tawaran;
import com.example.intel.fireapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;

public class HomePK extends AppCompatActivity {

    NotificationBadge nBadge;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference("transaksiTR");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pk);

    }


    public void grup(View view) {
        Intent intent = new Intent(HomePK.this,tambahgrup.class);
        startActivity(intent);
    }

    public void order(View view) {
        Intent intent = new Intent(HomePK.this,transaksikeTR.class);
        startActivity(intent);
    }

    public void bantuan(View view) {
        Intent intent = new Intent(HomePK.this,Bantuan.class);
        startActivity(intent);
    }

    public void logout(View view) {
        SaveSharedPreference.setLoggedInPK(this,false);
        SaveSharedPreference.setId(this,null);
        Intent intent = new Intent(HomePK.this,login.class);
        startActivity(intent);
        finish();
    }

    public void akun(View view) {
        Intent intent = new Intent(HomePK.this,db_ReadAkun.class);
        startActivity(intent);
    }

    public void transaksi(View view) {
        Intent intentss = new Intent(HomePK.this,ListTransaksiAll.class);
        startActivity(intentss);
    }

    public void penawaran(View view) {
        Intent intentss = new Intent(HomePK.this,ListTransaksiJualSampah.class);
        startActivity(intentss);
    }
}
