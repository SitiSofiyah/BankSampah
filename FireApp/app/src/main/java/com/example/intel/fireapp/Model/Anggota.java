package com.example.intel.fireapp.Model;

public class Anggota {

    public String id_grup ,user_id, nama,alamat,id_admin;
    public int saldo;

    public Anggota() {
    }

    public Anggota(String id_grup, String id_user, String nama, String alamat, int saldo,String id_admin) {
        this.id_grup = id_grup;
        this.user_id = id_user;
        this.nama = nama;
        this.alamat = alamat;
        this.saldo = saldo;
        this.id_admin=id_admin;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public String getId_grup() {
        return id_grup;
    }

    public void setId_grup(String id_grup) {
        this.id_grup = id_grup;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getId_user() {
        return user_id;
    }

    public void setId_user(String id_user) {
        this.user_id = id_user;
    }
}
