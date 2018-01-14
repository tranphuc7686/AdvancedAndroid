package com.example.admin.androidadvance;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
   private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        textView = findViewById(R.id.tienTrinh);
        new TienTrinhSplash(textView).execute();

    }
    class TienTrinhSplash extends AsyncTask<Void,Integer,Void> {



        TextView textView;
        int i=0;
        public TienTrinhSplash(TextView textView) {
            this.textView = textView;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                for(  i = 1 ; i< 100 ;i++){
                    Thread.sleep(30);
                    publishProgress(i);

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);



            textView.setText(i+" %");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent a = new Intent(SplashScreen.this,MainActivity.class);
            startActivity(a);
        }
    }
}
