package edu.neumont.harmonius.model;

import java.util.ArrayList;

public class MusicalWord {

	private final int SAMPLES_PER_SECOND = 40;
	private ArrayList<Float> pitchSamples = new ArrayList<Float>();
	private float duration;
	private float pitch;
	
	public MusicalWord(ArrayList<Float> samples, float time, float calcPitch){
		/*
		 * Will be (null, durationOfRest, 0) for a rest or a break
		 */
		
		if(samples != null){
			pitchSamples = samples;
			duration = samples.size()/SAMPLES_PER_SECOND;
		}
		else{
			duration = time;
		}
		pitch = calcPitch;
	}

	public ArrayList<Float> getPitchSamples() {
		return pitchSamples;
	}

	public float getDuration() {
		return duration;
	}

	public float getPitch() {
		return pitch;
	}	
	
	@Override
	public String toString(){
		String result = "Word stats: pitch " + pitch + ", duration " + duration + " seconds, ";
		String samples = "";
		for(Float f: pitchSamples){
			samples += " " + f.floatValue();
		}
		return result + samples;
		
	}
}