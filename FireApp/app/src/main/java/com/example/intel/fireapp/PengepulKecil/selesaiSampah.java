package com.example.intel.fireapp.PengepulKecil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Adapter.AdapterTransaksiTR;
import com.example.intel.fireapp.Model.TransaksiKeTR;
import com.example.intel.fireapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class selesaiSampah extends AppCompatActivity {

    public RecyclerView recyclerListView;
    public AdapterTransaksiTR myAdapter;
    private DatabaseReference transDB;
    LinearLayoutManager mLayoutmanajer;
    List<TransaksiKeTR> listTransaksi= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_transaksi_jual_sampah);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tabungan Sampah");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerListView = (RecyclerView) findViewById(R.id.jualList);
        mLayoutmanajer = new LinearLayoutManager(this);
        mLayoutmanajer.setReverseLayout(true);
        mLayoutmanajer.setStackFromEnd(true);
        recyclerListView.setLayoutManager(mLayoutmanajer);
        myAdapter = new AdapterTransaksiTR(this, "PKselesai");
        viewTrans();
        recyclerListView.setAdapter(myAdapter);
    }

    public void viewTrans(){
        transDB = FirebaseDatabase.getInstance().getInstance().getReference();
        final Query query = transDB.child("transaksiTR").orderByChild("tanggal");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> getTrans = dataSnapshot.getChildren();
                for (DataSnapshot data : getTrans){
                    TransaksiKeTR trans = data.getValue(TransaksiKeTR.class);
                    if(trans.getId_pk().equals(SaveSharedPreference.getId(getApplicationContext()))&&trans.getStatus().equals("selesai")){
                        listTransaksi.add(trans);
                    }
                }
                displayUsers(listTransaksi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void displayUsers(List<TransaksiKeTR> ls){
        recyclerListView.setVisibility(View.VISIBLE);
        myAdapter.setData(ls);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Cari sesuatu....");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange( String s) {
                ArrayList<TransaksiKeTR> dataFilter= new ArrayList<>();
                for( TransaksiKeTR data : listTransaksi){
                    String nama = data.getTanggal().toLowerCase();
                    if(nama.contains(s.toLowerCase())){
                        dataFilter.add(data);
                    }

                }
                myAdapter.setFilter(dataFilter);
                return true;
            }
        });
        searchItem.setActionView(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.akun:
                Intent intent = new Intent(selesaiSampah.this, db_ReadAkun.class);
                startActivity(intent);
                return true;

            case R.id.help:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            case R.id.out:
                SaveSharedPreference.setLoggedInPK(getApplicationContext(), false);
                SaveSharedPreference.setId(getApplicationContext(), null);
                Intent intents = new Intent(selesaiSampah.this, login.class);
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

