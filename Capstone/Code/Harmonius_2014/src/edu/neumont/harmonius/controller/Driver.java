package edu.neumont.harmonius.controller;

import edu.neumont.harmonius.model.Model;
import edu.neumont.harmonius.view.ApplicationView;

public class Driver {

	ApplicationView view;
	Model model;
	AnalyzerController analyzer;
	
	public Driver(){
	
	    model = new Model();
	    analyzer = new AnalyzerController(model);
	    view = new ApplicationView(analyzer);
	    analyzer.getView(view);
	}
	
	public static void main(String args[]){
		new Driver();
		
	}
}
