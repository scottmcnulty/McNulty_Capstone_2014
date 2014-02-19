package edu.neumont.harmonius.note;

public class Note {

	private String noteName;
	private double frequency;
	private double noteDuration;
	
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
	
	public Note(String name, double freq){
		this.frequency = freq;
		this.noteName = name;
		this.noteDuration = 0.0F;
	}
	public double getNoteDuration() {
		return noteDuration;
	}
	public void setNoteDuration(double noteDuration) {
		this.noteDuration = noteDuration;
	}
}
