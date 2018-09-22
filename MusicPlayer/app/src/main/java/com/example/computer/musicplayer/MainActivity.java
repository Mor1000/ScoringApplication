package com.example.computer.musicplayer;

import android.content.res.Resources;
import android.media.MediaPlayer;
import android.support.annotation.RawRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer=MediaPlayer.create(this,R.raw.song);
    }

    public void onStart(View view) {
        mediaPlayer.start();
    }

    public void onStop(View view) {
        mediaPlayer.pause();
    }
}
