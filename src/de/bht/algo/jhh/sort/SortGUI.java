package de.bht.algo.jhh.sort;

import javax.swing.JFrame;
import java.awt.GridLayout;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BoxLayout;

import de.bht.algo.bohnet.FileIntArray;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * 
 * @author Hanna
 * 
 */
public class SortGUI extends JFrame {
  private final JTextField textField;
  JFileChooser chooser;
  JTextArea textArea;
  private final JTextArea timeArea;
  private final JButton btnSortiere;

  /**
   * 
   */
  public SortGUI() {
    this.setSize(600, 400);
    getContentPane().setLayout(new GridLayout(3, 2, 10, 10));

    JPanel panel = new JPanel();
    getContentPane().add(panel);

    chooser = new JFileChooser("files");

    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    JPanel fileChooserPanel = new JPanel();
    panel.add(fileChooserPanel);
    fileChooserPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

    textField = new JTextField();
    fileChooserPanel.add(textField);
    textField.setColumns(40);

    JButton fileChooserButton = new JButton("Datei...");
    fileChooserButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        chooser.showOpenDialog(chooser.getParent());
        textField.setText(chooser.getSelectedFile().toString());
      }
    });

    fileChooserPanel.add(fileChooserButton);

    JPanel panel_2 = new JPanel();
    panel.add(panel_2);

    textArea = new JTextArea("");
    JScrollPane textScrollPane = new JScrollPane(textArea);
    getContentPane().add(textScrollPane);

    timeArea = new JTextArea("");
    JScrollPane timeScrollPane = new JScrollPane(timeArea);
    getContentPane().add(timeScrollPane);

    btnSortiere = new JButton("Sortiere");
    btnSortiere.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        File file = new File(textField.getText());
        String fileName = file.getName();
        int[] zahlen = FileIntArray.FileToIntArray(file.getAbsolutePath());
        Quicksort quicksort = new Quicksort(zahlen);

        // erster durchlauf
        textArea.append("Datei: " + fileName + "\n");
        textArea.append("ANFANGSARRAY: " + Quicksort.zahlenArraysToString(zahlen) + "\n");
        // timer starten
        timeArea.append("Timer startet. \n");
        long startTime = System.nanoTime();
        quicksort.sortiere(zahlen, 0, zahlen.length - 1);

        // timer beenden
        long endTime = System.nanoTime();
        timeArea.append("Timer beendet. \n");
        timeArea.append("Dauer " + (endTime - startTime) + " Nanosekunden \n");
        timeArea.append("------------------------------------------- \n");
        textArea.append("ENDE: " + Quicksort.zahlenArraysToString(zahlen) + " \n");
        textArea.append("------------------------------------------- \n");
      }
    });

    panel_2.add(btnSortiere);
  }

  /**
   * 
   * @param args
   */
  public static void main(String[] args) {
    new SortGUI().setVisible(true);
  }
}
