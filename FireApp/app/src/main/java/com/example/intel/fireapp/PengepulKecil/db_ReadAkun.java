package com.example.intel.fireapp.PengepulKecil;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.intel.fireapp.Account.Update;
import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Adapter.AdapterRecycleViewAkun;
import com.example.intel.fireapp.Anggota.Home_Anggota;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class db_ReadAkun extends AppCompatActivity {

    /**
     * Mendefinisikan variable yang akan dipakai
     */
    private DatabaseReference database;
    TextView tvNama, tvAlamat, tvPass, tvTelp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Mengeset layout
         */
        setContentView(R.layout.activity_db__read_akun);


        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tabungan Sampah");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            tvNama = (TextView) findViewById(R.id.tv_nama);
            tvPass = (TextView) findViewById(R.id.tv_JK);
            tvAlamat = (TextView) findViewById(R.id.tv_Alamat);
            tvTelp = (TextView) findViewById(R.id.tv_Telepon);




        database = FirebaseDatabase.getInstance().getReference();

        /**
         * Mengambil data barang dari Firebase Realtime DB
         */
        database.child("users").orderByChild("id").equalTo(SaveSharedPreference.getId(getApplicationContext())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                /**
                 * Saat ada data baru, masukkan datanya ke ArrayList
                 */

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    User user = noteDataSnapshot.getValue(User.class);
                    //uaer.setKey(noteDataSnapshot.getKey());
                    tvNama.setText(user.getNama());
                    tvAlamat.setText(user.getAlamat());
                    tvTelp.setText(user.getTelp());
                    tvPass.setText(user.getPassword());



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                /**
                 * Kode ini akan dipanggil ketika ada error dan
                 * pengambilan data gagal dan memprint error nya
                 * ke LogCat
                 */
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
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
                // User chose the "Settings" item, show the app settings UI...
                Intent intents = new Intent(db_ReadAkun.this, db_ReadAkun.class);
                startActivity(intents);
                finish();
                return true;

            case R.id.help:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            case R.id.out:
                SaveSharedPreference.setLoggedInPK(getApplicationContext(), false);
                SaveSharedPreference.setId(getApplicationContext(), null);
                Intent intent = new Intent(db_ReadAkun.this, login.class);
                startActivity(intent);
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void edit(View view) {
        Intent i = new Intent(db_ReadAkun.this,Update.class);
        startActivity(i);
    }
}

