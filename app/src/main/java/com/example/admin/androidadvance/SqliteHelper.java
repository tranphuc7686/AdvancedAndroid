package com.example.admin.androidadvance;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Admin on 26/01/2018.
 */

public class SqliteHelper {
    private SQLiteDatabase mSqLiteDatabase;
    private AppCompatActivity mAppCompatActivity;
    private  ContentValues contentValues;

    public final String[] EXTERNAL_PERMS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};


    public final int EXTERNAL_REQUEST = 138;

    public SqliteHelper(AppCompatActivity mAppCompatActivity) {
        this.mAppCompatActivity = mAppCompatActivity;
    }

    public void creatDatabase() {
        Context ctx = this.mAppCompatActivity.getBaseContext();
        String DATABASE_PATH = ctx.getFilesDir().getPath()+"/UserContact.db";
        System.out.println(DATABASE_PATH);
        this.mSqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH,null);
        this.mSqLiteDatabase = SQLiteDatabase.openDatabase(DATABASE_PATH,null,SQLiteDatabase.CREATE_IF_NECESSARY);
    }



    public boolean requestForPermission() {

        boolean isPermissionOn = true;
        final int version = Build.VERSION.SDK_INT;
        System.out.println(version + " version");
        if (version >= 23) {
            if (!canAccessExternalSd()) {
                isPermissionOn = false;
                mAppCompatActivity.requestPermissions(EXTERNAL_PERMS, EXTERNAL_REQUEST);
            }
        }

        return isPermissionOn;
    }
    public boolean canAccessExternalSd() {
        return (hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(mAppCompatActivity.getBaseContext(), perm));

    }
    public void createTable(){
        String sql="CREATE TABLE USER(NAME TEXT PRIMARY KEY,SDT TEXT)";

        this.mSqLiteDatabase.execSQL(sql);
    }
    public void insertRecord(String valueOfName,String valuesOfSdt){
         contentValues = new ContentValues();
        contentValues.put("NAME",valueOfName);
        contentValues.put("SDT",valuesOfSdt);
        if( mSqLiteDatabase.insert("USER",null,contentValues)== -1 ){
            Toast.makeText(mAppCompatActivity.getBaseContext(),"Lỗi insert",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(mAppCompatActivity.getBaseContext(),"Thành Công insert",Toast.LENGTH_SHORT).show();


    }
    public ArrayList<User> LoadData()
    {   ArrayList<User> arrUser = new ArrayList<User>();
        Cursor c = mSqLiteDatabase.query("USER",null,null,null,null,null,null);
        c.moveToFirst();

        while (!c.isAfterLast())
        {



            arrUser.add(new User(c.getString(0),c.getString(1)));

            c.moveToNext();

        }
        c.close();
        return arrUser;
    }
    public void deleteDataTable(String tableName){
        this.mSqLiteDatabase.delete(tableName,null,null);

    }
}
