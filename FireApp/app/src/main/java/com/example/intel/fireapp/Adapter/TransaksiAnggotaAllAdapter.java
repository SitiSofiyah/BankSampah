package com.example.intel.fireapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intel.fireapp.Model.transaksi_anggota;
import com.example.intel.fireapp.PengepulKecil.DetailTransaksi;
import com.example.intel.fireapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TransaksiAnggotaAllAdapter extends RecyclerView.Adapter<TransaksiAnggotaAllAdapter.MyViewHolder>{
    private final Context mContext;
    String ket,b;
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_transaksianggota, parent, false);
        return new TransaksiAnggotaAllAdapter.MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final TransaksiAnggotaAllAdapter.MyViewHolder holder, final int position) {
        final transaksi_anggota transaksi = transaksiAnggota.get(position);

        holder.tanggal.setText(transaksi.getTanggal());
        if(transaksi.getMasuk()>0){
            holder.ket.setText("Dana Masuk Sebesar Rp. "+transaksi.getMasuk()+",-");
        }else{
            holder.ket.setText("Dana Keluar Sebesar Rp. "+transaksi.getKeluar()+",-");
        }

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext,"Saldo "+a,Toast.LENGTH_SHORT).show();
//
//                Context context = view.getContext();
//                Intent intent = new Intent(context,DetailTransaksi.class);
//                intent.putExtra("trans",transaksi.getId_trans());
//                intent.putExtra("saldo",a);
//                intent.putExtra("idGrup",b);
//                intent.putExtra("id", transaksi.getId_user());
//                context.startActivity(intent);
//
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return transaksiAnggota.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tanggal, ket;
//        public Button detail, tambah;

        public MyViewHolder(View itemView) {
            super(itemView);
            tanggal = (TextView) itemView.findViewById(R.id.tanggal);
            ket = (TextView) itemView.findViewById(R.id.ket);

        }
    }
}
