package com.example.intel.fireapp.Account;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Anggota.Home_Anggota;
import com.example.intel.fireapp.PengepulKecil.db_ReadAkun;
import com.example.intel.fireapp.R;
import com.example.intel.fireapp.TukangRombeng.ListTransaksi;

import org.w3c.dom.Text;

public class Bantuan extends AppCompatActivity {
    private TextView bt_anggota, bt_pk, bt_tr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tabungan Sampah");
        setSupportActionBar(toolbar);

        bt_anggota = (TextView) findViewById(R.id.untukanggota);
        bt_pk = (TextView) findViewById(R.id.untukpengepul);
        bt_tr = (TextView) findViewById(R.id.untuktukang);

        bt_anggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bantuan.this, tatacaraanggota_update.class);
                startActivity(intent);
                finish();
            }
        });

        bt_pk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bantuan.this, pk_bantuan.class);
                startActivity(intent);
                finish();
            }

        });
        bt_tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bantuan.this, tr_bantuan.class);
                startActivity(intent);
                finish();
            }
        });

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
                Intent intents = new Intent(Bantuan.this, db_ReadAkun.class);
                startActivity(intents);
                return true;

            case R.id.help:
                Intent intentt = new Intent(Bantuan.this,Bantuan.class);
                startActivity(intentt);
                return true;

            case R.id.out:
                SaveSharedPreference.setLoggedInPK(getApplicationContext(), false);
                SaveSharedPreference.setId(getApplicationContext(), null);
                Intent intent = new Intent(Bantuan.this, login.class);
                startActivity(intent);
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }


    }

}
