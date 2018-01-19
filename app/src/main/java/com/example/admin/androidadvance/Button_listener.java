package com.example.admin.androidadvance;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Created by Admin on 17/01/2018.
 */

public class Button_listener extends BroadcastReceiver {
    private ServiceMusic serviceMusic;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(intent.getExtras().getInt("id"));
        serviceMusic = new ServiceMusic();
        Intent intent1 = new Intent(context,ServiceMusic.class);
        context.bindService(intent1,mServiceConnection,Context.BIND_AUTO_CREATE);
        serviceMusic.pauseAction();
    }
    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ServiceMusic.LoccalBinder loccalBinder =( ServiceMusic.LoccalBinder)iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
