package com.example.intel.fireapp.PengepulKecil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.example.intel.fireapp.Account.Bantuan;
import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Adapter.TransaksiAnggotaAdapter;
import com.example.intel.fireapp.Adapter.TransaksiAnggotaAllAdapter;
import com.example.intel.fireapp.Model.Anggota;
import com.example.intel.fireapp.Model.TambahGrup;
import com.example.intel.fireapp.Model.User;
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

public class ListTransaksiAll extends AppCompatActivity {

    public RecyclerView recyclerListView;
    public TransaksiAnggotaAllAdapter myAdapter;
    private DatabaseReference  transDB;
    FloatingActionButton add;
    List<transaksi_anggota> listTransaksi = new ArrayList<>();
    List<User> listAnggota = new ArrayList<>();
    LinearLayoutManager mLayoutmanajer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_transaksi_anggota);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tabungan Sampah");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        add = (FloatingActionButton) findViewById(R.id.add);
        recyclerListView=(RecyclerView) findViewById(R.id.transaksiAnggota_list);

        mLayoutmanajer = new LinearLayoutManager(this);
        mLayoutmanajer.setReverseLayout(true);
        mLayoutmanajer.setStackFromEnd(true);
        recyclerListView.setLayoutManager(mLayoutmanajer);
        myAdapter= new TransaksiAnggotaAllAdapter(this);
        getTrans();
        recyclerListView.setAdapter(myAdapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListTransaksiAll.this, PilihAnggota.class);
                startActivity(intent);
            }
        });

    }

    private void getTrans() {
        transDB = FirebaseDatabase.getInstance().getInstance().getReference();
        final Query query = transDB.child("transaksi_anggota").orderByChild("id_pk").equalTo(SaveSharedPreference.getId(getApplicationContext()));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    transaksi_anggota trans = data.getValue(transaksi_anggota.class);
                    listTransaksi.add(trans);
                }

                displayUsers(listTransaksi);

                for (int i = 0; i < listTransaksi.size(); i++) {
                    final transaksi_anggota anggota = listTransaksi.get(i);
                    transDB.child("users").orderByChild("id").equalTo(anggota.getId_user()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> getUser = dataSnapshot.getChildren();
                            for (DataSnapshot data : getUser){
                                User user = data.getValue(User.class);
                                listAnggota.add(user);
                            }
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
                final ArrayList<transaksi_anggota> dataFilter= new ArrayList<>();
                for( transaksi_anggota data : listTransaksi){
                    String tgl = data.getTanggal().toLowerCase();
                    String nama = getName(data.getId_user()).toLowerCase();
                        if(tgl.contains(s.toLowerCase())||nama.contains(s.toLowerCase())){
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
                Intent intent = new Intent(ListTransaksiAll.this, db_ReadAkun.class);
                startActivity(intent);
                return true;

            case R.id.help:
                Intent intentt = new Intent(ListTransaksiAll.this, Bantuan.class);
                startActivity(intentt);
                return true;

            case R.id.out:
                SaveSharedPreference.setLoggedInPK(getApplicationContext(), false);
                SaveSharedPreference.setId(getApplicationContext(), null);
                Intent intents = new Intent(ListTransaksiAll.this, login.class);
                startActivity(intents);
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public String getName(String id){

        String namaa = "";
        for( User data : listAnggota){
            if(data.getId().toString().equals(id)){
                namaa = data.getNama().toString();
            }
        }
        return namaa;
    }
}
