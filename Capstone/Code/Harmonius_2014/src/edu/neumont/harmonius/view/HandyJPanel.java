package edu.neumont.harmonius.view;


/*
 * Written by Scott McNulty
 * 
 * HandyJPanel - used to have a background image on the JPanel,
 *    handles all the repainting 
 */

import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import javax.swing.JPanel;

public class HandyJPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	Image img;
	LayoutManager lytmgr;
	int x,y,w,h;
	
	public HandyJPanel(Image img, int x, int y, int w, int h) {
		super(true);
		this.img = img;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;	
	}

	public HandyJPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(img != null){
			g.drawImage(img, x, y, null);
		}
	}
}
