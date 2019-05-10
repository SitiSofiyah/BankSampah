package com.example.intel.fireapp.Model;

import java.util.Date;

public class transaksi_anggota {
    public String id_user, keterangan;
    public int masuk, keluar;
    public String tanggal;


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

    public transaksi_anggota(String id_user, String keterangan, int masuk, int keluar, String tanggal) {
        this.id_user = id_user;
        this.keterangan = keterangan;

        this.tanggal = tanggal;
        this.keluar=keluar;
        this.masuk=masuk;
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
