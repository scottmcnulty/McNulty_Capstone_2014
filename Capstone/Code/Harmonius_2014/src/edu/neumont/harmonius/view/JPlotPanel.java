package edu.neumont.harmonius.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.JPanel;

public class JPlotPanel extends JPanel {

	Image img;
	LayoutManager lytmgr;
	int x,y,w,h;
	ArrayList<Float> plotData;
	double targetLine;
	
	public JPlotPanel(Image img, int x, int y, int w, int h) {
		super(true);
		this.img = img;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;	
		this.setSize(400, 300);
	}

	public JPlotPanel(ArrayList<Float> pitches, double target) {
		super();
		plotData = pitches;
		targetLine = target;
		this.setSize(400, 300);
		this.setPreferredSize(new Dimension(500, 500)); 
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(img != null){
			g.drawImage(img, x, y, null);
		}
		else{
			int time = 0;
			for(Float f : plotData){
				int x = time;
	            int y = (int)f.floatValue();
	            g.setColor(Color.BLACK);
	            g.fillOval(x, y, 12, 12);
	            g.setColor(Color.GREEN);
	            g.fillRect(x, (int)targetLine, 8, 8);
	            time+=10;
			}
		}
	}
}