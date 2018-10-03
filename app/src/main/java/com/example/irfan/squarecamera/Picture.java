package com.example.irfan.squarecamera;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

public class Picture{
    String id;
    String waktuMulai;
    String waktuSelesai;
    String waktu;
    public Picture() {
    }

    public Picture(String id,String waktuMulai, String waktuSelesai, String waktu){
        this.id=id;
        this.waktuMulai=waktuMulai;
        this.waktuSelesai=waktuSelesai;
        this.waktu=waktu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWaktuMulai() {
        return waktuMulai;
    }

    public void setWaktuMulai(String waktuMulai) {
        this.waktuMulai = waktuMulai;
    }

    public String getWaktuSelesai() {
        return waktuSelesai;
    }

    public void setWaktuSelesai(String waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}