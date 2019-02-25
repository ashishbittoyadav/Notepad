package com.headspire.microphone;


import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import java.io.File;

/**
 * This class contains the methods of the recording and playing the files..
 */
public class Operations {

    private MediaPlayer mediaPlayer;
    private MediaRecorder mediaRecorder;
    private Context context;
    private File audioFile=null;

    Operations(Context context)
    {
        this.context=context;
    }
    /**
     * stopRecorder will stop the recorder if its not null.
     */
    void stopRecorder()
    {
        if (mediaRecorder != null)
            mediaRecorder.stop();

    }

    /**
     * startRecord method will initialize and start the recorder.
     */
    void startRecorder()
    {
        try
        {
            audioFile=createFileInExternalStorage();
            mediaRecorder=new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            if (Build.VERSION.SDK_INT >= 26)
                mediaRecorder.setOutputFile(audioFile);
            else
                mediaRecorder.setOutputFile(audioFile.getPath());


            mediaRecorder.prepare();
            mediaRecorder.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    /**
     * createFileInExternalStorage will create a file in External storage which will later be
     * used in storing the audio recorded.
     * @return File
     */
    private File createFileInExternalStorage()
    {
        File audioFile=null;
        try
        {
            File directory= Environment.getExternalStorageDirectory();
            audioFile=new File(directory,"AUD"+System.currentTimeMillis()+".mp3");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return audioFile;
    }


    /**
     * playRecordedAudio method is playing the file recorded by recorder.
     */
    void playRecordedAudio()
    {
        mediaPlayer=  MediaPlayer.create(context, Uri
                .fromFile(audioFile));
        if(mediaPlayer!=null) {
            try {
                mediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * stopMediaPlayer method is stop the media player.
     */
    void stopMediaPlayer() {
        try {
            if (mediaPlayer != null)
                mediaPlayer.stop();
            mediaPlayer.release();
        } catch (Exception e)
        {
        }
    }
}
