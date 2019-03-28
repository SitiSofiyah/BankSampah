package com.example.intel.fireapp.PengepulKecil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Adapter.GrupAdapter;
import com.example.intel.fireapp.Anggota.Home_Anggota;
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
import java.util.EmptyStackException;
import java.util.List;

import static android.text.TextUtils.isEmpty;

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

        //inisialisasi edit teks dan button

        btTambahGrup = (Button) findViewById(R.id.tambahgrupbutton);
        etTambahgrup = (EditText) findViewById(R.id.tambahgrup);

        recyclerListView=(RecyclerView) findViewById(R.id.recylerview_list);
        recyclerListView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter= new GrupAdapter(this);
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

//    private void update(){
//        tagrup = new TambahGrup();
//        listView = (ListView) findViewById(R.id.listGrup);
//
//        adapter = new ArrayAdapter<String>(this,R.layout.list_grup,R.id.nama,list);
//
//        grupDB = FirebaseDatabase.getInstance().getInstance().getReference();
//        final Query query = grupDB.child("grup").orderByChild("id").equalTo(getIntent().getStringExtra("id"));
//
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds : dataSnapshot.getChildren()){
//                    tagrup =  ds.getValue(TambahGrup.class);
//                    list.add(tagrup.getNama_grup().toString());
//                    listView.setAdapter(adapter);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
    private void updateAdapter(){
        final List<TambahGrup> listGrup= new ArrayList<>();
        grupDB = FirebaseDatabase.getInstance().getInstance().getReference();
        final Query query = grupDB.child("grup").orderByChild("id").equalTo(getIntent().getStringExtra("id"));
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



        TambahGrup tambahGrup = new TambahGrup(id_grup, nama_grup, id_user);

        mDatabase.child(id_grup).setValue(tambahGrup);
    }
}
