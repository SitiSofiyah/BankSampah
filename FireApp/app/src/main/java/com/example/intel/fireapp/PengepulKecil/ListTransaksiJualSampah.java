package com.example.intel.fireapp.PengepulKecil;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Adapter.AdapterTransaksiTR;
import com.example.intel.fireapp.Adapter.TransaksiAnggotaAdapter;
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

public class ListTransaksiJualSampah extends AppCompatActivity {

    public RecyclerView recyclerListView;
    public AdapterTransaksiTR myAdapter;
    private DatabaseReference transDB;

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
        recyclerListView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new AdapterTransaksiTR(this, "PKJS");
        viewTrans();
        recyclerListView.setAdapter(myAdapter);
    }

    public void viewTrans(){
        final List<TransaksiKeTR> listTransaksi= new ArrayList<>();
        transDB = FirebaseDatabase.getInstance().getInstance().getReference();
        final Query query = transDB.child("transaksiTR").orderByChild("id_pk").equalTo(SaveSharedPreference.getId(getApplicationContext()));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> getTrans = dataSnapshot.getChildren();
                for (DataSnapshot data : getTrans){
                    TransaksiKeTR trans = data.getValue(TransaksiKeTR.class);
                    listTransaksi.add(trans);
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
}
