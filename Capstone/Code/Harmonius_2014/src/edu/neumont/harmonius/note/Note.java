package edu.neumont.harmonius.note;

import java.io.File;

public class Note {

	private String noteName;
	private double frequency;
	private double noteDuration;
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
		this.noteDuration = 0.0F;
		this.soundFile = soundFile;
	}
	public double getNoteDuration() {
		return noteDuration;
	}
	public void setNoteDuration(double noteDuration) {
		this.noteDuration = noteDuration;
	}
	public String getSoundFile() {
		return soundFile;
	}
	public void setSoundFile(String soundFile) {
		this.soundFile = soundFile;
	}
}
