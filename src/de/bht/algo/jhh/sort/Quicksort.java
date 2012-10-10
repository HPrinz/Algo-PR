package de.bht.algo.jhh.sort;

/**
 * 
 * @author Hala, Hanna, Jan
 * 
 */
public class Quicksort {
  int[] zahlen;

  /**
   * 
   * @param zahlen
   *          das zu sortierende Array
   */
  public Quicksort(int[] zahlen) {
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

  /**
   * 
   * @param zahlen
   *          ein integerArray
   * @param l
   *          die Anfangsposition links ab wohin noch zu sortieren ist
   * @param r
   *          die Endposition rechts bis wohin noch zu sortieren ist
   */
  public void sortiere(int[] zahlen, int l, int r) {

    int positionLinks = l;
    int positionRechts = r;

    /*
     * Sortierung von der Mitte aus starten, Pivotzahl finden
     */
    int pivot = zahlen[(l + r) / 2];

    /*
     * solange sie sich noch nicht getroffen haben
     */
    do {
      /* @formatter:off
       * suche von links ein Element, dass größer ist als das Pivot
       * 1  5  6 |3| 7  8  0
       * i->
       */
      while ( zahlen[ positionLinks ] < pivot ) {
        positionLinks++;
      }
      
      /* @formatter:off
       * suche von rechts ein Element, dass größer ist als das Pivot
       * 1  5  6 |3| 7  8  0
                         <-j
       */
      while ( zahlen[ positionRechts ] > pivot ) {
        positionRechts--;
      }
      
      // Zahlen links und rechts vertauschen
      if ( positionLinks <= positionRechts ) {
        int falscherWertL = zahlen[ positionLinks ];
        zahlen[ positionLinks ] = zahlen[ positionRechts ];
        zahlen[ positionRechts ] = falscherWertL;
        positionLinks++;
        positionRechts--;
      }
    } while ( positionLinks <= positionRechts );

    /*
     *  Rekursion
     */
    if ( l < positionRechts ) {
      sortiere( zahlen, l, positionRechts );
    }
    if ( positionLinks < r ) {
      sortiere( zahlen, positionLinks, r );
    }
  }
}
