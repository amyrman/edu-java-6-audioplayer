package com.example;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MP3Player {
    private MediaPlayer mediaPlayer;

    public void play(String filePath) {
        new JFXPanel();
        Media media = new Media(new File(filePath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void stop() {
        mediaPlayer.stop();
    }
}
