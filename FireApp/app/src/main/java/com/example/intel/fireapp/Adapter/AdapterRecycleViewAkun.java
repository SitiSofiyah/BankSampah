package com.example.intel.fireapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.intel.fireapp.Model.Anggota;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.R;

import java.util.ArrayList;

public class AdapterRecycleViewAkun extends RecyclerView.Adapter<AdapterRecycleViewAkun.ViewHolder>{
    private ArrayList<User> dataUser;
    private Context context;

    public AdapterRecycleViewAkun(ArrayList<User> users, Context ctx){
        /**
         * Inisiasi data dan variabel yang akan digunakan
         */
        dataUser = users;
        context = ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Inisiasi View
         * Di tutorial ini kita hanya menggunakan data String untuk tiap item
         * dan juga view nya hanyalah satu TextView
         */
        TextView tvNama, tvAlamat, tvJK, tvTelp;


        ViewHolder(View v) {
            super(v);
            tvNama = (TextView) v.findViewById(R.id.tv_nama);
            tvJK = (TextView) v.findViewById(R.id.tv_JK);
            tvAlamat = (TextView) v.findViewById(R.id.tv_Alamat);
            tvTelp = (TextView) v.findViewById(R.id.tv_Telepon);
    }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         *  Inisiasi ViewHolder
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lihat_akun,parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        /**
         *  Menampilkan data pada view
         */

        final User user = dataUser.get(position);
        holder.tvNama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Read detail data
                 */
            }
        });
        holder.tvNama.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Delete dan update data
                 */
                return true;
            }
        });
        holder.tvNama.setText(user.getNama());
        holder.tvJK.setText(user.getJeniskel());
        holder.tvAlamat.setText(user.getAlamat());
        holder.tvTelp.setText(user.getTelp());
    }

    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada barang
         */
        return dataUser.size();
    }
}

