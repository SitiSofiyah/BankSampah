package com.example.intel.fireapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intel.fireapp.Model.Tawaran;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.Model.transaksi_anggota;
import com.example.intel.fireapp.PengepulKecil.HomePK;
import com.example.intel.fireapp.PengepulKecil.ListTransaksiJualSampah;
import com.example.intel.fireapp.PengepulKecil.MenuPenjualan;
import com.example.intel.fireapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PenawaranAdapter extends RecyclerView.Adapter<PenawaranAdapter.MyViewHolder>{
    private final Context mContext;
    String ket,b;
    int a;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private List<Tawaran> listTawaran = new ArrayList<>();

    public PenawaranAdapter(Context context)
    {
        mContext = context;
    }

    public void setData(List<Tawaran> tawarans) {
        listTawaran.clear();
        listTawaran.addAll(tawarans);
        notifyDataSetChanged();
    }



    @Override
    public PenawaranAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tawaran, parent, false);
        return new PenawaranAdapter.MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final PenawaranAdapter.MyViewHolder holder, final int position) {
        final Tawaran tawaran = listTawaran.get(position);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final Query query = databaseReference.child("users").orderByChild("id").equalTo(tawaran.getId_tr());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        User login = userSnapshot.getValue(User.class);
                        holder.nama.setText(login.getNama().toString()+", "+login.getAlamat().toString()+", "+login.getTelp().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        NumberFormat nf = NumberFormat.getInstance();
        holder.harga.setText("Rp. "+nf.format(Integer.parseInt(tawaran.getPenawaran())));

        holder.terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("transaksiTR").child(tawaran.getId_js());
                dbr.child("status").setValue("terima");
                dbr.child("id_tr").setValue(tawaran.getId_tr());

                final DatabaseReference dbt = FirebaseDatabase.getInstance().getReference().child("penawaran");
                final Query query = dbt.child(tawaran.getId_js()).child(tawaran.getId_tr());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dbt.child(tawaran.getId_js()).child(tawaran.getId_tr()).child("status").setValue("terima");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Toast.makeText(mContext,"Berhasil diterima",Toast.LENGTH_LONG).show();
                Intent i = new Intent(mContext, ListTransaksiJualSampah.class);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTawaran.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView nama, harga;
        public Button terima;

        public MyViewHolder(View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.user);
            harga = (TextView) itemView.findViewById(R.id.harga);
            terima = (Button) itemView.findViewById(R.id.terima);
        }
    }
}
