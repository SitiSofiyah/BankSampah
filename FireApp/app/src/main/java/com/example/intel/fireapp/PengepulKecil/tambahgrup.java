package com.example.intel.fireapp.PengepulKecil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Adapter.GrupAdapter;
import com.example.intel.fireapp.Model.Anggota;
import com.example.intel.fireapp.Model.TambahGrup;
import com.example.intel.fireapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class tambahgrup extends AppCompatActivity {
    private Button btTambahGrup;
    private EditText etTambahgrup;
    SharedPreferences sharedPreferences;
    private DatabaseReference mDatabase,grupDB;
    ListView listView;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    TambahGrup tagrup;
    public RecyclerView recyclerListView;
    public GrupAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahgrup);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Grup Tabungan Sampah");
        setSupportActionBar(toolbar);

        //inisialisasi edit teks dan button

        btTambahGrup = (Button) findViewById(R.id.tambahgrupbutton);
        etTambahgrup = (EditText) findViewById(R.id.tambahgrup);

        recyclerListView=(RecyclerView) findViewById(R.id.recylerview_list);
        recyclerListView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter= new GrupAdapter(this, getIntent().getStringExtra("id"));
        updateAdapter();
        recyclerListView.setAdapter(myAdapter);

        btTambahGrup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  if (!isEmpty(etTambahgrup.getText().toString())){
                      TambahGrup();
                      updateAdapter();
                  }
                   else{
                      Snackbar.make(findViewById(R.id.tambahgrupbutton), "Data barang tidak boleh kosong",Snackbar.LENGTH_LONG).show();
                  }


            }

        });
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
                Intent intent = new Intent(tambahgrup.this, login.class);
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
        final List<TambahGrup> listGrup= new ArrayList<>();
        grupDB = FirebaseDatabase.getInstance().getInstance().getReference();
        final Query query = grupDB.child("grup").orderByChild("id").equalTo(SaveSharedPreference.getId(getApplicationContext()));
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listGrup.add(dataSnapshot.getValue(TambahGrup.class));
                displayUsers(listGrup);
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

    public void displayUsers(List<TambahGrup> ls){
        recyclerListView.setVisibility(View.VISIBLE);
        etTambahgrup.setText(null);
        myAdapter.setData(ls);
        myAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private void TambahGrup() {

        mDatabase = FirebaseDatabase.getInstance().getReference("grup");

        final String nama_grup = etTambahgrup.getText().toString();

        String id_user = getIntent().getStringExtra("id");

        String id_grup = mDatabase.push().getKey();


        mDatabase = FirebaseDatabase.getInstance().getReference("grup");



        TambahGrup tambahGrup = new TambahGrup(id_grup, nama_grup, id_user);

        mDatabase.child(id_grup).setValue(tambahGrup);
    }
}
