package com.example.admin.androidadvance;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 15/01/2018.
 */

public class AdapterRecycleViewMusic extends RecyclerView.Adapter<AdapterRecycleViewMusic.RecyclerViewHolder>{

    private List<File> data = new ArrayList<>();

    public AdapterRecycleViewMusic(List<File> data) {
        this.data = data;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_item_mrecyclerviewmusic, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        holder.txtUserName.setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtUserName;
        CardView cardView;
        Context context;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtUserName = (TextView) itemView.findViewById(R.id.nameOfSong);
            cardView = itemView.findViewById(R.id.cardViewItem);
            cardView.setOnClickListener(this);
            context = itemView.getContext();
        }


        @Override
        public void onClick(View view) {
            Intent pushDataToSongActivity = new Intent(context,SongActivity.class);

            pushDataToSongActivity.putExtra("song",data.get(getPosition()));
            context.startActivity(pushDataToSongActivity);
        }
    }
}