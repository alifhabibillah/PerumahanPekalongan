package com.alif.perumahanpekalongan;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Alif on 18/11/2016.
 */

public class MyData implements Serializable {

    private String kdperum, nmperum, kelurahan, kecamatan, detail, foto, username, tanggal;

    public MyData(String kdperum, String nmperum, String kelurahan, String kecamatan, String detail, String foto, String username, String tanggal) {
        this.kdperum = kdperum;
        this.nmperum = nmperum;
        this.kelurahan = kelurahan;
        this.kecamatan = kecamatan;
        this.detail = detail;
        this.foto = foto;
        this.username = username;
        this.tanggal = tanggal;
    }

    public String getKdperum() {
        return kdperum;
    }

    public void setKdperum(String kdperum) {
        this.kdperum = kdperum;
    }

    public String getNmperum() {
        return nmperum;
    }

    public void setNmperum(String nmperum) {
        this.nmperum = nmperum;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
