package com.example.intel.fireapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intel.fireapp.PengepulKecil.DetailTransaksi;
import com.example.intel.fireapp.Model.transaksi_anggota;
import com.example.intel.fireapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TransaksiAnggotaAdapter extends RecyclerView.Adapter<TransaksiAnggotaAdapter.MyViewHolder>{
    private final Context mContext;
    String ket,b,jenis;
    int a;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private List<transaksi_anggota> transaksiAnggota = new ArrayList<>();

    public TransaksiAnggotaAdapter(Context context, int a, String b, String jenis)
    {
        mContext = context;
        this.a = a;
        this.b=b;
        this.jenis=jenis;

    }

    public void setData(List<transaksi_anggota> transaksi) {
        transaksiAnggota.clear();
        transaksiAnggota.addAll(transaksi);
        notifyDataSetChanged();
    }



    @Override
    public TransaksiAnggotaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_transaksianggota, parent, false);
        return new TransaksiAnggotaAdapter.MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final TransaksiAnggotaAdapter.MyViewHolder holder, final int position) {
        final transaksi_anggota transaksi = transaksiAnggota.get(position);
        NumberFormat nf = NumberFormat.getInstance();

        holder.tanggal.setText(transaksi.getTanggal());
        if(transaksi.getMasuk()>0){
            if(jenis.equals("pk")){
                holder.ket.setText("Dana Masuk Sebesar Rp. "+nf.format(transaksi.getMasuk())+",-");
            }else{
                holder.ket.setText(transaksi.getKeterangan()+"\nDana Masuk Sebesar Rp. "+nf.format(transaksi.getMasuk())+",-");
            }

        }else{
            if(jenis.equals("pk")){
                holder.ket.setText("Dana Keluar Sebesar "+nf.format(transaksi.getKeluar())+",-");
            }else{
                holder.ket.setText(transaksi.getKeterangan()+"\nDana Keluar Sebesar Rp. "+nf.format(transaksi.getKeluar())+",-");
            }

        }
        holder.rinci.setText(transaksi.getKeterangan());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = view.getContext();
                if(jenis.equals("pk")) {
                    Intent intent = new Intent(context, DetailTransaksi.class);
                    intent.putExtra("trans", transaksi.getId_trans());
                    intent.putExtra("saldo", a);
                    intent.putExtra("idGrup", b);
                    intent.putExtra("id", transaksi.getId_user());
                    context.startActivity(intent);
                }



            }
        });



    }

    @Override
    public int getItemCount() {
        return transaksiAnggota.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tanggal, ket, rinci;
//        public Button detail, tambah;

        public MyViewHolder(View itemView) {
            super(itemView);
            tanggal = (TextView) itemView.findViewById(R.id.tanggal);
            ket = (TextView) itemView.findViewById(R.id.ket);
            rinci = (TextView) itemView.findViewById(R.id.rincian);

        }
    }
}
