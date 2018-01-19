package com.example.admin.androidadvance;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class SongActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnForward, btnPause, btnPlay, btnBackward;
    private ImageView btnFinish;
    public static MediaPlayer mediaPlayer;
    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();
    ;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private TextView tvStatus, tvStartTime, tvFinalTime, tvSongName;
    public static int oneTimeOnly = 0;
    private File song;
    // notice
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private int notification_id;
    private RemoteViews remoteViews;
    private Context context;
    private Button_listener button_listener;
    private ServiceMusic serviceMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        // Tham chiếu id của các View
        btnForward = findViewById(R.id.btnForward);
        btnPause = findViewById(R.id.btnPause);
        btnPlay = findViewById(R.id.btnPlay);
        btnBackward = findViewById(R.id.btnBackward);
        btnFinish = findViewById(R.id.btnFinish);
        tvSongName = findViewById(R.id.nameOfTheSong);
        tvStatus = findViewById(R.id.tvStatus);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvFinalTime = findViewById(R.id.tvFinalTime);
        context = this;
        //add Listener cho Button

        btnForward.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnBackward.setOnClickListener(this);
        btnFinish.setOnClickListener(this);

        //
        // ... get  Music Data
        song = getDataFileMusic();
        tvSongName.setText("     " + song.getName().toString());
        String filePath = song.getPath();

        mediaPlayer = MediaPlayer.create(this, Uri.parse(filePath));
        //


        seekbar = (SeekBar) findViewById(R.id.seekBar);
        seekbar.setClickable(false);
        btnPause.setEnabled(false);


    }
    public void songService(){
        ServiceConnection mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                ServiceMusic.LoccalBinder loccalBinder =( ServiceMusic.LoccalBinder)iBinder;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
        serviceMusic = new ServiceMusic();
        Intent intent1 = new Intent(context,ServiceMusic.class);
        bindService(intent1,mServiceConnection,Context.BIND_AUTO_CREATE);
        Toast.makeText(context,serviceMusic.XinChao(),Toast.LENGTH_SHORT).show();

        //

    }

    public void notiButtonClicked() {

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);

        remoteViews = new RemoteViews(getPackageName(), R.layout.activity_notification);


        CharSequence name = "Ragav";
        String desc = "this is notific";
        int imp = NotificationManager.IMPORTANCE_HIGH;
        final String ChannelID = "my_channel_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(ChannelID, name,
                    imp);
            mChannel.setDescription(desc);
            mChannel.setLightColor(Color.CYAN);
            mChannel.canShowBadge();
            mChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(mChannel);
        }

        final int ncode = 101;


        //

        Intent button_intent = new Intent("button_click");
        button_intent.putExtra("id", notification_id);
        PendingIntent button_pending_event = PendingIntent.getBroadcast(context, notification_id,
                button_intent, 0);
        regiterBroascast();
        remoteViews.setOnClickPendingIntent(R.id.btnPauseNotice, button_pending_event);


        Notification n = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            n = new Notification.Builder(context, ChannelID)
                    .setContentTitle(" Music App")
                    .setContentText(" Your song is running in background !! ")
                    .setBadgeIconType(R.mipmap.ic_launcher)
                    .setNumber(5)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setAutoCancel(true)
                    .setCustomBigContentView(remoteViews)
                    .build();
        }
        notificationManager.notify(ncode, n);


    }

    private void regiterBroascast() {
        button_listener = new Button_listener();
        IntentFilter filter = new IntentFilter("button_click");
        registerReceiver(button_listener, filter);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop() {


        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacks(UpdateSongTime);


        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    private File getDataFileMusic() {

        Intent a = getIntent();


        return (File) a.getSerializableExtra("song");
    }


    @Override
    protected void onPause() {
        super.onPause();
        notiButtonClicked();

    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();

            tvStartTime.setText(String.format("%d min, %d sec",

                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );


            seekbar.setProgress((int) startTime);

            myHandler.postDelayed(this, 100);
        }
    };

    public void playAction(View view) {


        tvStatus.setText("Playing sound");
        btnPause.setEnabled(true);
        mediaPlayer.start();

        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();


        seekbar.setMax((int) finalTime);
        System.out.println(finalTime + " dhsahdoiashdois");
        oneTimeOnly = 1;

        tvFinalTime.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
        );

        tvStartTime.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
        );


        myHandler.postDelayed(UpdateSongTime, 100);
    }

    public void pauseAction(View v) {

        tvStatus.setText("Pausing sound");
        mediaPlayer.pause();
    }

    public void forwardAction(View v) {

        int temp = (int) startTime;

        if ((temp + forwardTime) <= finalTime) {
            startTime = startTime + forwardTime;
            mediaPlayer.seekTo((int) startTime);
            tvStatus.setText("You have Jumped forward 5 seconds");
        } else {
            tvStatus.setText("Cannot jump forward 5 seconds");
        }
    }

    public void backwardAction(View v) {

        int temp = (int) startTime;

        if ((temp - backwardTime) > 0) {
            startTime = startTime - backwardTime;
            mediaPlayer.seekTo((int) startTime);
            tvStatus.setText("You have Jumped backward 5 seconds");
        } else {
            tvStatus.setText("Cannot jump backward 5 seconds");
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnFinish: {

                finish();
                break;
            }
            case R.id.btnPause: {
                pauseAction(view);
                break;
            }
            case R.id.btnPlay: {

                playAction(view);
                break;
            }
            case R.id.btnBackward: {
                backwardAction(view);
                break;
            }
            case R.id.btnForward: {
                forwardAction(view);
                break;
            }
        }
    }
}