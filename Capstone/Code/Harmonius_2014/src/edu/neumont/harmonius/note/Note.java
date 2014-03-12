package edu.neumont.harmonius.note;

import java.io.File;
public class Note {

	private String noteName;
	private double frequency;
	private String soundFile;
	
	public String getNoteName() {
		return noteName;
	}
	public void setNoteName(String noteName) {
		this.noteName = noteName;
	}
	public double getFrequency() {
		return frequency;
	}
	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	
	public Note(String name, double freq, String soundFile){
		this.frequency = freq;
		this.noteName = name;
		this.soundFile = soundFile;
	}
	public String getSoundFile() {
		return soundFile;
	}
	public void setSoundFile(String soundFile) {
		this.soundFile = soundFile;
	}
}
