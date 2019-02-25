package com.headspirel.notepad;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 *RecyclerFragment class extends Fragment class */
public class RecyclerFragment extends Fragment implements View.OnClickListener{
    private RecyclerView recyclerView;
    private Button addRecord;
    private AdapterNote adapterNote;
    private DataBaseHelper dataBaseHelper;
    private ArrayList<TableData> dataArrayList;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.recycle_view_fragment,container,false);
        recyclerView=view.findViewById(R.id.recycleview);
        addRecord=view.findViewById(R.id.add_record);
        createArrayList();
        createRecycleView();
        addRecord.setOnClickListener(this);
        return view;
    }


    /**
     *createArrayList method will get all the data from the database and store it in a arrayList of the 
     * Data class genric type.*/
    public void createArrayList()
    {
        dataBaseHelper=new DataBaseHelper(getContext());
        dataArrayList=dataBaseHelper.getAllData();
    }

    /**
     *createRecycleView method will create a recyclerView by the ArrayList object(dataArrayList)*/
    public void createRecycleView()
    {
        recyclerView=view.findViewById(R.id.recycleview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapterNote=new AdapterNote(getContext(),dataArrayList,getActivity().getSupportFragmentManager());
        recyclerView.setAdapter(adapterNote);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.add_record:
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new SaveFragment(),"save")
                    .addToBackStack(null)
                    .commit();
            break;
        }
    }
}