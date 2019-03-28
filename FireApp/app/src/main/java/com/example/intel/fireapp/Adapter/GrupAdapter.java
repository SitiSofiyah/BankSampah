package com.example.intel.fireapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intel.fireapp.Model.TambahGrup;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class GrupAdapter extends RecyclerView.Adapter<GrupAdapter.MyViewHolder>{
    private final Context mContext;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private List<TambahGrup> grupList = new ArrayList<>();

    public GrupAdapter(Context context) {
        mContext = context;
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

        holder.myTextView.setText(grup.getNama_grup());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, " " + grupList.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return grupList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView myTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            myTextView = (TextView) itemView.findViewById(R.id.nama);
        }
    }
}
