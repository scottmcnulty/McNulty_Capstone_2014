package edu.neumont.harmonius.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.neumont.harmonius.model.Model;
import edu.neumont.harmonius.note.Note;
import edu.neumont.harmonius.note.WesternScale;
import edu.neumont.harmonius.vendor.AudioFloatInputStream;
import edu.neumont.harmonius.vendor.Yin;
import edu.neumont.harmonius.vendor.Yin.DetectedPitchHandler;
import edu.neumont.harmonius.view.ApplicationView;
import edu.neumont.harmonius.view.HandyJPanel;
import edu.neumont.harmonius.view.JPlotPanel;


public class AnalyzerController {
	
	//constants for sampling, etc.
	final double AUDIO_BUFFER_SIZE_IN_SECONDS = 0.1;
	final int SAMPLE_RATE_IN_HZ = 22500;
	final int PITCH_SAMPLES_PER_MINUTE = 2400;
	final int MAX_PHRASE_LENGTH_IN_MINUTES = 5;  
	final float HALF_STEP_CONSTANT = 1.05946F;
	
	// volatile because it runs on its own thread - volatile guarantees the most updated value
	volatile AudioInputProcessor aiprocessor;
	
	// for processing files of notes
	private String fileName = null;
	
	//can replace with other tonal scales with their notes and pitch values
	private WesternScale ws = new WesternScale();
	
	public ArrayList<Note> notes = ws.getNotes();
	private Random gen = new Random();
	private Note currentNote = notes.get(0);
	private int currentInterval = 0;
	private String[] intervals = {"Unison", "Minor Second", "Major Second", "Minor Third", "Major Third", "Perfect Fourth", "Diminished Fifth", "Perfect Fifth", "Minor Sixth", "Major Sixth", "Minor Seventh", "Major Seventh", "Perfect Octave"};
    private ArrayList<Float> pitches = new ArrayList<Float>();
    double averagePitch = 0.0;
	Mixer.Info microphone;
	
	class AudioInputProcessor implements Runnable {

		private int sampleRate;
		private double audioBufferSize;

		public AudioInputProcessor() {
			sampleRate = SAMPLE_RATE_IN_HZ; 
			audioBufferSize = AUDIO_BUFFER_SIZE_IN_SECONDS;
		}

		public void run() {
			Mixer.Info selected = (Mixer.Info) microphone;
			if (selected == null)
				return;
			try{
				Mixer mixer = AudioSystem.getMixer(selected);
				AudioFormat format = new AudioFormat(sampleRate, 16, 1, true,false);
				DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, format);
				TargetDataLine line = (TargetDataLine) mixer.getLine(dataLineInfo);
				int numberOfSamples = (int) (audioBufferSize * sampleRate);
				line.open(format, numberOfSamples);
				line.start();
				AudioInputStream ais = new AudioInputStream(line);
				if (fileName != null) {
					ais = AudioSystem.getAudioInputStream(new File(fileName));
				}
				AudioFloatInputStream afis = AudioFloatInputStream.getInputStream(ais);
				processAudio(afis);
				line.close();
				//System.out.println("Line closed");
				// destroy current audioprocessor
				aiprocessor = null;
				//System.out.println("aiprocessor destroyed");
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void processAudio(AudioFloatInputStream afis)
				throws IOException, UnsupportedAudioFileException {

			Yin.processStream(afis, new DetectedPitchHandler() {
				@Override
				public void handleDetectedPitch(float time, float pitch) {
					boolean noteDetected = pitch != -1;
					if(noteDetected){
						pitches.add(pitch);
						//System.out.println(pitch);
					}
				}
			});

			double pitchAccum  = 0;
			int pitchCount = 0;
		
			for(Float f: pitches){
				if(f.floatValue() < 0){
					//no signal
					
				}	
				else if(f.floatValue() > 0){
					pitchAccum += f.floatValue();
					pitchCount++;
				}
				else //beginning of blank space
				{
					break; //found the end
				}
				
			}
			
			averagePitch = pitchAccum / pitchCount;
			System.out.print("\n Average pitch = " + averagePitch);
			plot();
			pitches.clear();
		}
	}

	private String getNote(float pitch) {
		String note = "";
		for (Note n : notes) {
			if (pitch <= n.getFrequency() + 1.0
					&& pitch > n.getFrequency() - 1.0) {
				note = n.getNoteName() + " ON PITCH";
			}
		}
		return note;
	}

	public void startAction() {
		if (aiprocessor != null) {
			return;
		} else {
			aiprocessor = new AudioInputProcessor();
			(new Thread(aiprocessor)).start();
			return;
		}
	}

	public void stopAction() {
		if (aiprocessor == null) {
			return;
		} else {
			Yin.stop();
			return;
		}
	}
	
	public void warmUpResultAction(){

		System.out.println("method call " + averagePitch );
		double returnable = averagePitch;

		double score = 0;
		double currFreq = 0;
		for(Note n: notes){

			// go over the frequency
			if(n.getFrequency() > returnable){
				System.out.println("got to chooser" + n.getFrequency() + " " + returnable);
				//find out if its closer to freq above or below
				if((returnable - currFreq) > (n.getFrequency() - returnable)){
					System.out.println("got to upper"  + returnable + " " + n.getFrequency());
					score = n.getFrequency() - returnable;
					view.setjp2ResultsJTextArea("You were " + score + "Hz high of your intended pitch, a " + n.getNoteName());
					break;
				}
				else{
					System.out.println("got to lower" + returnable + " " + currFreq);
					score = returnable - currFreq;
					view.setjp2ResultsJTextArea("You were " + score + "Hz low of your intended pitch, a " + n.getNoteName());
					break;
				}
			}
			currFreq = n.getFrequency();
		}
		averagePitch = 0;

	}
	
	
	public void plot( ) {	
				
		JPlotPanel j = new JPlotPanel(pitches,currentNote.getFrequency());
		j.setSize(600, 400);
		j.setVisible(true);
		JOptionPane.showConfirmDialog(null, j,"Results: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		
	}

	public void intervalPlayNoteAction() {
		String range = view.getJjp3JComboBoxSelected();
		String soundName = "";
		int randomNote = 0;
		
		switch(range){

		case "Soprano":
			randomNote  = gen.nextInt(24)+20;
			intervalAction(randomNote);
			break;
		case "Alto":
			randomNote  = gen.nextInt(24)+13;
			intervalAction(randomNote);
		break;
		case "Tenor":
			randomNote  = gen.nextInt(24)+8;
			intervalAction(randomNote);
			break;
		case "Bass":
			randomNote  = gen.nextInt(24);
			intervalAction(randomNote);
		break;
		}
	}

	private void intervalAction(int randomNote) {
		String soundName;
		soundName = notes.get(randomNote).getSoundFile();    
		currentNote = notes.get(randomNote);
		System.out.println("CurrentNote is: " + currentNote.getNoteName());
		play(soundName);
		currentInterval = gen.nextInt(13);
		view.setjp3IntervalJLabel(intervals[currentInterval]);
	}
	
	
	
	public void intervalTrainingStopAction(){
		
		double correctPitch = currentNote.getFrequency() * (Math.pow(1.05946, currentInterval));
		System.out.println("CurrentNote: " + currentNote.getFrequency() + " Correct Pitch to sing: " + correctPitch);
		double score = Math.abs(correctPitch - averagePitch);
		if(averagePitch > correctPitch){
			view.setjp3ResultsJTextArea("You were " + score + "Hz high of your intended pitch.");
		}
		else{
			view.setjp3ResultsJTextArea("You were " + score + "Hz low of your intended pitch.");
		}
		averagePitch = 0;
		
	}


	public String[] getNoteNameList() {
		
		String[] noteList = new String[notes.size()];
		int counter = 0;
		for(Note n: notes){
			noteList[counter] = n.getNoteName();
			counter++;
		}
		return noteList;
	}

	public void noteIdPlayNote() {
		int randomNote  = gen.nextInt(24)+8;
		String soundName = notes.get(randomNote).getSoundFile();    
		String noteName = notes.get(randomNote).getNoteName();
		try{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		//set the current note for comparison
		currentNote = notes.get(randomNote);
	}

	public void compareNoteId(String selected) {
		Note userChoice = null;
		
		if(selected == currentNote.getNoteName()){
			view.setjp4ResultsJTextArea("That's correct!");
		}
		else{
			
			for(Note n : notes){
				if (n.getNoteName().equals(selected)) {
					userChoice = n;
				}
			}
			
			if(userChoice.getFrequency() > currentNote.getFrequency()){
				view.setjp4ResultsJTextArea("That's incorrect, it is lower than your choice.");
			}
			else{
				view.setjp4ResultsJTextArea("That's incorrect, it is higher than your choice.");
			}
			
		}
	}

	public void setPPJLabelToCurrentNote() {
		
		String range = view.getJp5RangeJComboBoxSelected();
		System.out.println(range);
		int randomNote = 0;
		switch(range){
		case "Soprano":
			randomNote  = gen.nextInt(24)+20;
			break;
		case "Alto":
			randomNote  = gen.nextInt(24)+13;
		break;
		case "Tenor":
			randomNote  = gen.nextInt(24)+8;
			break;
		case "Bass":
			randomNote  = gen.nextInt(24);
		break;
		}
		currentNote = notes.get(randomNote);
		view.setjp5NoteDisplayJLabel(currentNote.getNoteName());
	}

	
	public void PPstopAction() {
		
		double returnable = averagePitch;

		double score = 0;
		for(Note n: notes){
			// go over the frequency
			if(currentNote.getFrequency() < returnable){
					score = returnable - currentNote.getFrequency();
					view.setjp5ResultsJTextArea("You were " + score + "Hz high of your intended pitch.");
					break;
				}
				else{
					score = currentNote.getFrequency() - returnable;
					view.setjp5ResultsJTextArea("You were " + score + "Hz low of your intended pitch.");
					break;
				}
		}
		
		System.out.println("SCORE " + score );
		//view.setjp5ResultsJTextArea("You were " + score + "Hz off of your intended pitch.");
		averagePitch = .0001;
	}
	
	public ApplicationView view; 
	
	public void getView(ApplicationView v){
		view = v;
	}
	
	public AnalyzerController(Model model) {

	}

	public void setFilename(String string) {
		fileName = string;
	}
	
	public void nullifyAIProcessor(){
		aiprocessor = null;
	}
	
	public void setInputDevice(Mixer.Info input) {
		this.microphone = input;
	}
	
	private void play(String file){
		try{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(file).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
