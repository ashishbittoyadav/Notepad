package com.headspirel.notepad;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class SaveFragment extends android.support.v4.app.Fragment implements View.OnClickListener{

    private View view;
    private Button save;
    //to handle the extra data
    private Bundle bundle;
    private EditText notedata;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_layout,container,false);
        notedata=view.findViewById(R.id.notedata);
        save=view.findViewById(R.id.savenote);
        save.setOnClickListener(this);
        bundle=getArguments();
        if(bundle!=null) {
            String str = bundle.getString("extradata");
            notedata.setText(str);
            save.setText("update");
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.savenote:

                DataBaseHelper dataBaseHelper=new DataBaseHelper(getContext());
                String data=notedata.getText().toString();
                Date date=new Date();
                String strdate=new SimpleDateFormat("dd/MM/yyyy").format(date);
                String date_detail=strdate;
                TableData tableData=new TableData();
                tableData.setNote(data);
                tableData.setDate(date_detail);
                if(save.getText().equals("update"))
                {
                    int position=bundle.getInt("extraposition");
                    position++;
                    dataBaseHelper.updateData(tableData,position);
                    save.setText("save");
                }
                else if(save.getText().equals("save"))
                {
                    dataBaseHelper.insertData(tableData);
                    save.setText("update");
                }


                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,new RecyclerFragment(),"recycle")
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}
