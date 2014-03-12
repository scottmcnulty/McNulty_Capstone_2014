package edu.neumont.harmonius.controller;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Mixer.Info;
import edu.neumont.harmonius.model.Model;
import edu.neumont.harmonius.note.Note;

public class AccuracyTestDriver {

	//ApplicationView view;
	Model model;
	AnalyzerController analyzer;
	ArrayList<Info> soundSources = new ArrayList<Info>();
	
	public AccuracyTestDriver(){
	
	    model = new Model();
	    analyzer = new AnalyzerController(model);
	    
	    
	    Info mixers[] = AudioSystem.getMixerInfo();
	    analyzer.setInputDevice(mixers[3]);  //input device doesn't matter when running file
	    
	    
	    
	    //only works with mono 22,500 Hz samples
	    runTest("./audiocheck/audiocheck.net_sin_110Hz_-3dBFS_3s.wav");
	    sleep(2000);
	    runTest("./audiocheck/audiocheck.net_sin_220Hz_-3dBFS_3s.wav");
	    sleep(2000);
	    runTest("./audiocheck/audiocheck.net_sin_440Hz_-3dBFS_3s.wav");
	    sleep(2000);
	    runTest("./audiocheck/audiocheck.net_sin_880Hz_-3dBFS_3s.wav");
	    sleep(2000);
	    runTest("./audiocheck/audiocheck.net_sin_100Hz_-3dBFS_3s.wav");
	    sleep(2000);
	    runTest("./audiocheck/audiocheck.net_sin_200Hz_-3dBFS_3s.wav");
	    sleep(2000);
	    runTest("./audiocheck/audiocheck.net_sin_300Hz_-3dBFS_3s.wav");
	    sleep(2000);
	    runTest("./audiocheck/audiocheck.net_sin_400Hz_-3dBFS_3s.wav");
	    sleep(2000);
	    runTest("./audiocheck/audiocheck.net_sin_500Hz_-3dBFS_3s.wav");
	    sleep(2000);
	    runTest("./audiocheck/audiocheck.net_sin_600Hz_-3dBFS_3s.wav");
	    sleep(2000);
	    runTest("./audiocheck/audiocheck.net_sin_700Hz_-3dBFS_3s.wav");
	    sleep(2000);
	    runTest("./audiocheck/audiocheck.net_sin_800Hz_-3dBFS_3s.wav");
	    sleep(2000);
	    runTest("./audiocheck/audiocheck.net_sin_900Hz_-3dBFS_3s.wav");
	    sleep(2000);
	    runTest("./audiocheck/audiocheck.net_sin_1000Hz_-3dBFS_3s.wav");
	    sleep(2000);
	   
	    
	    
	    //all .wav files available
	    for(Note n : analyzer.notes){
	    	
	    	try{
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(n.getSoundFile()).getAbsoluteFile());
				Clip clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				clip.start();
			} 
	    	catch (Exception e) {
				e.printStackTrace();
			}
	    	sleep(500);	
	    }
	    
	    
	    
	    
	}
	
	private void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void runTest(String file){
	    analyzer.setFilename(file);
	    analyzer.startAction();
	    analyzer.stopAction();
	}
	
	
	public static void main(String args[]){
		new AccuracyTestDriver();	
	}
}
