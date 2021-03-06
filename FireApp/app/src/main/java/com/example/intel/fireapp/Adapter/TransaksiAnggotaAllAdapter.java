package com.example.intel.fireapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.Constraints;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intel.fireapp.Model.Anggota;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.Model.transaksi_anggota;
import com.example.intel.fireapp.PengepulKecil.DetailTransaksi;
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

public class TransaksiAnggotaAllAdapter extends RecyclerView.Adapter<TransaksiAnggotaAllAdapter.MyViewHolder>{
    private final Context mContext;
    String ket,b, name;
    boolean nama;
    int a;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private List<transaksi_anggota> transaksiAnggota = new ArrayList<>();

    public TransaksiAnggotaAllAdapter(Context context)
    {
        mContext = context;
    }

    public void setData(List<transaksi_anggota> transaksi) {
        transaksiAnggota.clear();
        transaksiAnggota.addAll(transaksi);
        notifyDataSetChanged();
    }

    @Override
    public TransaksiAnggotaAllAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_transaksiallanggota, parent, false);
        return new TransaksiAnggotaAllAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TransaksiAnggotaAllAdapter.MyViewHolder holder, final int position) {
        final transaksi_anggota transaksi = transaksiAnggota.get(position);
        Locale locale = new Locale("in","ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        final Query query = databaseReference.child("users").orderByChild("id").equalTo(transaksi.getId_user());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        User login = userSnapshot.getValue(User.class);
                        holder.nama.setText(login.getNama().toString());
                        b = login.getGrup().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        NumberFormat nf = NumberFormat.getInstance();

        holder.rinci.setText(transaksi.getKeterangan());

        holder.tanggal.setText(transaksi.getTanggal());
        if(transaksi.getMasuk() > 0){
            holder.ket.setText("Sampah Masuk senilai Rp. "+nf.format(transaksi.getMasuk())+",-");
        }else if(transaksi.getKeluar() > 0){
            holder.ket.setText("Sampah Keluar Sebesar Rp. "+nf.format(transaksi.getKeluar())+",-");
        }
    }

    @Override
    public int getItemCount() {
        return transaksiAnggota.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tanggal, ket, nama, rinci;
//        public Button detail, tambah;

        public MyViewHolder(View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.nama);
            tanggal = (TextView) itemView.findViewById(R.id.tanggal);
            ket = (TextView) itemView.findViewById(R.id.ket);
            rinci = (TextView) itemView.findViewById(R.id.rincian);

        }
    }

    public void setFilter(ArrayList<transaksi_anggota> filter){
        transaksiAnggota = new ArrayList<>();
        transaksiAnggota.addAll(filter);
        notifyDataSetChanged();
    }
}

