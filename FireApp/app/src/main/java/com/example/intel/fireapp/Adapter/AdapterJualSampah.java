package com.example.intel.fireapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.intel.fireapp.Model.Anggota;
import com.example.intel.fireapp.Model.TransaksiKeTR;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.PengepulKecil.DetailAnggota;
import com.example.intel.fireapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdapterJualSampah extends RecyclerView.Adapter<AdapterJualSampah.MyViewHolder>{
    private final Context mContext;
    String ket;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private List<TransaksiKeTR> transList = new ArrayList<>();

    public AdapterJualSampah(Context context)
    {
        mContext = context;
    }

    public void setData(List<TransaksiKeTR> transaksi) {
        transList.clear();
        transList.addAll(transaksi);
        notifyDataSetChanged();
    }



    @Override
    public AdapterJualSampah.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_jualsampah, parent, false);
        return new AdapterJualSampah.MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final AdapterJualSampah.MyViewHolder holder, final int position) {
        final TransaksiKeTR trans = transList.get(position);

        holder.tanggal.setText(trans.getTanggal());
        holder.kaca.setText("Sampah Kaca : "+trans.getKaca_pk()+" Kg");
        holder.plastik.setText("Sampah Plastik : "+trans.getPlastik_pk()+" Kg");
        holder.logam.setText("Sampah Logam : "+trans.getLogam_pk()+" Kg");
        holder.kertas.setText("Sampah Kertas : "+trans.getKertas_pk()+" Kg");
        holder.lain.setText("Sampah Lainnya : "+trans.getLainnya_pk()+" Kg");
        holder.total.setText("Total Sampah : "+trans.getTotal()+" Kg");

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final Query query = databaseReference.child("users").orderByChild("id").equalTo(trans.getId_pk());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        User login = userSnapshot.getValue(User.class);
                        holder.user.setText(login.getNama().toString()+", "+login.getAlamat().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return transList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tanggal, kaca, plastik, logam,kertas, lain,total, user;

        public MyViewHolder(View itemView) {
            super(itemView);
            user = (TextView) itemView.findViewById(R.id.user);
            tanggal = (TextView) itemView.findViewById(R.id.tanggal);
            kaca = (TextView) itemView.findViewById(R.id.kaca);
            plastik = (TextView) itemView.findViewById(R.id.plastik);
            logam = (TextView) itemView.findViewById(R.id.logam);
            kertas = (TextView) itemView.findViewById(R.id.kertas);
            lain = (TextView) itemView.findViewById(R.id.lain);
            total = (TextView) itemView.findViewById(R.id.ket);
        }
    }
}
