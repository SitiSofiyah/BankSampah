package com.example.intel.fireapp.Account;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Anggota.Home_Anggota;
import com.example.intel.fireapp.R;

public class tr_bantuan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__anggota);
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
                SaveSharedPreference.setLoggedInPK(getApplicationContext(), false);
                SaveSharedPreference.setId(getApplicationContext(), null);
                Intent intent = new Intent(tr_bantuan.this, login.class);
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
