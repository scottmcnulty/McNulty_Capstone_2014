package edu.neumont.harmonius.model;

import java.util.ArrayList;

public class MusicalPhrase {

	/*
	 * A musical phrase has a number of notes in it, each with its own detail.
	 * 
	 *
	 */
	
	private int phraseLength;
	private ArrayList<MusicalWord> phrase = new ArrayList<MusicalWord>();
	
	public MusicalPhrase(){
		
	}
	
	public void addMusicalWord(MusicalWord word){
		phrase.add(word);
	}
	
	public int getPhraseLength(){
		return phrase.size();
	}
	
	public ArrayList<MusicalWord> getPhrase(){
		return phrase;
	}
	
	
	
}
