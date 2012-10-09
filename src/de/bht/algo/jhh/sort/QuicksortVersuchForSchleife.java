/**
 * 
 */
package de.bht.algo.jhh.sort;

/**
 * @author Hala, Hanna, Jan
 * 
 */
public class QuicksortVersuchForSchleife {
  int[] zahlen;

  public QuicksortVersuchForSchleife(int[] zahlen) {
    this.zahlen = zahlen;

    sortiere(zahlen, 0, zahlen.length - 1);
  }

  public static String zahlenArraysToString(int[] zahlen) {
    String s = "";
    for (int i = 0; i < zahlen.length; i++) {
      s += zahlen[i] + ", ";
    }
    return s;
  }

  private void sortiere(int[] zahlen, int l, int r) {

    int positionRechts = r;

    /*
     * Sortierung von der Mitte aus starten
     */
    // int pivot = zahlen.length / 2 ;
    int pivot = Math.round((r + 1 - l) / 2 + l);
    System.out.println();
    System.out.println("Mittelwert: " + zahlen[pivot]);
    if (l < r) {
      for (int i = l; i < zahlen.length; i++) {
        if (zahlen[i] >= zahlen[pivot]) {

          for (int j = positionRechts; j >= 0; j--) {

            if (zahlen[j] <= zahlen[pivot]) {
              positionRechts = j;
              int tmpJ = zahlen[j];
              zahlen[j] = zahlen[i];
              zahlen[i] = tmpJ;

              System.out.println("Zahlen " + "(Index " + l + "-" + r + "): " + zahlenArraysToString(zahlen));

              break;
            } else if (i == j) {
              int tmpJ = zahlen[j];
              zahlen[j] = zahlen[pivot];
              zahlen[pivot] = tmpJ;
              sortiere(zahlen, l, pivot - 1);
              sortiere(zahlen, pivot + 1, r);

              System.out.println("fertig 1: " + zahlenArraysToString(zahlen));

              continue;
            }
          }

        }
      }

    }
  }
}
