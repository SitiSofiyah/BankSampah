package com.example.intel.fireapp.PengepulKecil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.intel.fireapp.Account.login;
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

import java.util.ArrayList;
import java.util.EmptyStackException;

import static android.text.TextUtils.isEmpty;

public class tambahgrup extends AppCompatActivity {
    private Button btTambahGrup;
    private EditText etTambahgrup;
    SharedPreferences sharedPreferences;
    private DatabaseReference mDatabase,grupDB;
    ListView listView;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahgrup);

        //inisialisasi edit teks dan button
        listView = (ListView) findViewById(R.id.listGrup);
        btTambahGrup = (Button) findViewById(R.id.tambahgrupbutton);
        etTambahgrup = (EditText) findViewById(R.id.tambahgrup);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,list);
        listView.setAdapter(adapter);

        grupDB = FirebaseDatabase.getInstance().getInstance().getReference("grup");

        grupDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for(DataSnapshot grupSnapshot : dataSnapshot.getChildren()){
                    TambahGrup grup = grupSnapshot.getValue(TambahGrup.class);

                    if(grup.getId().equals(getIntent().getStringExtra("id"))){
                        list.add(grup.getNama_grup());
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                list.remove(dataSnapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        btTambahGrup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    if (!isEmpty(etTambahgrup.getText().toString()))
                    TambahGrup();
//                else
//                    Snackbar.make(findViewById(R.id.tambahgrupbutton), "Data barang tidak boleh kosong",
//                            Snackbar.LENGTH_LONG).show();
//
//                InputMethodManager imm = (InputMethodManager)
//                        getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(
//                        etTambahgrup.getWindowToken(), 0);
            }

        });
    }
    private boolean isEmpty(String s){
        //Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }
    private void TambahGrup() {

        mDatabase = FirebaseDatabase.getInstance().getReference("grup");

        final String nama_grup = etTambahgrup.getText().toString();

        String id_user = getIntent().getStringExtra("id");
        String id_grup = mDatabase.push().getKey();

<<<<<<< HEAD
        mDatabase = FirebaseDatabase.getInstance().getReference("Grup");
=======
>>>>>>> 28401986c2e4cb50d606f1ff60999cb0338180a3

        TambahGrup tambahGrup = new TambahGrup(id_grup, nama_grup, id_user);

        mDatabase.child(id_grup).setValue(tambahGrup);
    }
}
