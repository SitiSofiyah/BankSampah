package com.example.intel.fireapp.Model;

public class Pemberitahuan {
    String id,id_admin, id_user,tanggal, pesan, status;

    public Pemberitahuan() {
    }

    public Pemberitahuan(String id, String id_admin, String id_user, String tanggal, String pesan, String status) {
        this.id = id;
        this.id_admin = id_admin;
        this.id_user = id_user;
        this.tanggal = tanggal;
        this.pesan = pesan;
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_admin() {
        return id_admin;
    }

    public void setId_admin(String id_admin) {
        this.id_admin = id_admin;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
}
