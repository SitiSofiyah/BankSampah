package com.example.intel.fireapp.PengepulKecil;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
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

public class PilihAnggota extends AppCompatActivity {

    DatabaseReference ref;
    public RecyclerView recyclerListView;
    public AnggotaAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_anggota);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Pilih Anggota");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerListView=(RecyclerView) findViewById(R.id.anggota_list);
        recyclerListView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter= new AnggotaAdapter(this,"trans");
        grup();
        recyclerListView.setAdapter(myAdapter);

    }

    private void grup(){
        final List<TambahGrup> listGrup= new ArrayList<>();
        final List<Anggota> listUser= new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getInstance().getReference();
        final Query query = ref.child("grup").orderByChild("id").equalTo(SaveSharedPreference.getId(getApplicationContext()));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> getGrup = dataSnapshot.getChildren();
                for (DataSnapshot data : getGrup){
                    TambahGrup grup = data.getValue(TambahGrup.class);
                    listGrup.add(grup);
                }

                for (int i = 0; i < listGrup.size(); i++) {
                    final TambahGrup memberGroup = listGrup.get(i);
                    ref.child("anggota").child(memberGroup.getId_grup()).orderByChild("admin").equalTo(SaveSharedPreference.getId(getApplicationContext())).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> getGrup = dataSnapshot.getChildren();
                            for (DataSnapshot data : getGrup){
                                Anggota user = data.getValue(Anggota.class);
                                listUser.add(user);
                            }
                            displayUsers(listUser);
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

    public void displayUsers(List<Anggota> ls){
        myAdapter.setData(ls);
        myAdapter.notifyDataSetChanged();
    }

}
