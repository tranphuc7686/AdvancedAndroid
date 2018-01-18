package com.example.admin.androidadvance;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class SongActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnForward,btnPause,btnPlay,btnBackward;
    private ImageView btnFinish;
    private MediaPlayer mediaPlayer;
    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private TextView tvStatus,tvStartTime,tvFinalTime,tvSongName;
    public static int oneTimeOnly = 0;
    private   File song;
    // notice
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private int notification_id;
    private RemoteViews remoteViews;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        // Tham chiếu id của các View
        btnForward= findViewById(R.id.btnForward);
        btnPause =findViewById(R.id.btnPause);
        btnPlay = findViewById(R.id.btnPlay);
        btnBackward =findViewById(R.id.btnBackward);
        btnFinish = findViewById(R.id.btnFinish);
        tvSongName = findViewById(R.id.nameOfTheSong);
        tvStatus = findViewById(R.id.tvStatus);
        tvStartTime= findViewById(R.id.tvStartTime);
        tvFinalTime= findViewById(R.id.tvFinalTime);
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
        tvSongName.setText("     " +song.getName().toString());
        String filePath = song.getPath();

        mediaPlayer = MediaPlayer.create(this,Uri.parse(filePath));
        //





        seekbar=(SeekBar)findViewById(R.id.seekBar);
        seekbar.setClickable(false);
        btnPause.setEnabled(false);





    }

    public void notiButtonClicked() {

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);

        remoteViews = new RemoteViews(getPackageName(), R.layout.activity_notification);


        notification_id = (int) System.currentTimeMillis();

        Intent button_intent = new Intent("button_click");
        button_intent.putExtra("id", notification_id);
        PendingIntent button_pending_event = PendingIntent.getBroadcast(context, notification_id,
                button_intent, 0);

        remoteViews.setOnClickPendingIntent(R.id.btnPlayNoti, button_pending_event);

        Intent notification_intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notification_intent, 0);

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setCustomBigContentView(remoteViews)
                .setContentIntent(pendingIntent);

        notificationManager.notify(notification_id, builder.build());


    }

    @Override
    protected void onStart() {
        super.onStart();



    }

    @Override
    protected void onStop() {

        myHandler.removeCallbacks(UpdateSongTime);


        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
        super.onStop();

    }

    private File getDataFileMusic(){

        Intent a = getIntent();


        return (File)a.getSerializableExtra("song");
    }


    @Override
    protected void onPause() {
        super.onPause();


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


            seekbar.setProgress((int)startTime);

            myHandler.postDelayed(this, 100);
        }
    };

    public void playAction(View view){
        notiButtonClicked();

        tvStatus.setText("Playing sound");
        btnPause.setEnabled(true);
        mediaPlayer.start();

        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();


            seekbar.setMax((int) finalTime);
            System.out.println(finalTime +" dhsahdoiashdois");
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



        myHandler.postDelayed(UpdateSongTime,100);
    }

    public void pauseAction(View v) {

        tvStatus.setText("Pausing sound");
        mediaPlayer.pause();
    }

    public void forwardAction(View v) {

        int temp = (int)startTime;

        if((temp+forwardTime)<=finalTime){
            startTime = startTime + forwardTime;
            mediaPlayer.seekTo((int) startTime);
            tvStatus.setText("You have Jumped forward 5 seconds");
        }
        else{
            tvStatus.setText("Cannot jump forward 5 seconds");
        }
    }

    public void backwardAction(View v) {

        int temp = (int)startTime;

        if((temp-backwardTime)>0){
            startTime = startTime - backwardTime;
            mediaPlayer.seekTo((int) startTime);
            tvStatus.setText("You have Jumped backward 5 seconds");
        }
        else{
            tvStatus.setText("Cannot jump backward 5 seconds");
        }
    }
    public void createNotification() {
        // Prepare intent which is triggered if the
        // notification is selected
        System.out.println("aloo");
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(this)
                .setContentTitle("New mail from " + "test@gmail.com")
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnFinish:{

                finish();
                break;
            }
            case R.id.btnPause:{
                pauseAction(view);
                break;
            }
            case R.id.btnPlay:{
                playAction(view);
                break;
            }
            case R.id.btnBackward:{
                backwardAction(view);
                break;
            }
            case R.id.btnForward:{
                forwardAction(view);
                break;
            }
        }
    }
}