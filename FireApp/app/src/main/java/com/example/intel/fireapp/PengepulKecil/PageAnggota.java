package com.example.intel.fireapp.PengepulKecil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Adapter.AnggotaAdapter;
import com.example.intel.fireapp.Adapter.GrupAdapter;
import com.example.intel.fireapp.Model.Anggota;
import com.example.intel.fireapp.Model.TambahGrup;
import com.example.intel.fireapp.Model.User;
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

public class PageAnggota extends AppCompatActivity {

    DatabaseReference ref;
    ArrayAdapter<Anggota> adapter;
    ArrayList<Anggota> list;
    ArrayAdapter<User> userAdapter;
    ArrayList<User> userArray;
    Anggota anggota;
    User user;
    public RecyclerView recyclerListView;
    public AnggotaAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anggota);



        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Grup Tabungan Sampah");
        setSupportActionBar(toolbar);

//        recyclerListView=(RecyclerView) findViewById(R.id.recylerview_list);
//        recyclerListView.setLayoutManager(new LinearLayoutManager(this));
//        myAdapter= new AnggotaAdapter(this, getIntent().getStringExtra("id"));
//        updateAdapter();
//        recyclerListView.setAdapter(myAdapter);
//
//        updateAdapter();


    }
//
//    private void updateAdapter(){
//        final List<Anggota> listAnggota= new ArrayList<>();
//        final List<User> listUser= new ArrayList<>();
//        ref = FirebaseDatabase.getInstance().getInstance().getReference();
//        final Query query = ref.child("anggota").orderByChild("id_grup").equalTo(getIntent().getStringExtra("idGrup"));
//        query.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                for(DataSnapshot ds : dataSnapshot.getChildren()){
//                    Anggota anggota = ds.getValue(Anggota.class);
//                    final Query query = ref.child("users").orderByChild("id").equalTo(anggota.getId_user().toString());
//
//                    query.addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                            listAnggota.add(dataSnapshot.getValue(Anggota.class));
//                            listUser.add(dataSnapshot.getValue(User.class));
//                            displayUsers(listAnggota, listUser);
//                        }
//
//                        @Override
//                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                        }
//
//                        @Override
//                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                        }
//
//                        @Override
//                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(getApplicationContext(),"Canceled",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//    }
//
//
//    public void displayUsers(List<Anggota> ls, List<User> lu){
//        recyclerListView.setVisibility(View.VISIBLE);
//        myAdapter.setData(ls,lu);
//        myAdapter.notifyDataSetChanged();
//    }
//

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
                SharedPreferences preferences =getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                finish();
                Intent intent = new Intent(PageAnggota.this,login.class);
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
