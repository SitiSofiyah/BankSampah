package com.example.intel.fireapp.Model;

public class TambahGrup {
    public  String id_grup, nama_grup, id;

    public TambahGrup(){
    }
    //id merupakan id user yang mana id user adalah id pengepul kecil ketika registrasi
    public TambahGrup(String id_grup, String nama_grup, String id){
        this.id_grup = id_grup;
        this.nama_grup = nama_grup;
        this.id = id;
    }

    public String getId_grup() {
        return id_grup;
    }

    public void setId_grup(String id_grup) {
        this.id_grup = id_grup;
    }

    public String getNama_grup() {
        return nama_grup;
    }

    public void setNama_grup(String nama_grup) {
        this.nama_grup = nama_grup;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
