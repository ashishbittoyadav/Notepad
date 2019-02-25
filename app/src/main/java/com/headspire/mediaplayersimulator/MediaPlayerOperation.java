package com.headspire.mediaplayersimulator;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.telephony.mbms.FileInfo;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MediaPlayerOperation implements SeekBar.OnSeekBarChangeListener {


    /**
     * play button will start playing the audio from internal storage.*/
    private Button play;
    /**
     *stop button will stop the media player.*/
    private Button stop;

    /**
     *songName will show the name of song currently played.*/
    private TextView songName;
    /**
     *mediaPlayer will store the instance of MediaPlayer class.*/
    private MediaPlayer mediaPlayer;
    /**
     *songSeekBar will show the progress status of the song and allow user to jump to a particular
     * time slot of the audio.*/
    private SeekBar songSeekBar;

    /**
     *seekBarHandler will constantly update the seekbar according to MediaPlayer object using a thread
     * other than UI thread.*/
    private Handler seekBarHandler;
    /**
     *runnable allow to run seekbar updation operation in different thread.*/
    private Runnable runnable;
    private String songPath;
    private ArrayList<File> files;
    private Context context;

    public MediaPlayerOperation(Context context, Button play, Button stop, SeekBar seekBar,TextView songName)
    {
        this.context=context;
        this.songName=songName;
        this.play=play;
        this.stop=stop;
        songSeekBar=seekBar;
        songSeekBar.setOnSeekBarChangeListener(this);
        seekBarHandler=new Handler();
        stop.setEnabled(false);
    }

    public File getAudioFile()
    {
        File userSong= new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/songs");
        return userSong;
    }

    /**
     *playSong method will instanciate and play the audio.*/
    public void playSong(int position)
    {
        if(mediaPlayer!=null && mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        else {
            getAudioFileToPlay(position);
            File gaana = new File(getAudioFile() + "/" + songPath);
            play.setText("Pause");
            stop.setEnabled(true);
            mediaPlayer = MediaPlayer.create(context, Uri
                    .fromFile(gaana));
            songSeekBar.setMax(mediaPlayer.getDuration());
            songName.setText(gaana.getName());
            if (mediaPlayer != null) {
                try {
                    playUpdate();
                    mediaPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * getArrayListOfFile method will create a arrayList of songs in the directory.
     * @return array list of songs.
     */
    public ArrayList<File> getArrayListOfFile()
    {
        //getting all the audio files in the specified Directory...
        File f=getAudioFile();
        files=new ArrayList<>(Arrays.asList(f.listFiles()));
        //..................
        return files;
    }

    /**
     * getAudioFileToPlay method will set the absolute path of the song to be played.
     * @param position int value from recycler view.
     */
    public void getAudioFileToPlay(int position)
    {
        songPath=files.get(position).getName();
    }

    /**
     *pauseSong method will pause the song at a specific time slot.*/
    public int pauseSong()
    {
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
        play.setText("Resume");
        return mediaPlayer.getCurrentPosition();
    }
    /**
     *resumeSong method will play the song from in between.*/
    public void resumeSong(int songTime)
    {
        if(mediaPlayer!=null)
        {
            mediaPlayer.seekTo(songTime);
            playUpdate();
            mediaPlayer.start();
        }
        play.setText("Pause");
    }

    /**
     *stopSong method will stop the song.*/
    public void stopSong()
    {
        if(mediaPlayer!=null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            songSeekBar.setProgress(0);
            seekBarHandler.removeCallbacks(runnable);
            stop.setEnabled(false);
            mediaPlayer.release();
            mediaPlayer=null;
            play.setText("Play");
        }

    }

    /**
     * onProgressChanged method will set the song time line to user choice location in seekbar.
     * @param seekBar contains the reference of seekBar
     * @param progress contains the integer value return by seekbar
     * @param fromUser if user change the seekbar value explicitly then this variable contains true.
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser)
        {
            mediaPlayer.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /**
     *update the seek bar in UI thread, according to the song played.*/
    public void playUpdate()
    {
        songSeekBar.setProgress(mediaPlayer.getCurrentPosition());
        runnable=new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer.isPlaying())
                    playUpdate();
            }
        };
        seekBarHandler.postDelayed(runnable,500);
    }
}
