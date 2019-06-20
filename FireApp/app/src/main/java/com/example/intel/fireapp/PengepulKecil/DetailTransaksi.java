package com.example.intel.fireapp.PengepulKecil;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intel.fireapp.Account.Bantuan;
import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.Model.transaksi_anggota;
import com.example.intel.fireapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;

public class DetailTransaksi extends AppCompatActivity {

    public TextView tanggal, keterangan, rincian;
    DatabaseReference mdatabaseReference = FirebaseDatabase.getInstance().getReference();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tabungan Sampah");
        setSupportActionBar(toolbar);

        tanggal = (TextView) findViewById(R.id.tanggal);
        keterangan = (TextView) findViewById(R.id.ket);
        rincian = (TextView) findViewById(R.id.rincian);

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final Query query = databaseReference.child("transaksi_anggota").orderByChild("id_trans").equalTo(getIntent().getStringExtra("trans"));
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot transSnapshot : dataSnapshot.getChildren()) {
                    transaksi_anggota trans  = transSnapshot.getValue(transaksi_anggota.class);
                    NumberFormat nf = NumberFormat.getInstance();
                    if(trans.getMasuk()>0){
                        rincian.setText("Dana Masuk Sebesar Rp. "+nf.format(trans.getMasuk())+",-");
                    }else{
                        rincian.setText("Dana Keluar Sebesar Rp. "+nf.format(trans.getKeluar())+",-");
                    }
                    tanggal.setText(trans.getTanggal().toString());
                    keterangan.setText(trans.getKeterangan().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void editTransaksi(View view) {
        Intent intent = new Intent(DetailTransaksi.this, EditTransaksi.class);
        intent.putExtra("saldo",getIntent().getIntExtra("saldo",0));
        intent.putExtra("trans",getIntent().getStringExtra("trans"));
        intent.putExtra("idGrup",getIntent().getStringExtra("idGrup"));
        intent.putExtra("id",getIntent().getStringExtra("id"));
        Toast.makeText(getApplicationContext(),"Saldo "+getIntent().getIntExtra("saldo",0),Toast.LENGTH_SHORT).show();

        startActivity(intent);
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
                Intent intent = new Intent(DetailTransaksi.this, db_ReadAkun.class);
                startActivity(intent);
                return true;

            case R.id.help:
                Intent intentt = new Intent(DetailTransaksi.this, Bantuan.class);
                startActivity(intentt);
                return true;

            case R.id.out:
                SaveSharedPreference.setLoggedInPK(getApplicationContext(), false);
                SaveSharedPreference.setId(getApplicationContext(), null);
                Intent intents = new Intent(DetailTransaksi.this, login.class);
                startActivity(intents);
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


}
