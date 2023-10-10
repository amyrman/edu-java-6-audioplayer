package com.example;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javafx.scene.media.MediaException;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.File;

public class AppUI implements ActionListener {
  private DefaultListModel<String> listModel;
  private JList<String> fileList;
  JScrollPane scrollPane = new JScrollPane(fileList);

  public AppUI() {
    // Set the look and feel of the UI to system
    try {
      UIManager.setLookAndFeel(
          UIManager.getSystemLookAndFeelClassName());
    } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
        | IllegalAccessException e) {
      e.printStackTrace();
    }
    // Create the list model and list objects
    listModel = new DefaultListModel<>();
    fileList = new JList<>(listModel);

    // Create the label object
    JLabel label = new JLabel("Java Music Player");

    // Create the frame object
    JFrame frame = new JFrame("Java Exercise #6: Java Music Player");
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    // Add a window listener to the frame to handle closing events
    frame.addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        int confirmed = JOptionPane.showConfirmDialog(null,
            "Are you sure you want to exit the application?", "Exit Application Confirmation",
            JOptionPane.YES_NO_OPTION);

        if (confirmed == JOptionPane.YES_OPTION) {
          System.exit(0);
        }
      }
    });

    // Create the open button object
    JButton openButton = new JButton("Open");
    openButton.addActionListener(this);

    // Add the components to the frame
    frame.add(label, BorderLayout.NORTH);
    frame.add(fileList, BorderLayout.CENTER);
    frame.add(openButton, BorderLayout.SOUTH);

    // Set the size and visibility of the frame
    frame.setSize(400, 400);
    frame.setLocationRelativeTo(null); // center window
    frame.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JFileChooser fileChooser = new JFileChooser();
    setFileChooserDirectory(fileChooser);
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "MP3 Files", "mp3");
    fileChooser.setFileFilter(filter);
    fileChooser.setMultiSelectionEnabled(true);

    int returnValue = fileChooser.showOpenDialog(null);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File[] selectedFiles = fileChooser.getSelectedFiles();
      addFilesToList(selectedFiles);
    }
  }

  private void setFileChooserDirectory(JFileChooser fileChooser) {
    File downloadsDir = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Downloads");
    if (downloadsDir.exists() && downloadsDir.isDirectory()) {
      fileChooser.setCurrentDirectory(downloadsDir);
    } else {
      fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    }
  }

  private void addFilesToList(File[] files) {
    for (File file : files) {
      try {
        String filePath = file.getAbsolutePath();
        listModel.addElement(filePath);
        MP3Player mp3Player = new MP3Player();
        mp3Player.play(filePath);
      } catch (MediaException ex) {
        ex.printStackTrace();
      }
    }
  }
}
