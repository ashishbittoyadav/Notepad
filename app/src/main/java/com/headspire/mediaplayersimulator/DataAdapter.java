package com.headspire.mediaplayersimulator;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.SimpleOnItemTouchListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataHolder>
{
    private DoWork doWork;
    private ArrayList<File> arrayList;
    public DataAdapter(ArrayList<File> arrayList,DoWork doWork)
    {
        this.arrayList=arrayList;
        this.doWork = doWork;
    }
    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.song_list_item,viewGroup,false);
        DataHolder dataHolder=new DataHolder(view);
        return dataHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull DataHolder dataHolder, int i) {
        dataHolder.songName.setText(arrayList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public  class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView songName;
        private LinearLayout songNameContainer;
        public DataHolder(@NonNull View itemView) {
            super(itemView);
            songName=itemView.findViewById(R.id.name_of_song);
            songNameContainer=itemView.findViewById(R.id.song_name_container);
            songNameContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.song_name_container:
                    doWork.onHit(getAdapterPosition());
                    break;
            }
        }
    }
}
interface DoWork
{
    public void onHit(int position);
}