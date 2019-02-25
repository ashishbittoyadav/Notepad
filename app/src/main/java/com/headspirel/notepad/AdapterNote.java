package com.headspirel.notepad;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.euicc.DownloadableSubscription;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *AdapterNote extends RecyclerView class for binding the data to recycler view in Main Activity.*/
public class AdapterNote extends RecyclerView.Adapter<AdapterNote.DataHold>{

    ArrayList<TableData> noteArrayList;
    Context context;
    FragmentManager fragmentManager;
    LayoutInflater layoutInflater;


    public AdapterNote(Context context, ArrayList<TableData> noteArrayList,FragmentManager fragmentManager)
    {
        this.noteArrayList=noteArrayList;
        this.context=context;
        this.fragmentManager=fragmentManager;
    }

    /**
     * This method will create the view holder for recycler view.
     * @param viewGroup contains the view of the parent
     * @param i position of integer type
     * @return view of DataHold
     */
    @NonNull
    @Override
    public DataHold onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View view=layoutInflater.inflate(R.layout.menu_layout,viewGroup,false);
        return (new DataHold(view));
    }

    /**
     * This method will bind the data with the view.
     * @param dataHold will allow to set the components data.
     * @param i contains the position
     */
    @Override
    public void onBindViewHolder(@NonNull DataHold dataHold, int i) {
        dataHold.note.setText(noteArrayList.get(i).getNote());
        dataHold.date.setText(noteArrayList.get(i).getDate());
        dataHold.id.setText(noteArrayList.get(i).getId()+"");
    }

    /**
     *
     * @return size of the array list.
     */
    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }

    public class DataHold extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView note;
        private TextView date;
        private TextView id;
        private LinearLayout edit;
        private ImageView delete;

        public DataHold(@NonNull View itemView) {
            super(itemView);
            note=itemView.findViewById(R.id.note_name);
            edit=itemView.findViewById(R.id.editnote);
            date=itemView.findViewById(R.id.date);
            id=itemView.findViewById(R.id.columnid);
            delete=itemView.findViewById(R.id.delete);
            delete.setOnClickListener(this);
            edit.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.editnote:
                    Bundle bundle = new Bundle();
                    bundle.putString("extradata", note.getText().toString());
                    bundle.putInt("extraposition",getAdapterPosition());
                    SaveFragment saveFragment = new SaveFragment();
                    saveFragment.setArguments(bundle);
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, saveFragment, "new")
                            .commit();
                    break;
                case R.id.delete:
                    DataBaseHelper dataBaseHelper=new DataBaseHelper(context);
                    dataBaseHelper.delete(note.getText().toString());
                    fragmentManager.beginTransaction()
                            .replace(R.id.container,new RecyclerFragment())
                            .commit();
                    break;
            }
        }
    }
}
