package edu.neumont.harmonius.note;

import java.util.ArrayList;
import java.util.Iterator;

public class WesternScale {
	
	
   /*  VOCAL RANGES
    * 
	* 	Soprano: C4 – C6
	*	Mezzo-soprano: A3 – A5
	*	Contralto: F3 – F5
	*	Tenor: C3 – C5
	*	Baritone: F2 – F4 (G#2 to G#4 in operatic music)
	*	Bass: E2 – E4
	*/
	
	/* Instrument range - standard guitar
	 *    E2 to E5
	 */
	
	
	
	//Note A1 = new Note("A1", 55.0F);
	//Note A1Sharp = new Note("A1Sharp", 58.27F);
	//Note B1 = new Note("B1", 61.74F);
	
	//Note C2 = new Note("C2", 65.41F);
	//Note C2Sharp = new Note("C2Sharp", 69.30F);
	//Note D2 = new Note("D2", 73.42F);
	//Note D2Sharp = new Note("D2Sharp", 77.78F);
	Note E2 = new Note("E2", 82.41F, "e2.wav");
	Note F2 = new Note("F2", 87.31F, "f2.wav");
	Note F2Sharp = new Note("F2Sharp", 92.50F,  "f#2.wav");
	Note G2 = new Note("G2", 98.00F, "g2.wav");
	Note G2Sharp = new Note("G2Sharp", 103.83F,"g#2.wav");	
	Note A2 = new Note("A2", 110.0F,"a2.wav");
	Note A2Sharp = new Note("A2Sharp", 116.54F, "a#2.wav");
	Note B2 = new Note("B2", 123.47F,"b2.wav");
	
	Note C3 = new Note("C3", 130.81F, "c3.wav");
	Note C3Sharp = new Note("C3Sharp", 138.59F, "c#3.wav");
	Note D3 = new Note("D3", 146.83F, "d3.wav");
	Note D3Sharp = new Note("D3Sharp", 155.56F, "d#3.wav");
	Note E3 = new Note("E3", 164.81F, "e3.wav");
	Note F3 = new Note("F3", 174.61F, "f3.wav");
	Note F3Sharp = new Note("F3Sharp", 185.0F, "f#3.wav");
	Note G3 = new Note("G3", 196.00F, "g3.wav");
	Note G3Sharp = new Note("G3Sharp", 207.65F, "g#3.wav");
	Note A3 = new Note("A3", 220.0F, "a3.wav");
	Note A3Sharp = new Note("A3Sharp", 233.08F, "a#3.wav");
	Note B3 = new Note("B3", 246.94F, "b3.wav");
	
	Note C4 = new Note("C4", 261.63F, "c4.wav");
	Note C4Sharp = new Note("C4Sharp", 277.18F, "c#4.wav");
	Note D4 = new Note("D4", 293.66F, "d4.wav");
	Note D4Sharp = new Note("D4Sharp", 311.13F, "d#4.wav");
	Note E4 = new Note("E4", 329.63F, "e4.wav");
	Note F4 = new Note("F4", 349.23F, "f4.wav");
	Note F4Sharp = new Note("F4Sharp", 369.99F, "f#4.wav");
	Note G4 = new Note("G4", 392.00F, "g4.wav");
	Note G4Sharp = new Note("G4Sharp", 415.30F, "g#4.wav");
	Note A4 = new Note("A4", 440.00F, "a4.wav");
	Note A4Sharp = new Note("A4Sharp", 466.16F, "a#4.wav");
	Note B4 = new Note("B4", 493.88F, "b4.wav");

	Note C5 = new Note("C5", 523.25F, "c5.wav");
	Note C5Sharp = new Note("C5Sharp", 554.37F, "c#5.wav");
	Note D5 = new Note("D5", 587.33F, "d5.wav");
	Note D5Sharp = new Note("D5Sharp", 622.25F, "d#5.wav");
	Note E5 = new Note("E5", 659.26F, "e5.wav");
	Note F5 = new Note("F5", 698.46F, "f5.wav");
	Note F5Sharp = new Note("F5Sharp", 739.99F, "f#5.wav");
	Note G5 = new Note("G5", 783.99F, "g5.wav");
	Note G5Sharp = new Note("G5Sharp", 830.61F, "g#5.wav");
	Note A5 = new Note("A5", 880.00F, "a5.wav");
	Note A5Sharp = new Note("A5Sharp", 932.33F, "a#5.wav");
	Note B5 = new Note("B5", 987.77F, "b5.wav");
	
	Note C6 = new Note("C6", 1046.50F, "c6.wav");
	
	
	ArrayList<Note> notes = new ArrayList<Note>();
	
	public WesternScale(){
		

		//notes.add(A1);
		//notes.add(A1Sharp);
		//notes.add(B1);
		
		//notes.add(C2);
		//notes.add(C2Sharp);
		//notes.add(D2);
		//notes.add(D2Sharp);
		notes.add(E2);
		notes.add(F2);
		notes.add(F2Sharp);
		notes.add(G2);
		notes.add(G2Sharp);	
		notes.add(A2);
		notes.add(A2Sharp);
		notes.add(B2);
		
		notes.add(C3);
		notes.add(C3Sharp);
		notes.add(D3);
		notes.add(D3Sharp);
		notes.add(E3);
		notes.add(F3);
		notes.add(F3Sharp);
		notes.add(G3);
		notes.add(G3Sharp);
		notes.add(A3);
		notes.add(A3Sharp);
		notes.add(B3);
		
		notes.add(C4);
		notes.add(C4Sharp);
		notes.add(D4);
		notes.add(D4Sharp);
		notes.add(E4);
		notes.add(F4);
		notes.add(F4Sharp);
		notes.add(G4);
		notes.add(G4Sharp);
		notes.add(A4);
		notes.add(A4Sharp );
		notes.add(B4);

		notes.add(C5);
		notes.add(C5Sharp);
		notes.add(D5);
		notes.add(D5Sharp);
		notes.add(E5);
		notes.add(F5);
		notes.add(F5Sharp);
		notes.add(G5);
		notes.add(G5Sharp);
		notes.add(A5);
		notes.add(A5Sharp);
		notes.add(B5);
		
		notes.add(C6);
		
		
	}

	public ArrayList<Note> getNotes() {
		return notes;
	}

	
	public Iterator<Note> getIterator(){
		Iterator<Note> it = notes.iterator();
		return it;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
