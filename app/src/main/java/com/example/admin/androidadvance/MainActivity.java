package com.example.admin.androidadvance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private AppCompatActivity mAppCompatActivity = this;
    private String mNameValues[] ={"Phúc","Thành","Công","Duy","Thanh"};
    private String mSdtValues[]={"09010","8095324","4324234","123123","47234934"};
    private RecyclerView mRecyclerView;
    private RecycleViewAdapter mRecycleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.mListUser);
        //create database
        SqliteHelper mSqliteHelper = new SqliteHelper(this);
        if(mSqliteHelper.requestForPermission() ==true)
        {
            mSqliteHelper.creatDatabase();
            mSqliteHelper.deleteDataTable("USER");
            int i = 0;
            while(true){
                mSqliteHelper.insertRecord(mNameValues[i],mSdtValues[i]);

                if(i >= mNameValues.length-1)
                    break;
                else i++;

            }



            //Create Recycleviewm


            mRecycleViewAdapter = new RecycleViewAdapter(mSqliteHelper.LoadData());

            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(mRecycleViewAdapter);


        }



    }
}
