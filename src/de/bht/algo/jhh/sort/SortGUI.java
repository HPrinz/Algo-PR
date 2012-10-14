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
 * Diese Klasse erstellt eine GUI zum Sortieren.
 * 
 * - Es koennen alle Dateien eines Ordners sortiert werden 
 * - Eine Datei kann zum sortieren ueber einen FileChooser ausgewaehlt werden
 * - Der Dateiname und eine Zeitangabe in Nanosekunden kann in einer xls-Datei gespeichert werden
 * 
 * 
 * @author Hala, Hanna, Jan
 * 
 */
public class SortGUI extends JFrame {
  private final String sheetName = "Quicksort";
  private int counter = 0;
  private final JTextField textField;
  private final JFileChooser chooser;
  private final JTextArea textArea;
  private final JTextArea timeArea;
  private final JButton btnSortiere;
  private final Workbook wb;
  private final JCheckBox chckbxExportNachxls;
  private final JButton btnFertig;
  private final JButton btnSortiereAlle;
  private final JButton fileChooserButton;

  /**
   * Erstellt die GUI
   *  
   */
  public SortGUI() {
    wb = prepareExcelSheet(sheetName);
    this.setSize(600, 400);
    getContentPane().setLayout(new GridLayout(3, 2, 10, 10));

    chooser = new JFileChooser("files");

    JPanel panel = new JPanel();
    getContentPane().add(panel);
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    JPanel fileChooserPanel = new JPanel();
    panel.add(fileChooserPanel);
    fileChooserPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

    textField = new JTextField();
    textField.setColumns(40);
    fileChooserPanel.add(textField);

    fileChooserButton = new JButton("Datei...");
    fileChooserPanel.add(fileChooserButton);

    chckbxExportNachxls = new JCheckBox("Export nach .xls");
    fileChooserPanel.add(chckbxExportNachxls);

    JPanel buttonPanel = new JPanel();
    panel.add(buttonPanel);

    textArea = new JTextArea("");
    JScrollPane textScrollPane = new JScrollPane(textArea);
    getContentPane().add(textScrollPane);

    timeArea = new JTextArea("");
    JScrollPane timeScrollPane = new JScrollPane(timeArea);
    getContentPane().add(timeScrollPane);

    btnSortiere = new JButton("Sortiere");
    buttonPanel.add(btnSortiere);

    btnSortiereAlle = new JButton("Sortiere Alle");
    buttonPanel.add(btnSortiereAlle);

    btnFertig = new JButton("Fertig");
    buttonPanel.add(btnFertig);

    initActionListeners();
  }

  /**
   * initialisiert die Buttonlistener (zur besseren Uebersicht)
   */
  private void initActionListeners() {

    fileChooserButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        chooser.showOpenDialog(chooser.getParent());
        textField.setText(chooser.getSelectedFile().toString());
      }
    });

    btnSortiere.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        File file = new File(textField.getText());
        String fileName = file.getName();
        int[] zahlen = FileIntArray.FileToIntArray(file.getAbsolutePath());

        callQuicksort(fileName, zahlen);
      }
    });

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

          System.out.println("nach QS");

          it.remove(); // avoids a ConcurrentModificationException
        }
      }
    });

    btnFertig.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        finalizeExcel("Sort_Results.xls");
        end();
      }
    });
  }

  /**
   * bereitet die Datenstruktur fuer das Excel-Workbook vor. Wird pro
   * Programmaufruf ein mal Ausgefuehrt (am Anfang)
   * 
   * @param sheetName
   *          der Name des Blattes/Sheets
   * @return das erstelle Workbook
   */
  private Workbook prepareExcelSheet(String sheetName) {
    // neues Workbook und Sheet erstellen
    Workbook workB = new HSSFWorkbook();
    Sheet sheet = workB.createSheet(sheetName);
    // Titelzeile
    Row rowOne = sheet.createRow(counter);
    rowOne.createCell(0).setCellValue("LAUFZEITEN QUICKSORT");
    counter++;
    // Tabellenkopf
    Row rowTwo = sheet.createRow(counter);
    rowTwo.createCell(0).setCellValue("Sortierte Datei");
    rowTwo.createCell(1).setCellValue("Dauer in Nanosekunden");
    rowTwo.createCell(2).setCellValue("Anzahl Rekursionen");
    counter++;

    return workB;
  }

  /**
   * Fuegt eine neue Zeile in dem Excel-Sheet mithilfe eines counters ein. Kann
   * pro Programmaufruf beliebig oft ausgefuehrt werden.
   * 
   * @param fileName
   *          der Name der Datei, deren Werte sortiert wurden
   * @param timeToSort
   *          Dauer der Sortierung in Nanosekunden
   */
  private void exportRowToExcel(String fileName, String timeToSort, String timeRekursionen) {
    Sheet sheet = wb.getSheet(sheetName);

    Row row = sheet.createRow(counter);
    row.createCell(1).setCellValue(fileName);
    row.createCell(2).setCellValue(timeToSort);
    row.createCell(3).setCellValue(timeRekursionen);
    counter++;
  }

  /**
   * Diese Methode erstellt die Excel-Datei mit dem Workbook und dem Sheet, auf
   * das die Sortierergebnisse geschrieben wurden. Wird nur ein mal pro
   * Programmaufruf aufgerufen (und zwar am Ende)
   * 
   * @param fileName
   *          wie die Datei heissen soll, kann auch ein Pfad sein
   */
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
   * ruft quicksort fuer die angegeben Zahlen auf und gibt die Ergebnisse in der
   * Kommandozeile aus
   * 
   * @param fileName
   *          der Name der zu sortierenden Datei
   * @param zahlen
   *          die zu sortierenden Zahlen
   */
  private void callQuicksort(String fileName, int[] zahlen) {

    // Ausgabe der Informationen im Fenster
    textArea.append("DATEI: " + fileName + "\n");
    textArea.append("ANFANGSARRAY: " + Quicksort.zahlenArraysToString(zahlen) + "\n");
    timeArea.append("Timer startet. \n");

    // timer starten
    long startTime = System.nanoTime();

    // erster durchlauf, starte Quicksort
    Quicksort quicksort = new Quicksort(zahlen);

    // timer beenden
    long endTime = System.nanoTime();

    // Ausgabe der Informationen im Fenster
    timeArea.append("Timer beendet. \n");
    timeArea.append("DAUER: " + (endTime - startTime) + " Nanosekunden \n");
    timeArea.append("------------------------------------------- \n");
    textArea.append("ENDE: " + Quicksort.zahlenArraysToString(zahlen) + " \n");
    textArea.append("REKURSIONEN: " + Quicksort.getRekursionen()  + " \n");
    textArea.append("------------------------------------------- \n");

    if (chckbxExportNachxls.isSelected()) {
      exportRowToExcel(fileName, "" + (endTime - startTime) + "", "" + Quicksort.getRekursionen());
    }
  }

  /**
   * Schliesst das Fenster und beendet das Programm
   */
  private void end() {
    this.dispose();
  }

  /**
   * Oeffnet das JFrame
   * 
   * @param args
   *          keine
   */
  public static void main(String[] args) {
    new SortGUI().setVisible(true);
  }
}
