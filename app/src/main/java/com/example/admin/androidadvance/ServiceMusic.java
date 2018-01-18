package com.example.admin.androidadvance;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;

/**
 * Created by Admin on 18/01/2018.
 */

public class ServiceMusic extends Service {
    private File mFileMusic ;

    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mFileMusic = (File) intent.getExtras().getSerializable("song");

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
