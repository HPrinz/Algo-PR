/**
 * 
 */
package de.bht.algo.jhh.sort;

/**
 * 
 * @author Hala, Hanna, Jan
 * 
 */

public class QuicksortJAN {
	int[] zahlen;

	/**
	 * 
	 * @param zahlen
	 *            das zu sortierende Array
	 */
	public QuicksortJAN( int[] zahlen ) {
		this.zahlen = zahlen;
		// erster durchlauf
		System.out.println( "ANFANG: " + zahlenArraysToString( zahlen ) );
		sortiere( zahlen, 0, zahlen.length - 1 );
		System.out.println( "ENDE: " + zahlenArraysToString( zahlen ) );
	}

	public static String zahlenArraysToString( int[] zahlen ) {
		String s = "";
		for ( int i = 0; i < zahlen.length; i++ ) {
			s += zahlen[ i ] + ", ";
		}
		return s;
	}

	/**
	 * 
	 * @param zahlen
	 *            ein integerArray
	 * @param l
	 *            die Anfangsposition links ab wohin noch zu sortieren ist
	 * @param r
	 *            die Endposition rechts bis wohin noch zu sortieren ist
	 */
	private void sortiere( int[] zahlen, int l, int r ) {
		int positionLinks = l;
		int positionRechts = r;

		/*
		 * Sortierung von der Mitte aus starten, Pivotzahl finden
		 */
		int pivot = zahlen[ ( l + r ) / 2 ];

		/*
		 *  solange sie sich noch nicht getroffen haben
		 */
		do {
			// bis Zahl grš§er als Pivotzahl
			while ( zahlen[ positionLinks ] < pivot ) {
				positionLinks++;
			}
			
			// bis Zahl kleiner als Pivotzahl
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
		if ( l < positionRechts )
			sortiere( zahlen, l, positionRechts );
		if ( positionLinks < r )
			sortiere( zahlen, positionLinks, r );
	}
}
