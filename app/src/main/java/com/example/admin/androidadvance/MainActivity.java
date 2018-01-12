package com.example.admin.androidadvance;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRcvAdapter;
    public List<Bean_RecyclerView> data;
    private Toolbar mToolbar;
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(mToolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyView);
        data = new ArrayList<>();


        mRcvAdapter = new RecyclerAdapter(data);


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mRcvAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addItem: {
                // Create the Intent for Image Gallery.
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
                startActivityForResult(i, PICK_IMAGE);
                break;
            }
            case R.id.exit: {
                finish();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(getBaseContext(), "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_LONG).show();
                return;
            } else {


                DialogGetNameOfHero(getBitmapFromUri(data.getData()));


            }
        }

    }

    private void DialogGetNameOfHero(final Bitmap bitmap) {
        final String[] nameOfhero = new String[1];
        Button mSubmit, mCancel;
        EditText mEditText = null;
        final Dialog mDialog = new Dialog(this);
        mDialog.setTitle("The name of this hero?");
        mDialog.setContentView(R.layout.custom_dialog_nameofhero);
        mSubmit = mDialog.findViewById(R.id.submitNameImae);
        mCancel = mDialog.findViewById(R.id.cancel_dialog);
        mEditText = mDialog.findViewById(R.id.editTextNameImage);
        final EditText finalMEditText = mEditText;
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameOfhero[0] = finalMEditText.getText().toString();

                data.add(new Bean_RecyclerView(nameOfhero[0], bitmap));
                mRcvAdapter.notifyDataSetChanged();
                mDialog.dismiss();


            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        mDialog.show();

    }

    private Bitmap getBitmapFromUri(Uri uri) {
        final Uri selectedMediaUri = uri;
        Bitmap bitmap = null;

        if (selectedMediaUri.toString().contains("image")) {
            InputStream image_stream = null;
            try {

                image_stream = getContentResolver().openInputStream(selectedMediaUri);
                bitmap = BitmapFactory.decodeStream(image_stream);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        return bitmap;
    }
}
