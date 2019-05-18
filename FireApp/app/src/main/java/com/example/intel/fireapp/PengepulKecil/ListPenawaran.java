package com.example.intel.fireapp.PengepulKecil;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Adapter.AdapterTransaksiTR;
import com.example.intel.fireapp.Adapter.PenawaranAdapter;
import com.example.intel.fireapp.Model.Tawaran;
import com.example.intel.fireapp.Model.TransaksiKeTR;
import com.example.intel.fireapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListPenawaran extends AppCompatActivity {

    public RecyclerView recyclerListView;
    public PenawaranAdapter myAdapter;
    private DatabaseReference tawarDB, transdb;
    TextView tanggal, plastik, logam, kaca, kertas, lain, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_penawaran);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tabungan Sampah");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tanggal = (TextView) findViewById(R.id.tanggal);
        plastik = (TextView) findViewById(R.id.plastik);
        logam = (TextView) findViewById(R.id.logam);
        kaca = (TextView) findViewById(R.id.kaca);
        kertas = (TextView) findViewById(R.id.kertas);
        lain = (TextView) findViewById(R.id.lain);
        total = (TextView) findViewById(R.id.ket);

        viewRincian();

        recyclerListView = (RecyclerView) findViewById(R.id.tawarList);
        recyclerListView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new PenawaranAdapter(this);
        viewTrans();
        recyclerListView.setAdapter(myAdapter);
    }

    public void viewRincian(){
        transdb = FirebaseDatabase.getInstance().getReference("transaksiTR");
        final Query query = transdb.orderByChild("id_ordersampah").equalTo(getIntent().getStringExtra("id_order"));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    TransaksiKeTR trans = data.getValue(TransaksiKeTR.class);
                    tanggal.setText(trans.getTanggal().toString());
                    plastik.setText(trans.getPlastik_pk().toString());
                    logam.setText(trans.getLogam_pk().toString());
                    kaca.setText(trans.getKaca_pk().toString());
                    kertas.setText(trans.getKertas_pk().toString());
                    lain.setText(trans.getLainnya_pk().toString());
                    total.setText(trans.getTotal().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void viewTrans(){
        final List<Tawaran> listPenawaran= new ArrayList<>();
        tawarDB = FirebaseDatabase.getInstance().getReference();
        final Query query = tawarDB.child("penawaran").child(getIntent().getStringExtra("id_order"));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> getTawar = dataSnapshot.getChildren();
                for (DataSnapshot data : getTawar){
                    Tawaran tawars = data.getValue(Tawaran.class);
                    listPenawaran.add(tawars);
                }
                displayUsers(listPenawaran);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void displayUsers(List<Tawaran> ls){
        recyclerListView.setVisibility(View.VISIBLE);
        myAdapter.setData(ls);
        myAdapter.notifyDataSetChanged();
    }

}
