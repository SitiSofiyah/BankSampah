package com.example.intel.fireapp.TukangRombeng;

import android.content.Intent;
import android.os.Handler;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.intel.fireapp.Account.Bantuan;
import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Adapter.AdapterTransaksiTR;
import com.example.intel.fireapp.Model.TransaksiKeTR;
import com.example.intel.fireapp.OnLoadMoreListener;
import com.example.intel.fireapp.PengepulKecil.TransaksiAnggota;
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

public class ListTransaksi extends AppCompatActivity {

    public RecyclerView recyclerListView;
    public AdapterTransaksiTR myAdapter;
    DatabaseReference transDB;
    List<TransaksiKeTR> listTransaksi= new ArrayList<>();
    LinearLayoutManager mLayoutmanajer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_transaksi);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tabungan Sampah");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerListView=(RecyclerView) findViewById(R.id.transaksiAnggota_list);
        mLayoutmanajer = new LinearLayoutManager(this);
        mLayoutmanajer.setReverseLayout(true);
        mLayoutmanajer.setStackFromEnd(true);
        recyclerListView.setLayoutManager(mLayoutmanajer);
        myAdapter= new AdapterTransaksiTR(recyclerListView,this,"TRtrans");
        viewTrans();
        recyclerListView.setAdapter(myAdapter);

        myAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (listTransaksi.size() <= 20) {
                    listTransaksi.add(null);
                    myAdapter.notifyItemInserted(listTransaksi.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listTransaksi.remove(listTransaksi.size() - 1);
                            myAdapter.notifyItemRemoved(listTransaksi.size());

                            //Generating more data
                            int index = listTransaksi.size();
                            int end = index + 1;
                            for (int i = index; i < end; i++) {
                                TransaksiKeTR transTr = new TransaksiKeTR();
                                listTransaksi.add(transTr);
                            }
                            myAdapter.notifyDataSetChanged();
                            myAdapter.setLoaded();
                        }
                    }, 5000);
                } else {
                    Toast.makeText(ListTransaksi.this, "Loading data completed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void viewTrans(){

        transDB = FirebaseDatabase.getInstance().getInstance().getReference();
        final Query query = transDB.child("transaksiTR").orderByChild("tanggal");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    TransaksiKeTR trans = data.getValue(TransaksiKeTR.class);
                    if(trans.getId_tr().equals(SaveSharedPreference.getId(getApplicationContext()))){
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
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.akun:
                Intent intents = new Intent(ListTransaksi.this, db_ReadAkun.class);
                startActivity(intents);
                return true;

            case R.id.help:
                Intent intentt = new Intent(ListTransaksi.this,Bantuan.class);
                startActivity(intentt);
                return true;

            case R.id.out:
                SaveSharedPreference.setLoggedInTR(getApplicationContext(), false);
                SaveSharedPreference.setId(getApplicationContext(), null);
                Intent intent = new Intent(ListTransaksi .this, login.class);
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
        Intent intent = new Intent(ListTransaksi.this,ListJualSampah.class);
        startActivity(intent);
    }

    public void sampah(View view) {
        Intent intent = new Intent(ListTransaksi.this,ListTransaksi.class);
        startActivity(intent);
    }
}
