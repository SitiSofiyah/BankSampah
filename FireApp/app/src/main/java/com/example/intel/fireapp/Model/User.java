package com.example.intel.fireapp.Model;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
   public String id, nama, alamat, telp, jeniskel, level, password;



    public User() {
    }




    public User(String id, String nama, String alamat, String telp, String jeniskel, String level, String password) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.telp = telp;
        this.jeniskel = jeniskel;
        this.level = level;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getTelp() {
        return telp;
    }

    public String getJeniskel() {
        return jeniskel;
    }

    public String getLevel() {
        return level;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public void setJeniskel(String jeniskel) {
        this.jeniskel = jeniskel;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
