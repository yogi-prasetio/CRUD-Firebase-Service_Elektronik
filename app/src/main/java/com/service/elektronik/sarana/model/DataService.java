package com.service.elektronik.sarana.model;

import java.io.Serializable;

public class DataService implements Serializable {

    private String nama;
    private String alamat;
    private String nohp;
    private String merk;
    private String jenis;
    private String warna;
    private String kerusakan;
    private String key;

    public  DataService() { }
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

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public String getKerusakan() {
        return kerusakan;
    }

    public void setKerusakan(String kerusakan) {
        this.kerusakan = kerusakan;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String toString() {
        return "" +nama+"" +
                "" +alamat+""+
                ""+nohp+""+
                "" +merk+"" +
                "" +jenis+""+
                ""+warna+""+
                ""+kerusakan;
    }
    public DataService(String nm, String almt, String phone, String mrk, String jns, String wrn, String rsk){
        nama=nm;
        alamat=almt;
        nohp=phone;
        merk=mrk;
        jenis=jns;
        warna=wrn;
        kerusakan=rsk;
    }
}
