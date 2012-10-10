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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import de.bht.algo.bohnet.FileIntArray;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JCheckBox;

/**
 * 
 * @author Hanna
 * 
 */
public class SortGUI extends JFrame {
  private final String sheetName = "Quicksort";
  private int counter = 0;
  private final JTextField textField;
  JFileChooser chooser;
  JTextArea textArea;
  private final JTextArea timeArea;
  private final JButton btnSortiere;
  private Workbook wb;

  /**
   * 
   */
  public SortGUI() {
    wb = prepareExcelSheet(sheetName);
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

    final JCheckBox chckbxExportNachxls = new JCheckBox("Export nach .xls");
    fileChooserPanel.add(chckbxExportNachxls);

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

        if (chckbxExportNachxls.isSelected()) {
          exportRowToExcel(fileName, "" + (endTime - startTime) + "");
        }
      }
    });

    panel_2.add(btnSortiere);

    JButton btnSortiereAlle = new JButton("Sortiere Alle");
    btnSortiereAlle.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        FileReader fileReader = new FileReader();
        HashMap<String, int[]> hM = fileReader.getFileMap();

        Iterator it = hM.entrySet().iterator();
        while (it.hasNext()) {
          Map.Entry pairs = (Map.Entry) it.next();
          String fileName = (String) pairs.getKey();
          int[] zahlen = (int[]) pairs.getValue();
          System.out.println("Key: " + fileName + " Value: " + Quicksort.zahlenArraysToString(zahlen));

          Quicksort quicksort = new Quicksort(zahlen);
          System.out.println("nach QS");

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

          it.remove(); // avoids a ConcurrentModificationException

          if (chckbxExportNachxls.isSelected()) {
            exportRowToExcel(fileName, "" + (endTime - startTime) + "");
          }
        }

      }
    });
    panel_2.add(btnSortiereAlle);

    JButton btnFertig = new JButton("Fertig");
    btnFertig.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        finalizeExcel("Sort_Results.xls");
      }
    });

    panel_2.add(btnFertig);
  }

  private Workbook prepareExcelSheet(String sheetName) {
    counter++;
    // Create new workbook and tab
    wb = new HSSFWorkbook();
    Sheet sheet = wb.createSheet(sheetName);
    // Create 2D Cell Array
    Row row = sheet.createRow(1);
    Cell cellOne = row.createCell(1);
    cellOne.setCellValue("Dateinamen");
    Cell cellTwo = row.createCell(2);
    cellTwo.setCellValue("Dauer");
    return wb;
  }

  private void exportRowToExcel(String fileName, String timeToSort) {
    counter++;
    Sheet sheet = wb.getSheet(sheetName);
    Row row = sheet.createRow(counter);
    Cell filenameCell = row.createCell(1);
    filenameCell.setCellValue(fileName);
    Cell timeCell = row.createCell(2);
    timeCell.setCellValue(timeToSort);
  }

  private void finalizeExcel(String fileName) {
    try {
      FileOutputStream fileOut = new FileOutputStream(fileName);
      wb.write(fileOut);
      fileOut.close();
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Exception not handled in code", e);
    } catch (IOException e) {
      throw new RuntimeException("Exception not handled in code", e);
    }
  }

  /**
   * 
   * @param args
   */
  public static void main(String[] args) {
    new SortGUI().setVisible(true);
  }
}
