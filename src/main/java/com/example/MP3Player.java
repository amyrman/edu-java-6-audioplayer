package com.example;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MP3Player {
    private MediaPlayer mediaPlayer;
    private boolean isPlaying;
    

    public void play(String filePath) {
        new JFXPanel();

        mediaPlayer = new MediaPlayer(new Media(new File(filePath).toURI().toString()));
        mediaPlayer.play();
        isPlaying = true;
    }

    public void pause() {
        if (mediaPlayer != null && isPlaying) {
            mediaPlayer.pause();
            isPlaying = false;
        }
    }

    public void stop() {
        if (mediaPlayer != null && isPlaying) {
            mediaPlayer.stop();
            isPlaying = false;
        }
        mediaPlayer.stop();
    }

    public boolean isPlaying() {
        return isPlaying;
    }
    // Other methods here
}
