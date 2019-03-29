package com.example.intel.fireapp.PengepulKecil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;

import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Account.register;
import com.example.intel.fireapp.R;

public class HomePK extends AppCompatActivity {

    private Button btgrup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pk);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tabungan Sampah");
        setSupportActionBar(toolbar);
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
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.help:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            case R.id.out:
                SharedPreferences preferences =getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                finish();
                Intent intent = new Intent(HomePK.this,login.class);
                startActivity(intent);
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    public void grup(View view) {
        Intent intent = new Intent(HomePK.this,tambahgrup.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity(intent);
        finish();
    }

    public void order(View view) {
    }

    public void transaksi(View view) {
    }
}
