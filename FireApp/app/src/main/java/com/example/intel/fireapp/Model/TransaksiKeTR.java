package com.example.intel.fireapp.Model;

public class TransaksiKeTR {
    public String id_ordersampah, plastik_pk, kertas_pk, logam_pk, kaca_pk, lainnya_pk;
    public String tanggal,id_pk,total,status,id_tr;


    public TransaksiKeTR() {

    }

    public String getId_tr() {
        return id_tr;
    }

    public void setId_tr(String id_tr) {
        this.id_tr = id_tr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TransaksiKeTR (String id_ordersampah, String id_pk, String id_tr, String plastik_pk, String kertas_pk, String logam_pk, String kaca_pk, String lainnya_pk,
                          String total, String tanggal, String status) {
        this.id_pk = id_pk;
        this.id_ordersampah = id_ordersampah;
        this.plastik_pk = plastik_pk;
        this.kertas_pk = kertas_pk;
        this.logam_pk = logam_pk;
        this.kaca_pk = kaca_pk;
        this.lainnya_pk = lainnya_pk;
        this.total = total;
        this.tanggal = tanggal;
        this.status=status;
        this.id_tr=id_tr;

    }

    public String getId_ordersampah() {
        return id_ordersampah;
    }

    public void setId_ordersampah(String id_ordersampah) {
        this.id_ordersampah = id_ordersampah;
    }

    public String getId_pk() {
        return id_pk;
    }

    public void setId_pk(String id_pk){
        this.id_pk = id_pk;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getPlastik_pk() {
        return plastik_pk;
    }

    public void setPlastik_pk(String plastik_pk) {
        this.plastik_pk = plastik_pk;
    }

    public String getKertas_pk() {
        return kertas_pk;
    }

    public void setKertas_pk(String kertas_pk) {
        this.kertas_pk = kertas_pk;
    }

    public String getLogam_pk() {
        return logam_pk;
    }

    public void setLogam_pk(String logam_pk) {
        this.logam_pk = logam_pk;
    }

    public String getKaca_pk() {
        return kaca_pk;
    }

    public void setKaca_pk(String kaca_pk) {
        this.kaca_pk = kaca_pk;
    }

    public String getLainnya_pk() {
        return lainnya_pk;
    }

    public void setLainnya_pk(String lainnya_pk) {
        this.lainnya_pk = lainnya_pk;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
