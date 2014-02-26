package edu.neumont.harmonius.model;

import java.util.Stack;

import edu.neumont.harmonius.controller.AnalyzerController;

public class Model {

	
	Stack<Data> warmUpData = new Stack<Data>();
	Stack<Data> intervalData = new Stack<Data>();
	Stack<Data> noteIDData = new Stack<Data>();
	Stack<Data> perfectPitchData = new Stack<Data>();
	//Stack<Data> sessionTrainingData = new Stack<Data>();
	
	public Model() {
		
	}

	public Data getWarmUpData() {
		return warmUpData.peek();
	}

	public void setWarmUpData(Data d) {
		warmUpData.push(d);
	}
	
	public void removeWarmUpData() {
		warmUpData.pop();
	}

	
	public Data getIntervalData() {
		return intervalData.peek();
	}

	public void setIntervalData(Data d) {
		intervalData.push(d);
	}
	
	public void removeIntervalData() {
		intervalData.pop();
	}
	
	
	
	public Data getNoteIDData() {
		return noteIDData.peek();
	}

	public void setNoteIDData(Data d) {
		noteIDData.push(d);
	}
	
	public void removeNoteIDData() {
		noteIDData.pop();
	}
	
	
	
	public Data getPerfectPitchData() {
		return perfectPitchData.peek();
	}

	public void setPerfectPitchData(Data d) {
		perfectPitchData.push(d);
	}
	
	public void removePerfectPitchData() {
		perfectPitchData.pop();
	}
	
	
}
