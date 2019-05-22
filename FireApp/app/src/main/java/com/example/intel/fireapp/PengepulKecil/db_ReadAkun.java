package com.example.intel.fireapp.PengepulKecil;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.intel.fireapp.Account.Update;
import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class db_ReadAkun extends AppCompatActivity {

    /**
     * Mendefinisikan variable yang akan dipakai
     */
    private DatabaseReference database;
    TextView tvNama, tvAlamat, tvPass, tvTelp, tvJK, tvName, tvDesign,tvLocation;
    String level;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Mengeset layout
         */
        setContentView(R.layout.activity_db__read_akun);


//        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("Tabungan Sampah");
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            tvNama = (TextView) findViewById(R.id.tv_nama);
            tvAlamat = (TextView) findViewById(R.id.tv_alamat);
            tvTelp = (TextView) findViewById(R.id.tv_notlp);
            tvPass = (TextView) findViewById(R.id.tv_pass);
            tvJK = (TextView) findViewById(R.id.tv_jk);
            tvName = (TextView) findViewById(R.id.nama1);
            tvDesign = (TextView) findViewById(R.id.designation);
            tvLocation = (TextView) findViewById(R.id.location);

        database = FirebaseDatabase.getInstance().getReference();

        database.child("users").orderByChild("id").equalTo(SaveSharedPreference.getId(getApplicationContext())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    User user = noteDataSnapshot.getValue(User.class);
                    tvNama.setText(user.getNama());
                    tvAlamat.setText(user.getAlamat());
                    tvJK.setText(user.getJeniskel());
                    tvTelp.setText(user.getTelp());
                    tvPass.setText(user.getPassword());
                    level=user.getLevel().toString();
                    tvName.setText(user.getNama());
                    tvDesign.setText(user.getLevel());
                    tvLocation.setText(user.getAlamat().toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
            }
        });
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.akun:
//                Intent intents = new Intent(db_ReadAkun.this, db_ReadAkun.class);
//                startActivity(intents);
//                finish();
//                return true;
//
//            case R.id.help:
//                return true;
//
//            case R.id.out:
//                if(level.equals("Pengepul Kecil")){
//                    SaveSharedPreference.setLoggedInPK(getApplicationContext(), false);
//                }else if(level.equals("Tukang Rombeng")){
//                    SaveSharedPreference.setLoggedInTR(getApplicationContext(), false);
//                }else if(level.equals("Anggota")){
//                    SaveSharedPreference.setLoggedInAnggota(getApplicationContext(), false);
//                }
//                SaveSharedPreference.setId(getApplicationContext(), null);
//                Intent intent = new Intent(db_ReadAkun.this, login.class);
//                startActivity(intent);
//                finish();
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//
//        }
//    }

    public void edit(View view) {
        Intent i = new Intent(db_ReadAkun.this,Update.class);
        startActivity(i);
    }
}

