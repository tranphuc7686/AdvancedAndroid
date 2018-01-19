package com.example.admin.androidadvance;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Admin on 18/01/2018.
 */

public class ServiceMusic extends Service {
    IBinder iBinder = new LoccalBinder();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class LoccalBinder extends Binder {

        LoccalBinder getLoccalBinder(){
            return LoccalBinder.this;
        }
    }
    public String XinChao(){
        return "Xin Chao cac ban";
    }
    public void pauseAction() {


        SongActivity.mediaPlayer.pause();
    }
}
