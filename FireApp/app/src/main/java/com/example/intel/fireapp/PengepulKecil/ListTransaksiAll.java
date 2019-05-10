package com.example.intel.fireapp.PengepulKecil;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.example.intel.fireapp.Adapter.TransaksiAnggotaAdapter;
import com.example.intel.fireapp.Adapter.TransaksiAnggotaAllAdapter;
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
    private DatabaseReference grupDb, anggotaDb, transDB;
    public ArrayList<String> grup = new ArrayList<>();
    public ArrayList<String> anggota = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_transaksi_anggota);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tabungan Sampah");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerListView=(RecyclerView) findViewById(R.id.transaksiAnggota_list);
        recyclerListView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter= new TransaksiAnggotaAllAdapter(this);
        getGrup();
        Toast.makeText(ListTransaksiAll.this,"ada "+grup.size(),Toast.LENGTH_LONG).show();
//        getAnggota();
//        updateAdapter();
        recyclerListView.setAdapter(myAdapter);

    }

    private void getGrup() {
        grupDb = FirebaseDatabase.getInstance().getInstance().getReference();
        final Query query = grupDb.child("grup").orderByChild("id").equalTo(SaveSharedPreference.getId(getApplicationContext()));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot grupSnapshot : dataSnapshot.getChildren()) {
                    TambahGrup grups = grupSnapshot.getValue(TambahGrup.class);
                    grup.add(grups.getId_grup());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getAnggota(){
        anggotaDb = FirebaseDatabase.getInstance().getInstance().getReference();
        final Query query = grupDb.child("users");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    int i;
                    User user = userSnapshot.getValue(User.class);
                    for (i = 0; i < grup.size(); i++) {
                        if(user.getGrup().equals(grup.get(i))){
                            anggota.add(user.getId());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateAdapter(){
        final List<transaksi_anggota> listTransaksi= new ArrayList<>();
        transDB = FirebaseDatabase.getInstance().getInstance().getReference();
        final Query query = transDB.child("transaksi_anggota");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot transSnapshot : dataSnapshot.getChildren()) {
                    transaksi_anggota trans = transSnapshot.getValue(transaksi_anggota.class);
                    int i;
                    for (i = 0; i < anggota.size(); i++) {
                        if(trans.getId_user().equals(anggota.get(i))){
                            listTransaksi.add(trans);
                        }
                    }
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
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.help:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            case R.id.out:
                SaveSharedPreference.setLoggedInPK(getApplicationContext(), false);
                SaveSharedPreference.setId(getApplicationContext(), null);
                Intent intent = new Intent(ListTransaksiAll.this, login.class);
                startActivity(intent);
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
