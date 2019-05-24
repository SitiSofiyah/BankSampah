package com.example.intel.fireapp.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Model.Tawaran;
import com.example.intel.fireapp.Model.TransaksiKeTR;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.PengepulKecil.ListPenawaran;
import com.example.intel.fireapp.PengepulKecil.db_ReadAkun;
import com.example.intel.fireapp.R;
import com.example.intel.fireapp.TukangRombeng.ListJualSampah;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdapterTransaksiTR extends RecyclerView.Adapter<AdapterTransaksiTR.MyViewHolder>{
    private final Context mContext;
    private static String jenis;
    String m_Text;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private List<TransaksiKeTR> transList = new ArrayList<>();

    public AdapterTransaksiTR(Context context, String jenis)
    {
        this.jenis=jenis;
        mContext = context;
    }

    public void setData(List<TransaksiKeTR> transaksi) {
        transList.clear();
        transList.addAll(transaksi);
        notifyDataSetChanged();
    }



    @Override
    public AdapterTransaksiTR.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_transaksirombeng, parent, false);
        return new AdapterTransaksiTR.MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final AdapterTransaksiTR.MyViewHolder holder, final int position) {
        final TransaksiKeTR trans = transList.get(position);

        holder.tanggal.setText(trans.getTanggal());
        holder.total.setText("Total Sampah : "+trans.getTotal().toString()+" Kg");
        holder.kaca.setText("Sampah Kaca : "+trans.getKaca_pk().toString()+" Kg");
        holder.plastik.setText("Sampah Plastik : "+trans.getPlastik_pk().toString()+" Kg");
        holder.logam.setText("Sampah Logam : "+trans.getLogam_pk().toString()+" Kg");
        holder.kertas.setText("Sampah Kertas : "+trans.getKertas_pk().toString()+" Kg");
        holder.lain.setText("Sampah Lainnya : "+trans.getLainnya_pk().toString()+" Kg");
        if(jenis.equals("TRtrans")){
            final Query query = databaseReference.child("users").orderByChild("id").equalTo(trans.getId_pk());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            User login = userSnapshot.getValue(User.class);
                            holder.user.setText(""+login.getNama().toString()+", "+login.getAlamat().toString()+"\n "+login.getTelp().toString()+"");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            holder.tawar.setText("Status Pengambilan : "+trans.getStatus().toString());
        }else if(jenis.equals("PKJS")){
            holder.tawar.setText("Lihat Penawaran -->");
            holder.jml.setVisibility(View.VISIBLE);
            final Query query2 = databaseReference.child("penawaran").child(trans.getId_ordersampah());
            query2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        holder.jml.setText((int)dataSnapshot.getChildrenCount()+" Penawaran");
                    }else{
                        holder.jml.setText("0 Penawaran");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            holder.tawar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intents = new Intent(context, ListPenawaran.class);
                    intents.putExtra("id_order",""+trans.getId_ordersampah());
                    context.startActivity(intents);
                }
            });
        } else if(jenis.equals("TRJS")){
            holder.tawar.setText("Lakukan Penawaran -->");
            holder.tawar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Masukkan penawaran");

                    final EditText input = new EditText(mContext);
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
                    builder.setView(input);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            m_Text = input.getText().toString();
                            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("penawaran");
                            String id = databaseReference.push().getKey();
                            Tawaran tawaran = new Tawaran(id, trans.getId_pk(), SaveSharedPreference.getId(mContext),trans.getId_ordersampah(),m_Text,"menunggu");
                            databaseReference.child(trans.getId_ordersampah()).child(SaveSharedPreference.getId(mContext)).setValue(tawaran);
                        }
                    });
                    builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            });
        } else if(jenis.equals("PKtunggu")){
            holder.jml.setVisibility(View.VISIBLE);
            holder.jml.setText("Tandai Selesai");
            holder.jml.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Query getSelesai = databaseReference.child("transaksiTR").child(trans.getId_ordersampah());
                    getSelesai.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                databaseReference.child("transaksiTR").child(trans.getId_ordersampah()).child("status").setValue("selesai");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
            holder.tawar.setText("Batalkan Penjualan");
            holder.tawar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Query getBatal = databaseReference.child("transaksiTR").child(trans.getId_ordersampah());
                    getBatal.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                databaseReference.child("transaksiTR").child(trans.getId_ordersampah()).child("status").setValue("belum");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    final Query getKembali = databaseReference.child("penawaran").child(trans.getId_ordersampah());
                    getKembali.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                for(DataSnapshot data : dataSnapshot.getChildren()){
                                    Tawaran tawar = data.getValue(Tawaran.class);
                                    tawar.setStatus("menunggu");
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
        }else if(jenis.equals("PKselesai")){
            holder.tawar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return transList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tanggal, kaca, plastik, logam,kertas, lain,total, user,tawar, jml;
//        public Button detail, tambah;

        public MyViewHolder(View itemView) {
            super(itemView);
            user = (TextView) itemView.findViewById(R.id.user);
            if(jenis.equals("PKJS")){
                user.setVisibility(View.GONE);
            }
            tanggal = (TextView) itemView.findViewById(R.id.tanggal);
            kaca = (TextView) itemView.findViewById(R.id.kaca);
            plastik = (TextView) itemView.findViewById(R.id.plastik);
            logam = (TextView) itemView.findViewById(R.id.logam);
            kertas = (TextView) itemView.findViewById(R.id.kertas);
            lain = (TextView) itemView.findViewById(R.id.lain);
            total = (TextView) itemView.findViewById(R.id.ket);
            tawar=(TextView) itemView.findViewById(R.id.status);
            jml=(TextView) itemView.findViewById(R.id.jml);
//            detail = (Button) itemView.findViewById(R.id.detail);
//            tambah = (Button) itemView.findViewById(R.id.tambah);
        }
    }

    public void setFilter(ArrayList<TransaksiKeTR> filter){
        transList = new ArrayList<>();
        transList.addAll(filter);
        notifyDataSetChanged();
    }
}

