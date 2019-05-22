package com.example.intel.fireapp.PengepulKecil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Adapter.AnggotaAdapter;
import com.example.intel.fireapp.Adapter.CalonAnggotaAdapter;
import com.example.intel.fireapp.Model.Anggota;
import com.example.intel.fireapp.Model.TambahGrup;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.R;
import com.example.intel.fireapp.TukangRombeng.Home_tr;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InputAnggota extends AppCompatActivity {

    DatabaseReference ref,user;
    public RecyclerView recyclerListView;
    public CalonAnggotaAdapter myAdapter;
    public Button add;
    final List<User> listAnggota= new ArrayList<>();
    StringBuilder sb=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_anggota);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Pilih Anggota");
        setSupportActionBar(toolbar);

        recyclerListView = (RecyclerView) findViewById(R.id.listt);
        recyclerListView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new CalonAnggotaAdapter(this);
        updateAdapter();
        recyclerListView.setAdapter(myAdapter);

        add = (Button) findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb = new StringBuilder();
                int i = 0;
                if(myAdapter.checkedAnggota.size()>i){
                    for (i = 0; i < myAdapter.checkedAnggota.size(); i++) {
                        ref = FirebaseDatabase.getInstance().getReference();
                        User anggota = myAdapter.checkedAnggota.get(i);

                        ref = FirebaseDatabase.getInstance().getReference("anggota").child(getIntent().getStringExtra("idGrup"));
                        user = FirebaseDatabase.getInstance().getReference("users").child(anggota.getId());

                        Anggota tambahAnggota = new Anggota(getIntent().getStringExtra("idGrup"), anggota.getId(), anggota.getNama(), anggota.getAlamat(), 0, SaveSharedPreference.getId(getApplicationContext()));

                        ref.child(anggota.getId()).setValue(tambahAnggota);
                        user.child("grup").setValue(getIntent().getStringExtra("idGrup"));
                        Intent intents = new Intent(InputAnggota.this, PageAnggota.class);
                        intents.putExtra("idGrup", getIntent().getStringExtra("idGrup").toString());
                        intents.putExtra("namaGrup", getIntent().getStringExtra("namaGrup").toString());
                        startActivity(intents);
                        finish();
                    }
                }else {
                    Toast.makeText(InputAnggota.this, "Tidak ada Anggota yang ditambahkan", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(InputAnggota.this, PageAnggota.class);
                    intent.putExtra("idGrup", getIntent().getStringExtra("idGrup").toString());
                    intent.putExtra("namaGrup", getIntent().getStringExtra("namaGrup").toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    private void updateAdapter(){
        ref = FirebaseDatabase.getInstance().getInstance().getReference();
        final Query query = ref.child("users").orderByChild("grup").equalTo("no");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists())
                {
                    User anggota = dataSnapshot.getValue(User.class);
                    if(anggota.getLevel().equals("Anggota")){
                        listAnggota.add(anggota);
                        displayUsers(listAnggota);
                    }
                }else {
                    Toast.makeText(InputAnggota.this, "Anggota tidak ada", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(InputAnggota.this, PageAnggota.class);
                    intent.putExtra("idGrup", getIntent().getStringExtra("idGrup").toString());
                    intent.putExtra("namaGrup", getIntent().getStringExtra("namaGrup").toString());
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void displayUsers(List<User> ls){
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
                ArrayList<User> dataFilter= new ArrayList<>();
                for( User data : listAnggota){
                    String nama = data.getNama().toLowerCase();
                    String alamat = data.getAlamat().toLowerCase();
                    if(nama.contains(s.toLowerCase())||alamat.contains(s.toLowerCase())){
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
}
