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
import com.example.intel.fireapp.Model.TambahGrup;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.PengepulKecil.PageAnggota;
import com.example.intel.fireapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AnggotaAdapter extends RecyclerView.Adapter<AnggotaAdapter.MyViewHolder>{
    private final Context mContext;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private List<Anggota> anggotaList = new ArrayList<>();

    public AnggotaAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<Anggota> anggota) {
        anggotaList.clear();
        anggotaList.addAll(anggota);
        notifyDataSetChanged();
    }



    @Override
    public AnggotaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_anggota, parent, false);
        return new AnggotaAdapter.MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final AnggotaAdapter.MyViewHolder holder, final int position) {
        final Anggota anggota = anggotaList.get(position);

        holder.id_grup.setText(anggota.getId_grup());
        holder.user_id.setText(anggota.getId_user());
        holder.nama.setText(anggota.getNama());
        holder.alamat.setText(anggota.getAlamat());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Context context = view.getContext();
//                Intent intent = new Intent(context,PageAnggota.class);
//                intent.putExtra("idGrup", ""+grup.getId_grup());
//                intent.putExtra("id", ""+idUser);
//                context.startActivity(intent);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return anggotaList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView user_id,id_grup,nama, alamat;

        public MyViewHolder(View itemView) {
            super(itemView);
            user_id = (TextView) itemView.findViewById(R.id.idUser);
            id_grup = (TextView) itemView.findViewById(R.id.idGrup);
            nama = (TextView) itemView.findViewById(R.id.namaAnggota);
            alamat = (TextView) itemView.findViewById(R.id.alamatAnggota);
        }
    }
}
