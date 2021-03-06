package de.bht.algo.jhh.sort;

import java.io.File;
import java.util.HashMap;

import de.bht.algo.bohnet.FileIntArray;

/**
 * Diese Klasse sortiert Zahlen nach dem Quicksort-Algorithmus
 * 
 * @author Hala, Hanna, Jan
 * 
 */
public class FileReader {
  // giTTest

  /**
   * die Hashmap in der alle Dateinamen mit ihren Zahlen gespeichert werden
   * Bitte über getIntsOfFile(String) darauf zugreifen
   */
  private static HashMap<String, int[]> fileMap = new HashMap<String, int[]>();

  /** Der Ordner in dem die Dateien mit den Zahlen gefunden werden können */
  private final static String fileFolder = "files";

  /**
   * Der Eintiegspunkt für das Programm. Von hier aus werden automatisch die
   * Dateien mit den Zahlen eingelesen. Sie werden in einer Hashmap gespeichert.
   * Der Schl�ssel ist der Dateiname und der Wert das passende int-Array.
   * 
   * @param args
   *          keine Parameter hier
   */
  public FileReader() {
    for (File f : new File(fileFolder).listFiles()) {
      String name = f.getName();
      int[] numbers = FileIntArray.FileToIntArray(fileFolder + "/" + name);
      fileMap.put(name, numbers);

    }

    // zum testen
    // int[] testInt = getIntsOfFile("Rand500_2");
    int[] testInt = getIntsOfFile("Rand10000_2");
    // int[] testInt = getIntsOfFile("Rand100000_2");

    // int[] testInt = getIntsOfFile("Sort500_1");
    // int[] testInt = getIntsOfFile("Sort10000_1");
    // int[] testInt = getIntsOfFile("Sort100000_1");
  }

  /**
   * Mit dieser Methode kann auf die int-Arrays der jeweiligen Datei zugegriffen
   * werden. Allerdings wird hier nicht wie in FileIntArray.FileToIntArray()
   * versucht die Datei zu lesen, sondern der Name der Datei wird in der zuvor
   * erstellten HashMap gesucht.
   * 
   * @param fileName
   * @return
   */
  public static int[] getIntsOfFile(String fileName) {
    int[] retValue = fileMap.get(fileName);
    // zum testen
    System.out.println("File " + fileName + " keeps " + retValue.length + " Numbers");
    System.out.println("Die ersten beiden Zahlen in " + fileName + " sind " + retValue[0] + " und " + retValue[1]);
    return retValue;
  }

  public HashMap<String, int[]> getFileMap() {
    return fileMap;
  }

}
