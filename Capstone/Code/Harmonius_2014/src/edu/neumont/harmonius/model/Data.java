package edu.neumont.harmonius.model;

public class Data {
 
	double userPitch;
	double userDuration;
	
	double expectedPitch;
	double expectedDuration;
	
	String feedbackNotes;
	
	
	public Data(){
	 
    }


	public double getUserPitch() {
		return userPitch;
	}


	public void setUserPitch(double userPitch) {
		this.userPitch = userPitch;
	}


	public double getUserDuration() {
		return userDuration;
	}


	public void setUserDuration(double userDuration) {
		this.userDuration = userDuration;
	}


	public double getExpectedPitch() {
		return expectedPitch;
	}


	public void setExpectedPitch(double expectedPitch) {
		this.expectedPitch = expectedPitch;
	}


	public double getExpectedDuration() {
		return expectedDuration;
	}


	public void setExpectedDuration(double expectedDuration) {
		this.expectedDuration = expectedDuration;
	}


	public String getFeedbackNotes() {
		return feedbackNotes;
	}


	public void setFeedbackNotes(String feedbackNotes) {
		this.feedbackNotes = feedbackNotes;
	}
	
	
}
