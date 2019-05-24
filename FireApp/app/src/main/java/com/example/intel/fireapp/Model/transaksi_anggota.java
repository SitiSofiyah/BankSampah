package com.example.intel.fireapp.Model;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Anggota.Home_Anggota;
import com.example.intel.fireapp.PengepulKecil.HomePK;
import com.example.intel.fireapp.TukangRombeng.Home_tr;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class transaksi_anggota {
    public String id_trans,id_user, keterangan, id_pk;
    public int masuk, keluar;
    public String tanggal;

    public String getId_trans() {
        return id_trans;
    }

    public void setId_trans(String id_trans) {
        this.id_trans = id_trans;
    }

    public int getMasuk() {
        return masuk;
    }

    public void setMasuk(int masuk) {
        this.masuk = masuk;
    }

    public int getKeluar() {
        return keluar;
    }

    public void setKeluar(int keluar) {
        this.keluar = keluar;
    }

    public transaksi_anggota() {

    }

    public String getId_pk() {
        return id_pk;
    }

    public void setId_pk(String id_pk) {
        this.id_pk = id_pk;
    }

    public transaksi_anggota(String id_trans, String id_user, String id_pk, int keluar, String keterangan, int masuk, String tanggal) {
        this.id_trans = id_trans;
        this.id_user = id_user;
        this.keterangan = keterangan;
        this.tanggal = tanggal;
        this.keluar=keluar;
        this.masuk=masuk;
        this.id_pk=id_pk;
    }



    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
