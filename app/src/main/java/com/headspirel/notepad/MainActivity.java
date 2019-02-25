package com.headspirel.notepad;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * @version: 1.0
 * @created-by: Ashish yadav
 * @Objective: create a notepad to using SQLite.
 */

public class MainActivity extends AppCompatActivity{

    RecyclerFragment single_note;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager=getSupportFragmentManager();
        single_note=new RecyclerFragment();
        single_note.setArguments(savedInstanceState);
        fragmentManager.beginTransaction()
                .replace(R.id.container,single_note,"recycle")
                .addToBackStack("recycle")
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
