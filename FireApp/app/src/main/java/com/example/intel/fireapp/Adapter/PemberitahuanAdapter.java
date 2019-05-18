package com.example.intel.fireapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Model.Pemberitahuan;
import com.example.intel.fireapp.Model.transaksi_anggota;
import com.example.intel.fireapp.PengepulKecil.DetailTransaksi;
import com.example.intel.fireapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PemberitahuanAdapter  extends RecyclerView.Adapter<PemberitahuanAdapter.MyViewHolder>{
    private final Context mContext;
    String ket,b;
    int a;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private List<Pemberitahuan> pemberitahuans  = new ArrayList<>();

    public PemberitahuanAdapter(Context context)
    {
        mContext = context;
    }

    public void setData(List<Pemberitahuan> info) {
        pemberitahuans.clear();
        pemberitahuans.addAll(info);
        notifyDataSetChanged();
    }



    @Override
    public PemberitahuanAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pemberitahuan, parent, false);
        return new PemberitahuanAdapter.MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final PemberitahuanAdapter.MyViewHolder holder, final int position) {
        final DatabaseReference transDB = FirebaseDatabase.getInstance().getReference();
        final Pemberitahuan info = pemberitahuans.get(position);
        holder.tanggal.setText(info.getTanggal());
        holder.pesan.setText(info.getPesan());

//        if(info.getStatus().equals("send")){
//            transDB.child("pemberitahuan").child(SaveSharedPreference.getId(mContext)).child(info.getId()).child("status").setValue("read");
//        }
    }

    @Override
    public int getItemCount() {
        return pemberitahuans.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tanggal, pesan;
        public RelativeLayout isi;
//        public Button detail, tambah;

        public MyViewHolder(View itemView) {
            super(itemView);
            tanggal = (TextView) itemView.findViewById(R.id.tanggal);
            pesan = (TextView) itemView.findViewById(R.id.pesan);
            isi = (RelativeLayout) itemView.findViewById(R.id.isi);
        }
    }
}
