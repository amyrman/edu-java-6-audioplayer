package com.example;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.scene.media.MediaException;

public class AppUI implements ActionListener {
  private MP3Player mp3Player;
  private DefaultListModel<String> listModel;
  private JList<String> fileList;
  JScrollPane scrollPane = new JScrollPane(fileList);
  private JComboBox<String> sortBox;

  public AppUI() {
    mp3Player = new MP3Player();
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

    // Create the frame object
    JFrame frame = new JFrame("Java Exercise #6: Java Music Player");

    // Add a window listener to the frame to handle closing events
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

    // Create the label object
    JLabel label = new JLabel("Java Music Player");

    // Create the main content panel with BorderLayout
    JPanel contentPanel = new JPanel(new BorderLayout());

    // Create open button object
    JButton openButton = new JButton("Open");
    openButton.setActionCommand("Open Explorer");
    openButton.addActionListener(this);

    // Create play stop button object
    JButton playStopButton = new JButton("Play/Stop");
    playStopButton.setActionCommand("Play");
    playStopButton.addActionListener(this);

    JButton sortButton = new JButton("Sort");
    sortButton.setActionCommand("Sort");
    sortButton.addActionListener(this);

    sortBox = new JComboBox<>();
    sortBox.addItem("Ascending");
    sortBox.addItem("Descending");
    sortBox.addActionListener(this);

    // Create a panel for the buttons with FlowLayout
    JPanel buttonPanel = new JPanel(new FlowLayout());

    // Add the label and file list to the top and center of the content panel
    contentPanel.add(label, BorderLayout.NORTH);
    contentPanel.add(fileList, BorderLayout.CENTER);

    // Add the buttons to the button panel
    buttonPanel.add(openButton);
    buttonPanel.add(playStopButton);
    buttonPanel.add(sortBox);

    // Add the button panel to the bottom of the content panel
    contentPanel.add(buttonPanel, BorderLayout.SOUTH);

    // Add the content panel to the frame
    frame.add(contentPanel);

    // Set the size and visibility of the frame
    frame.setSize(600, 400);
    frame.setLocationRelativeTo(null); // center window
    frame.setVisible(true);
  }

  private void openFileExplorer() {
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

  private void playStopSelectedFile() {
    int selectedIndex = fileList.getSelectedIndex();
    if (selectedIndex != -1) {
      String filePath = listModel.getElementAt(selectedIndex);
      if (mp3Player != null && mp3Player.isPlaying()) {
        // Stop the mp3 player if it's already playing
        mp3Player.stop();
      } else {
        // Start playing the mp3 file
        try {
          mp3Player.play(filePath);
        } catch (MediaException ex) {
          ex.printStackTrace();
        }
      }
    }
  }

  private void sortList() {
    String selected = (String) sortBox.getSelectedItem();
    if (selected.equals("Ascending")) {
      List<String> list = new ArrayList<>();
      for (int i = 0; i < listModel.getSize(); i++) {
        list.add(listModel.getElementAt(i));
      }
      Collections.sort(list);
      listModel.clear();
      for (String s : list) {
        listModel.addElement(s);
      }
    } else if (selected.equals("Descending")) {
      List<String> list = new ArrayList<>();
      for (int i = 0; i < listModel.getSize(); i++) {
        list.add(listModel.getElementAt(i));
      }
      Collections.sort(list, new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
          return o2.compareTo(o1);
        }
      });
      listModel.clear();
      for (String s : list) {
        listModel.addElement(s);
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String actionCommand = e.getActionCommand();
    switch (actionCommand) {
      case "Open Explorer":
        openFileExplorer();
        break;
      case "Play":
        playStopSelectedFile();
        break;
      default:
        // Handle the sortBox selection
        if (e.getSource() == sortBox) {
          sortList();
        }
        break;
      // Add cases for other actions as needed
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
      if (file.isFile()) {
        try {
          String filePath = file.getAbsolutePath();
          listModel.addElement(filePath);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }
  }
}

// // // Un-implemented buttons
// // Create stop button object
// JButton stopButton = new JButton("Stop");
// stopButton.addActionListener(this);

// // Create prev button object
// JButton prevButton = new JButton("Prev");
// prevButton.addActionListener(this);

// // Create next button object
// JButton nextButton = new JButton("Next");
// nextButton.addActionListener(this);

// buttonPanel.add(stopButton);
// buttonPanel.add(prevButton);
// buttonPanel.add(nextButton);
