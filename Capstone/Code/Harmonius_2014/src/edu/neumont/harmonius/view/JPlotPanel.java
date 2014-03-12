package edu.neumont.harmonius.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.JPanel;

public class JPlotPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	Image img;
	LayoutManager lytmgr;
	int x,y,w,h;
	ArrayList<Float> plotData;
	private Float targetLine;
	
	public JPlotPanel(Image img, int x, int y, int w, int h) {
		super(true);
		this.img = img;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;	
	}

	public JPlotPanel(boolean isDoubleBuffered, ArrayList<Float> pitches, Float target) {
		super(isDoubleBuffered);
		plotData = pitches;
		targetLine = target;
		//repaint();
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
	            g.fillOval(x, y, 8, 8);
			}
		}
	}
}