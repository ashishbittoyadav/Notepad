package com.headspire.microphone;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;


/**
 *
 * @version: 1.0 13-feb-2019
 * @created by Ashish yadav
 * Objective: Audio Recorder and player simulator.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * record button is start the recording of the audio in a file*/
    private Button startRecord;
    /**
     *stopRecord button will stop the recording*/
    private Button stopRecord;
    /**
     *playMedia button will play the audio file stored in file system.*/
    private Button startMedia;
    /**
     *stopMedia button will stop media player.*/
    private Button stopMedia;
    /**
     *mediaRecorder is to perform operation of recording.*/
    private MediaRecorder mediaRecorder;
    /**
     *mediaPlayer is to perform operation of media player used for recorded audio.*/
    private MediaPlayer mediaPlayer;
    /**
     * file contains the path of the recorded file in the external storage.
     */
    File file=null;
    /**
     *operations instance of Operations class used to invoke the operation of Operations class
     * regarding media recording and media player.*/
    private Operations operations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startRecord=findViewById(R.id.startrecording);
        stopRecord=findViewById(R.id.stoprecording);
        startMedia=findViewById(R.id.playrecorded);
        stopMedia=findViewById(R.id.stop);
        startRecord.setOnClickListener(this);
        stopMedia.setOnClickListener(this);
        startMedia.setOnClickListener(this);
        stopRecord.setOnClickListener(this);
        stopRecord.setEnabled(false);
        startMedia.setEnabled(false);
        stopMedia.setEnabled(false);
        operations=new Operations(this);
    }

    @Override
    public void onClick(View v) {
        getConfigure();
        switch (v.getId())
        {
            case R.id.startrecording:
                operations.startRecorder();
                startMedia.setEnabled(false);
                stopMedia.setEnabled(false);
                startRecord.setEnabled(false);
                stopRecord.setEnabled(true);
                break;
            case R.id.stoprecording:
                operations.stopRecorder();
                stopRecord.setEnabled(false);
                startMedia.setEnabled(true);
                startRecord.setEnabled(true);
                break;
            case R.id.playrecorded:
                operations.playRecordedAudio();
                stopMedia.setEnabled(true);
                startMedia.setEnabled(false);
                startRecord.setEnabled(true);
                break;
            case R.id.stop:
                operations.stopMediaPlayer();
                stopMedia.setEnabled(false);
                startMedia.setEnabled(true);
                startRecord.setEnabled(true);
                break;
        }
    }


    /**
     * getConfigure function will request the desired permissions needed for all the functionality
     * of this project..
     */
    public void getConfigure()
    {
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ,Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO
                    },3);
        }
    }

}
