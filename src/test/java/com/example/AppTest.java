package com.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.ComboBox;

public class AppTest {
    @BeforeAll
    public static void initToolkit() {
        // Initialize the JavaFX toolkit
        new JFXPanel();
    }

    @Test
    public void testAddFilesToList() {
        // Create a new instance of AppUI
        AppUI appUI = new AppUI();

        // Call the show() method to initialize the UI
        Platform.runLater(() -> appUI.show());

        // Create a list of mock files to add
        List<File> files = new ArrayList<>();
        File mockFile1 = Mockito.mock(File.class);
        when(mockFile1.isFile()).thenReturn(true);
        when(mockFile1.getAbsolutePath()).thenReturn("path/to/file1.mp3");
        File mockFile2 = Mockito.mock(File.class);
        when(mockFile2.isFile()).thenReturn(true);
        when(mockFile2.getAbsolutePath()).thenReturn("path/to/file2.mp3");
        File mockFile3 = Mockito.mock(File.class);
        when(mockFile3.isFile()).thenReturn(true);
        when(mockFile3.getAbsolutePath()).thenReturn("path/to/file3.mp3");
        files.add(mockFile1);
        files.add(mockFile2);
        files.add(mockFile3);

        // Call the addFilesToList() method
        appUI.addFilesToList(files);

        // Check that the listModel contains the expected file paths
        assertThat(appUI.listModel).containsExactly(
                "path/to/file1.mp3",
                "path/to/file2.mp3",
                "path/to/file3.mp3");
    }

    @Test
public void testSortList() {
    // Create a new instance of AppUI
    AppUI appUI = new AppUI();

// Initialize the listModel field
    appUI.listModel = FXCollections.observableArrayList();
    appUI.sortBox = new ComboBox<>();

    // Add some items to the listModel
    appUI.listModel.addAll("apple", "banana", "cherry", "date", "elderberry");

    // Sort the listModel in ascending order
    appUI.sortBox.getSelectionModel().select("Ascending");
    appUI.sortList();

    // Check that the listModel is sorted correctly
    assertThat(appUI.listModel).containsExactly("apple", "banana", "cherry", "date", "elderberry");

    // Sort the listModel in descending order
    appUI.sortBox.getSelectionModel().select("Descending");
    appUI.sortList();

    // Check that the listModel is sorted correctly
    assertThat(appUI.listModel).containsExactly("elderberry", "date", "cherry", "banana", "apple");
}
}
