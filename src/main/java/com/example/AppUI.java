package com.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AppUI {
    private MediaPlayer mediaPlayer;
    ObservableList<String> listModel;
    private ListView<String> fileList;
    ComboBox<String> sortBox;

    public void show() {
        // Create the UI
        Label label = new Label("Java Music Player");

        fileList = new ListView<>();
        listModel = FXCollections.observableArrayList();
        fileList.setItems(listModel);

        Button openButton = new Button("Open");
        openButton.setOnAction(e -> openFileExplorer());

        Button playPauseButton = new Button("Play/Pause");
        playPauseButton.setOnAction(e -> playPauseSelectedFile());

        Button stopButton = new Button("Stop");
        stopButton.setOnAction(e -> stopSelectedFile());

        sortBox = new ComboBox<>();
        sortBox.getItems().addAll("Ascending", "Descending");
        sortBox.setValue("Ascending");
        sortBox.setOnAction(e -> sortList());

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            int confirmed = showConfirmationDialog("Are you sure you want to exit the application?");
            if (confirmed == 1) {
                Platform.exit();
            }
        });

        HBox buttonBox = new HBox(openButton, playPauseButton, stopButton, sortBox, exitButton);
        buttonBox.setSpacing(10);

        VBox root = new VBox(label, fileList, buttonBox);
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        // Create the scene and stage
        Scene scene = new Scene(root, 600, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Java Exercise #6: Java Music Player");

        // Add a window listener to the stage to handle closing events
        stage.setOnCloseRequest(e -> {
            e.consume();
            int confirmed = showConfirmationDialog("Are you sure you want to exit the application?");
            if (confirmed == 1) {
                Platform.exit();
            }
        });

        // Show the stage
        stage.show();
    }

    private void openFileExplorer() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Audio Files");
        fileChooser.setInitialDirectory(new File("src\\main\\resources\\exampleaudio"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav", "*.aiff", "*.au", "*.snd", "*.mid",
                        "*.rmi"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        if (selectedFiles != null) {
            addFilesToList(selectedFiles);
        }
    }

    private void playPauseSelectedFile() {
        int selectedIndex = fileList.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            String filePath = listModel.get(selectedIndex);
            if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                // Pause the media player if it's already playing
                mediaPlayer.pause();
            } else if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                // Resume playback if the media player is paused
                mediaPlayer.play();
            } else {
                // Start playing the media file
                Media media = new Media(new File(filePath).toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
            }
        }
    }

    private void stopSelectedFile() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
        }
    }

    void sortList() {
        String selected = sortBox.getSelectionModel().getSelectedItem();
        List<String> list = new ArrayList<>(listModel);
        if (selected.equals("Ascending")) {
            list.sort(String::compareTo);
        } else if (selected.equals("Descending")) {
            list.sort((s1, s2) -> s2.compareTo(s1));
        }
        listModel.setAll(list);
    }

    void addFilesToList(List<File> files) {
        for (File file : files) {
            if (file.isFile()) {
                try {
                    String filePath = file.getAbsolutePath();
                    listModel.add(filePath);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private int showConfirmationDialog(String message) {
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        Alert alert = new Alert(AlertType.CONFIRMATION, message, yesButton, noButton);
        alert.setHeaderText(null);
        alert.setTitle("Confirmation");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == yesButton) {
                return 1;
            } else if (result.get() == noButton) {
                return 0;
            }
        }
        return 0;
    }
}
