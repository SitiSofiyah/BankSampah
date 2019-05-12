package com.example.intel.fireapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Model.Anggota;
import com.example.intel.fireapp.PengepulKecil.PageAnggota;
import com.example.intel.fireapp.Model.TambahGrup;
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

public class GrupAdapter extends RecyclerView.Adapter<GrupAdapter.MyViewHolder>{
    private final Context mContext;
    private final String idUser;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference ref;
    private List<TambahGrup> grupList = new ArrayList<>();
    private String hasil;

    public GrupAdapter(Context context, String id) {
        mContext = context;
        idUser = id;
    }

    public void setData(List<TambahGrup> users) {
        grupList.clear();
        grupList.addAll(users);
        notifyDataSetChanged();
    }

    @Override
    public GrupAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_grup, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final GrupAdapter.MyViewHolder holder, final int position) {
        final TambahGrup grup = grupList.get(position);

        holder.nama.setText(grup.getNama_grup());
        ref = FirebaseDatabase.getInstance().getInstance().getReference().child("anggota");
        ref.child(grup.getId_grup()).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    holder.id.setText(dataSnapshot.getChildrenCount()+" Anggota");
                }else{
                    holder.id.setText("0 Anggota");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context,PageAnggota.class);
                intent.putExtra("idGrup", ""+grup.getId_grup());
                intent.putExtra("namaGrup", ""+grup.getNama_grup());
                context.startActivity(intent);
            }
        });
    }

//    public String getCount(String id){
//        final List<Anggota> listAnggota= new ArrayList<>();
//        ref = FirebaseDatabase.getInstance().getInstance().getReference().child("anggota");
//        ref.child(id).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                if(dataSnapshot.exists()){
//                    hasil = dataSnapshot.getChildrenCount()+" Anggota";
//                }else{
//                    hasil = "0 Anggota";
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        return hasil;
//    }

    @Override
    public int getItemCount() {
        return grupList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView nama, id;

        public MyViewHolder(View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.nama);
            id = (TextView) itemView.findViewById(R.id.idAnggota);
        }
    }
}
