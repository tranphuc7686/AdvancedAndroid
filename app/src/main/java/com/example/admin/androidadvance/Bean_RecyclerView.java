package com.example.admin.androidadvance;

import android.graphics.Bitmap;

/**
 * Created by Admin on 09/01/2018.
 */

public class Bean_RecyclerView {
    private String tieuDe;
    private Bitmap bitmapHinhAnh;

    public Bean_RecyclerView(String tieuDe, Bitmap bitmapHinhAnh) {
        this.tieuDe = tieuDe;
        this.bitmapHinhAnh = bitmapHinhAnh;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public Bitmap getBitmapHinhAnh() {
        return bitmapHinhAnh;
    }

    public void setBitmapHinhAnh(Bitmap bitmapHinhAnh) {
        this.bitmapHinhAnh = bitmapHinhAnh;
    }
}
