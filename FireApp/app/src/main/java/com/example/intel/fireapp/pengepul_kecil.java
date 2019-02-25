package com.example.intel.fireapp;

public class pengepul_kecil {
    String id,nama, alamat, telp;

    public pengepul_kecil() {
    }

    public pengepul_kecil(String id,String nama, String alamat, String telp) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.telp = telp;
    }

    public String getNama() {
        return nama;
    }

    public String getId() {
        return id;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getTelp() {
        return telp;
    }
}
