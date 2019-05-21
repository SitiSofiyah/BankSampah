package com.example.intel.fireapp.Model;

public class Tawaran {
    public String id, id_pk,id_tr,penawaran,status, id_js;

    public Tawaran() {
    }

    public Tawaran(String id, String id_pk, String id_tr, String id_js, String penawaran, String status) {
        this.id = id;
        this.id_pk = id_pk;
        this.id_tr = id_tr;
        this.id_js=id_js;
        this.penawaran = penawaran;
        this.status = status;
    }

    public String getId_js() {
        return id_js;
    }

    public void setId_js(String id_js) {
        this.id_js = id_js;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_pk() {
        return id_pk;
    }

    public void setId_pk(String id_pk) {
        this.id_pk = id_pk;
    }

    public String getId_tr() {
        return id_tr;
    }

    public void setId_tr(String id_tr) {
        this.id_tr = id_tr;
    }

    public String getPenawaran() {
        return penawaran;
    }

    public void setPenawaran(String penawaran) {
        this.penawaran = penawaran;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
