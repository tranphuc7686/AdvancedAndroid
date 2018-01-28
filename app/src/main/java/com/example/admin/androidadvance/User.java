package com.example.admin.androidadvance;

/**
 * Created by Admin on 26/01/2018.
 */

public class User {
    private String namem,sdt;

    public String getNamem() {
        return namem;
    }

    public void setNamem(String namem) {
        this.namem = namem;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public User(String namem, String sdt) {

        this.namem = namem;
        this.sdt = sdt;
    }
}
