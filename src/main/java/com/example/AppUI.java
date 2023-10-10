package com.example;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javafx.scene.media.MediaException;

import java.awt.event.*;
import java.io.File;

public class AppUI implements ActionListener {
  private DefaultListModel<String> listModel;
  private JList<String> fileList;

  public AppUI() {
    try {
      UIManager.setLookAndFeel(
          UIManager.getSystemLookAndFeelClassName());
    } catch (UnsupportedLookAndFeelException e) {
    } catch (ClassNotFoundException e) {
    } catch (InstantiationException e) {
    } catch (IllegalAccessException e) {
    }

    listModel = new DefaultListModel<>();
    fileList = new JList<>(listModel);

    JLabel label = new JLabel("Java Music Player");

    JFrame frame = new JFrame("Java Exercise #6: Java Music Player");

    frame.setSize(300, 150);
    // cemter window
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    // Add JOptionPane to close
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

    JButton openButton = new JButton("Open");

    openButton.setBounds(10, 10, 80, 25);
    openButton.addActionListener(this);

    frame.add(openButton);
    frame.add(label);
    frame.setVisible(true);

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JFileChooser fileChooser = new JFileChooser();
    File downloadsDir = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Downloads");
    if (downloadsDir.exists() && downloadsDir.isDirectory()) {
      fileChooser.setCurrentDirectory(downloadsDir);
    } else {
      fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    }
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "MP3 Files", "mp3");
    fileChooser.setFileFilter(filter);

    int returnValue = fileChooser.showOpenDialog(null);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      try {
        String filePath = selectedFile.getAbsolutePath();
        MP3Player mp3Player = new MP3Player();
        mp3Player.play(filePath);
      } catch (MediaException ex) {
        ex.printStackTrace();
      }
    }
  }
}
