package com.example.intel.fireapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Model.Tawaran;
import com.example.intel.fireapp.Model.TransaksiKeTR;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.OnLoadMoreListener;
import com.example.intel.fireapp.PengepulKecil.HomePK;
import com.example.intel.fireapp.PengepulKecil.ListPenawaran;
import com.example.intel.fireapp.PengepulKecil.db_ReadAkun;
import com.example.intel.fireapp.PengepulKecil.sampahMenunggu;
import com.example.intel.fireapp.PengepulKecil.transaksikeTR;
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

public class AdapterTransaksiTR extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context mContext;
    private static String jenis;
    String m_Text;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private List<TransaksiKeTR> transList = new ArrayList<>();
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public AdapterTransaksiTR(RecyclerView recyclerView, Context context, String jenis)
    {
        this.jenis=jenis;
        mContext = context;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setData(List<TransaksiKeTR> transaksi) {
        transList.clear();
        transList.addAll(transaksi);
        notifyDataSetChanged();
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_transaksirombeng, parent, false);
            return new MyViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }


    private OnLoadMoreListener onLoadMoreListener;
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return transList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            final TransaksiKeTR trans = transList.get(position);
            final MyViewHolder myholder = (MyViewHolder) holder;
            myholder.tanggal.setText(trans.getTanggal());
            myholder.total.setText("Total Sampah : " + trans.getTotal().toString() + " Kg");
            myholder.kaca.setText("Sampah Kaca : " + trans.getKaca_pk().toString() + " Kg");
            myholder.plastik.setText("Sampah Plastik : " + trans.getPlastik_pk().toString() + " Kg");
            myholder.logam.setText("Sampah Logam : " + trans.getLogam_pk().toString() + " Kg");
            myholder.kertas.setText("Sampah Kertas : " + trans.getKertas_pk().toString() + " Kg");
            myholder.lain.setText("Sampah Lainnya : " + trans.getLainnya_pk().toString() + " Kg");

            final Query query = databaseReference.child("users").orderByChild("id").equalTo(trans.getId_pk());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            User login = userSnapshot.getValue(User.class);
                            myholder.user.setText("" + login.getNama().toString() + ", " + login.getAlamat().toString() + "\n " + login.getTelp().toString() + "");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            if (jenis.equals("TRtrans")) {
                myholder.tawar.setText("Status Pengambilan : " + trans.getStatus().toString());
            } else if (jenis.equals("PKJS")) {
                myholder.tawar.setText("Lihat Penawaran -->");
                myholder.jml.setVisibility(View.VISIBLE);
                final Query query2 = databaseReference.child("penawaran").child(trans.getId_ordersampah());
                query2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            myholder.jml.setText((int) dataSnapshot.getChildrenCount() + " Penawaran");
                        } else {
                            myholder.jml.setText("0 Penawaran");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                myholder.tawar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        Intent intents = new Intent(context, ListPenawaran.class);
                        intents.putExtra("id_order", "" + trans.getId_ordersampah());
                        context.startActivity(intents);
                    }
                });
            } else if (jenis.equals("TRJS")) {
                myholder.tawar.setText("Lakukan Penawaran -->");
                myholder.tawar.setOnClickListener(new View.OnClickListener() {
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
                                Tawaran tawaran = new Tawaran(id, trans.getId_pk(), SaveSharedPreference.getId(mContext), trans.getId_ordersampah(), m_Text, "menunggu");
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
            } else if (jenis.equals("PKtunggu")) {
                myholder.jml.setVisibility(View.VISIBLE);
                myholder.jml.setText("Tandai Selesai");
                myholder.jml.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference.child("transaksiTR").child(trans.getId_ordersampah()).child("status").setValue("selesai");
                        Intent intent = new Intent(mContext, sampahMenunggu.class);
                        mContext.startActivity(intent);
                    }
                });
                myholder.tawar.setText("Batalkan Penjualan");
                myholder.tawar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference.child("transaksiTR").child(trans.getId_ordersampah()).child("status").setValue("belum");
                        databaseReference.child("transaksiTR").child(trans.getId_ordersampah()).child("id_tr").setValue("");
                        databaseReference.child("penawaran").child(trans.getId_ordersampah()).child(trans.id_tr).child("status").setValue("menunggu");
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("Batalkan Penjualan");
                        builder.setMessage("Pembatalan penjualan berhasil !");

                        // add a button
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(mContext, sampahMenunggu.class);
                                mContext.startActivity(intent);
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                        Intent intent = new Intent(mContext, sampahMenunggu.class);
                        mContext.startActivity(intent);
                    }
                });
            } else {
                myholder.tawar.setVisibility(View.INVISIBLE);
            }
        }else if(holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return transList == null ? 0 : transList.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

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

