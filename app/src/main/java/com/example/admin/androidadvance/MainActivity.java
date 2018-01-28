package com.example.admin.androidadvance;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button mButtonToGetData;
    private String mUrlDataJson;
    private EditText mEditText;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonToGetData = findViewById(R.id.btnGetData);
        mEditText = findViewById(R.id.txtInput);
        mText = findViewById(R.id.showData);
        mUrlDataJson = "https://api.github.com/users/google/repos";
        mButtonToGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TienTrinhLoadJson mTienTrinhLoadJson = new TienTrinhLoadJson();
                mTienTrinhLoadJson.execute(mUrlDataJson);
            }
        });

    }
    class TienTrinhLoadJson extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder duLieu=null;
            try {
                URL mURL = new URL(strings[0]);
                HttpURLConnection mHttpURLConnection = (HttpURLConnection) mURL.openConnection();
                InputStream mInputStream = mHttpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(mInputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String dong = "";
                 duLieu = new StringBuilder();
                while((dong = bufferedReader.readLine()) !=null){
                    duLieu.append(dong);


                }

                mInputStream.close();
                reader.close();
                bufferedReader.close();




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return duLieu.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ParserData a = new ParserData(s,mEditText.getText().toString());
            GithubData b = a.GetNamePackage();
            if(b != null)
                mText.setText(b.toString());
            else
                Toast.makeText(getBaseContext(),"Không Tồn Tại ID",Toast.LENGTH_SHORT).show();






        }
    }
}
