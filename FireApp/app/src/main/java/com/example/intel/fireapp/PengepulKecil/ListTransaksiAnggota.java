package com.example.intel.fireapp.PengepulKecil;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.intel.fireapp.Account.Bantuan;
import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Adapter.GrupAdapter;
import com.example.intel.fireapp.Adapter.TransaksiAnggotaAdapter;
import com.example.intel.fireapp.Model.TambahGrup;
import com.example.intel.fireapp.Model.Tawaran;
import com.example.intel.fireapp.Model.transaksi_anggota;
import com.example.intel.fireapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListTransaksiAnggota extends AppCompatActivity {

    public RecyclerView recyclerListView;
    public TransaksiAnggotaAdapter myAdapter;
    private DatabaseReference transDB;
    LinearLayoutManager mLayoutmanajer;
    FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_transaksi_anggota);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tabungan Sampah");
        setSupportActionBar(toolbar);

        recyclerListView=(RecyclerView) findViewById(R.id.transaksiAnggota_list);
        mLayoutmanajer = new LinearLayoutManager(this);
        mLayoutmanajer.setReverseLayout(true);
        mLayoutmanajer.setStackFromEnd(true);
        recyclerListView.setLayoutManager(mLayoutmanajer);
        myAdapter= new TransaksiAnggotaAdapter(this,getIntent().getIntExtra("saldo",0),getIntent().getStringExtra("idGrup"),"pk");
        updateAdapter();
        recyclerListView.setAdapter(myAdapter);

        add = (FloatingActionButton) findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(ListTransaksiAnggota.this, TransaksiAnggota.class);
                intents.putExtra("idGrup", getIntent().getStringExtra("idGrup").toString());
                intents.putExtra("id", getIntent().getStringExtra("id").toString());
                intents.putExtra("saldo", getIntent().getIntExtra("saldo",0));
                startActivity(intents);
            }
        });
    }

    private void updateAdapter(){
        final List<transaksi_anggota> listTransaksi= new ArrayList<>();
        transDB = FirebaseDatabase.getInstance().getInstance().getReference();
        final Query query = transDB.child("transaksi_anggota").orderByChild("id_user").equalTo(getIntent().getStringExtra("id"));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    transaksi_anggota trans = data.getValue(transaksi_anggota.class);
                    listTransaksi.add(trans);
                }
                displayUsers(listTransaksi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean isEmpty(String s){
        //Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }

    public void displayUsers(List<transaksi_anggota> ls){
        recyclerListView.setVisibility(View.VISIBLE);
        myAdapter.setData(ls);
        myAdapter.notifyDataSetChanged();
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
                Intent intent = new Intent(ListTransaksiAnggota.this, db_ReadAkun.class);
                startActivity(intent);
                return true;

            case R.id.help:
                Intent intentt = new Intent(ListTransaksiAnggota.this, Bantuan.class);
                startActivity(intentt);
                return true;

            case R.id.out:
                SaveSharedPreference.setLoggedInPK(getApplicationContext(), false);
                SaveSharedPreference.setId(getApplicationContext(), null);
                Intent intents = new Intent(ListTransaksiAnggota.this, login.class);
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
