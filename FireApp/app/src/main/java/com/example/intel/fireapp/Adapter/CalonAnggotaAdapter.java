package com.example.intel.fireapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intel.fireapp.Model.Anggota;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.PengepulKecil.InputAnggota;
import com.example.intel.fireapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CalonAnggotaAdapter extends RecyclerView.Adapter<CalonAnggotaAdapter.MyViewHolder> {
    private final Context mContext;
    private List<User> anggotaList = new ArrayList<>();
    public ArrayList<User> checkedAnggota=new ArrayList<User>();
    StringBuilder sb=null;
    public CalonAnggotaAdapter(Context mContext) {
        this.mContext = mContext;
    }


    public ArrayList<User> getCheckedAnggota() {
        return checkedAnggota;
    }


    public void setCheckedAnggota(ArrayList<User> checkedAnggota) {
        this.checkedAnggota = checkedAnggota;
    }

    public void setData(List<User> anggota) {
        anggotaList.clear();
        anggotaList.addAll(anggota);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CalonAnggotaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.calon_anggota, parent, false);
        return new CalonAnggotaAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CalonAnggotaAdapter.MyViewHolder holder, final int position) {
        final User anggota = anggotaList.get(position);

        holder.id_anggota.setText(anggota.getId());
        holder.nama.setText(anggota.getNama());
        holder.alamat.setText(anggota.getAlamat());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.anggota.isChecked()) {
                    holder.anggota.setChecked(false);
                    checkedAnggota.remove(anggotaList.get(position));
                    Toast.makeText(mContext, "Ada " + checkedAnggota.size(), Toast.LENGTH_SHORT).show();
                    sb = new StringBuilder();
                    int i = 0;
                    for (i = 0; i < checkedAnggota.size(); i++) {

                        User anggota = checkedAnggota.get(i);
                        sb.append(anggota.getNama() + "\n");
                    }
                    Toast.makeText(mContext, "Ada " + sb.toString(), Toast.LENGTH_SHORT).show();

                }
                else if(!holder.anggota.isChecked()) {
                    holder.anggota.setChecked(true);
                    checkedAnggota.add(anggotaList.get(position));
                    Toast.makeText(mContext, "Ada " + checkedAnggota.size(), Toast.LENGTH_SHORT).show();
                    sb = new StringBuilder();
                    int i = 0;
                    for (i = 0; i < checkedAnggota.size(); i++) {

                        User anggota = checkedAnggota.get(i);
                        sb.append(anggota.getNama() + "\n");
                    }
                    Toast.makeText(mContext, "Ada " + sb.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return anggotaList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView id_anggota, nama, alamat;
        public CheckBox anggota;

        public MyViewHolder(View itemView) {
            super(itemView);
            anggota = (CheckBox) itemView.findViewById(R.id.check);
            id_anggota = (TextView) itemView.findViewById(R.id.idAnggota);
            nama = (TextView) itemView.findViewById(R.id.namaAnggota);
            alamat = (TextView) itemView.findViewById(R.id.alamatAnggota);
        }
    }
}
