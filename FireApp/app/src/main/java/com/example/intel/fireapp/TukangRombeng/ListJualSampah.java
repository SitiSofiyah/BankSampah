package com.example.intel.fireapp.TukangRombeng;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Adapter.AdapterTransaksiTR;
import com.example.intel.fireapp.Model.Anggota;
import com.example.intel.fireapp.Model.TambahGrup;
import com.example.intel.fireapp.Model.TransaksiKeTR;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.PengepulKecil.db_ReadAkun;
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

public class ListJualSampah extends AppCompatActivity {

    public RecyclerView recyclerListView;
    public AdapterTransaksiTR myAdapter;
    DatabaseReference transDB;
    LinearLayoutManager mLayoutmanajer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_jual_sampah);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tabungan Sampah");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerListView=(RecyclerView) findViewById(R.id.jualList);
        mLayoutmanajer = new LinearLayoutManager(this);
        mLayoutmanajer.setReverseLayout(true);
        mLayoutmanajer.setStackFromEnd(true);
        recyclerListView.setLayoutManager(mLayoutmanajer);
        myAdapter= new AdapterTransaksiTR(this,"TRJS");
        viewTrans();
        recyclerListView.setAdapter(myAdapter);


    }

    public void viewTrans(){
        final List<TransaksiKeTR> listTransaksi= new ArrayList<>();
        transDB = FirebaseDatabase.getInstance().getInstance().getReference();
        final Query query = transDB.child("transaksiTR").orderByChild("tanggal");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> getTrans = dataSnapshot.getChildren();
                for (DataSnapshot data : getTrans){
                    TransaksiKeTR trans = data.getValue(TransaksiKeTR.class);
                    if(trans.getStatus().equals("belum")){
                        listTransaksi.add(trans);
                    }
                }

                for (int i = 0; i < listTransaksi.size(); i++) {
                    final TransaksiKeTR tr = listTransaksi.get(i);
                    transDB.child("penawaran").child(tr.getId_ordersampah()).child(SaveSharedPreference.getId(getApplicationContext())).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                listTransaksi.remove(tr);
                            }
                            displayUsers(listTransaksi);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

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
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.akun:
                Intent intents = new Intent(ListJualSampah.this, db_ReadAkun.class);
                startActivity(intents);
                return true;

            case R.id.help:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            case R.id.out:
                SaveSharedPreference.setLoggedInTR(getApplicationContext(), false);
                SaveSharedPreference.setId(getApplicationContext(), null);
                Intent intent = new Intent(ListJualSampah.this, login.class);
                startActivity(intent);
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void transaksi(View view) {
        Intent intent = new Intent(ListJualSampah.this,ListJualSampah.class);
        startActivity(intent);
    }

    public void sampah(View view) {
        Intent intent = new Intent(ListJualSampah.this,ListTransaksi.class);
        startActivity(intent);
    }
}
