package com.example.admin.androidadvance;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerViewMusic;
    private AdapterRecycleViewMusic adapterRecycleViewMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        addControl();
        addEvent();

    }

    private void addControl() {
        mRecyclerViewMusic = findViewById(R.id.mRecyclerViewMusic);
        // add recycleview
        if(requestForPermission() == true){
            adapterRecycleViewMusic = new AdapterRecycleViewMusic(getFileMusic());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerViewMusic.setLayoutManager(layoutManager);
            mRecyclerViewMusic.setAdapter(adapterRecycleViewMusic);
        }

        //

    }
    public final String[] EXTERNAL_PERMS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};


    public final int EXTERNAL_REQUEST = 138;
    public boolean requestForPermission() {

        boolean isPermissionOn = true;
        final int version = Build.VERSION.SDK_INT;
        System.out.println(version + " version");
        if (version >= 23) {
            if (!canAccessExternalSd()) {
                isPermissionOn = false;
                requestPermissions(EXTERNAL_PERMS, EXTERNAL_REQUEST);
            }
        }

        return isPermissionOn;
    }

    public boolean canAccessExternalSd() {
        return (hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, perm));

    }

    private void addEvent() {

    }
    private  ArrayList<File> getFileMusic(){

        String path = Environment.getExternalStorageDirectory().toString()+"/Zing MP3";

        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        System.out.println(files.length + " fodspofkdspof");
        ArrayList<File> listFile = new ArrayList<>();
        Log.d("Files", "Size: "+ files.length);

        for (int i = 0; i < files.length; i++)
        {
            Log.d("Files", "FileName:" + files[i].getName());
            if(checkFileMp3(files[i].getName()) == true)
            listFile.add(files[i]);

        }
            return  listFile;
    }

    private boolean checkFileMp3(String pathFile){
        if(pathFile.substring(pathFile.length()-4,pathFile.length()).compareTo(".mp3")==0) {
            return true;
        }
        else
            return false;
    }
}
