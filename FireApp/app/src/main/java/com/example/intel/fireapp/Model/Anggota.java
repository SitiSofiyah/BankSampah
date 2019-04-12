package com.example.intel.fireapp.Model;

public class Anggota {
    public String id ,id_user;

    public Anggota() {
    }

    public Anggota(String id, String id_grup, String id_user) {
        this.id = id;
        this.id_user = id_user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
