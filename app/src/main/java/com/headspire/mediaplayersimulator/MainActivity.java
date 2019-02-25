package com.headspire.mediaplayersimulator;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,DoWork {

    /**
     * play button will start playing the audio from internal storage.*/
    private Button play;
    /**
     *stop button will stop the media player.*/
    private Button stop;
    /**
     *songSeekBar will show the progress status of the song and allow user to jump to a particular
     * time slot of the audio.*/
    private SeekBar songSeekBar;
    /**
     *songName will show the name of song currently played.*/
    private TextView songName;
    /**
     *select the song to play*/
    private Button select_song;
    /**
     *songTime will save the current position of the mediaplayer.*/
    private int songTime;
    /**
     *songFolder is the file that contains all the songs to be played.*/
    private File songFolder;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MediaPlayerOperation mediaPlayerOperation;
    private DataAdapter dataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play=findViewById(R.id.play_song);
        stop=findViewById(R.id.stop_song);
        select_song=findViewById(R.id.select_song);
        songName=findViewById(R.id.song_name);
        recyclerView=findViewById(R.id.recyclerview);
        songSeekBar=findViewById(R.id.song_progress);
        play.setOnClickListener(this);
        stop.setOnClickListener(this);
        select_song.setOnClickListener(this);
        songTime=0;
        mediaPlayerOperation=new MediaPlayerOperation(this,play,stop,songSeekBar,songName);
        songFolder=new File(Environment.getExternalStorageDirectory(),"songs");
        if(!songFolder.exists())
            songFolder.mkdirs();
        layoutManager=new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        dataAdapter=new DataAdapter(mediaPlayerOperation.getArrayListOfFile(),this);
    }

    /**
     *
     * @param v is the instance of View class by which programmer can identify the button clicked in the main view.
     */
    @Override
    public void onClick(View v) {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        getConfigure();
        else
        switch (v.getId())
        {
            case R.id.play_song:
                if(play.getText().equals("Play"))
                {
                        mediaPlayerOperation.playSong(0);
                }
                else if(play.getText().equals("Pause"))
                {
                    songTime=mediaPlayerOperation.pauseSong();
                }
                else if(play.getText().equals("Resume"))
                {
                    mediaPlayerOperation.resumeSong(songTime);
                }
                break;
            case R.id.stop_song:
                mediaPlayerOperation.stopSong();
                break;
            case R.id.select_song:
                    createFolder();
                    recyclerView.setAdapter(dataAdapter);
                    recyclerView.setLayoutManager(layoutManager);
                break;
        }
    }

    /**
     *createFolder method will create a external folder in external storage.*/
    public boolean createFolder()
    {
        File parentDir=new File(Environment.getExternalStorageDirectory()+"/SONGS");
        if(parentDir.exists())
        {
            Toast.makeText(this,"parent directory exists",Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            parentDir.mkdirs();
            return true;
        }
    }

    /**
     *getConfigure method will contain the code required for user permission.*/
    public void getConfigure()
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.cont,new PermissionFragment())
                .commit();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==2)
        {
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"permission granted.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this,"in the pause",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"resume",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this,"restart",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"destroy",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHit(int position) {
        mediaPlayerOperation.stopSong();
        mediaPlayerOperation.playSong(position);
    }
}
