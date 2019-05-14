package com.example.intel.fireapp.PengepulKecil;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
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

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Adapter.GrupAdapter;
import com.example.intel.fireapp.Adapter.TransaksiAnggotaAdapter;
import com.example.intel.fireapp.Model.TambahGrup;
import com.example.intel.fireapp.Model.transaksi_anggota;
import com.example.intel.fireapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class ListTransaksiAnggota extends AppCompatActivity {

    public RecyclerView recyclerListView;
    public TransaksiAnggotaAdapter myAdapter;
    private DatabaseReference transDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_transaksi_anggota);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tabungan Sampah");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toast.makeText(getApplicationContext(),"Saldo "+getIntent().getIntExtra("saldo",0),Toast.LENGTH_SHORT).show();

        recyclerListView=(RecyclerView) findViewById(R.id.transaksiAnggota_list);
        recyclerListView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter= new TransaksiAnggotaAdapter(this,getIntent().getIntExtra("saldo",0),getIntent().getStringExtra("idGrup"));
        updateAdapter();
        recyclerListView.setAdapter(myAdapter);
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
                Intent intent = new Intent(ListTransaksiAnggota.this, login.class);
                startActivity(intent);
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void updateAdapter(){
        final List<transaksi_anggota> listTransaksi= new ArrayList<>();
        transDB = FirebaseDatabase.getInstance().getInstance().getReference();
        final Query query = transDB.child("transaksi_anggota").orderByChild("id_user").equalTo(getIntent().getStringExtra("id"));
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listTransaksi.add(dataSnapshot.getValue(transaksi_anggota.class));
                displayUsers(listTransaksi);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Canceled",Toast.LENGTH_SHORT).show();
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



}