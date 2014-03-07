package edu.neumont.harmonius.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import edu.neumont.harmonius.controller.AnalyzerController;



public class ApplicationView extends JFrame {

	Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
	JTabbedPane jtp;
	
	HandyJPanel jp1;
	JLabel jp1Label;
	JLabel labelx;
	@SuppressWarnings("rawtypes")
	JComboBox mixer_selector;
	
	HandyJPanel jp2;
	JLabel jp2Label;
	JTextArea jp2ResultsJTextArea;
	JLabel jp2InstructionsLabel;
	JLabel jp2ResultsLabel;
	JPanel jp2WestButtonPanel;
	JButton startbutton_jp2, stopbutton_jp2;
	
	HandyJPanel jp3;
	JLabel jp3Label;
	JTextArea jp3ResultsJTextArea;
	JLabel jp3InstructionsLabel;
	JLabel jp3ResultsLabel;
	JPanel jp3WestButtonPanel;
	JButton startbutton_jp3, stopbutton_jp3;
	JComboBox<String> jp3RangeJComboBox;
	JButton jp3PlayNoteButton;
	
	JTextArea jp4ResultsJTextArea;
	JComboBox<String> jp4NotesJComboBox;
	String[] noteList = new String[45];
	
	JLabel jp5NoteDisplayJLabel;
	JComboBox jp5RangeJComboBox;
	JTextArea jp5ResultsJTextArea;
	
	public ApplicationView(final AnalyzerController analyzer){

		
		
		setTitle("Harmonius"); 
		setSize(1024,768);  //find out projector size
		setLocationRelativeTo(null);
		setDefaultCloseOperation(3);

		ArrayList<javax.sound.sampled.Mixer.Info> capMixers = new ArrayList<javax.sound.sampled.Mixer.Info>();
		javax.sound.sampled.Mixer.Info mixers[] = AudioSystem.getMixerInfo();

		jtp = new JTabbedPane();

		/*Load images
		 * 
		 */
		BufferedImage musicalNotes = null;
		BufferedImage burntGradient = null;
		try {
		    musicalNotes = ImageIO.read(new File("musicalnotesbg.jpg"));
		    burntGradient = ImageIO.read(new File("burntGradient.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}


		/***********
		 * create GUI for jp1 (JPanel One)
		 ***********/
		jp1 = new HandyJPanel(musicalNotes, 0, 0, 1024, 768);
		jp1.setLayout(new FlowLayout(FlowLayout.CENTER, 80, 80));
		jp1Label = new JLabel();
		jp1Label.setText("Harmonious");
		//jp1Label.setBorder(raisedetched);
		jp1Label.setFont(new Font("Courier New", Font.BOLD, 80));
		jp1Label.setBackground(new Color(242,209,166));
		//label1.setBackground(Color.LIGHT_GRAY);
		
		jp1Label.setForeground(Color.BLACK);
		jp1Label.setOpaque(true);
		jp1.add(jp1Label);
		
		labelx = new JLabel();
		labelx.setText("Please select an input device to use with this session:");
		//labelx.setBorder(raisedetched);
		labelx.setFont(new Font("Courier New", Font.ITALIC, 12));
		labelx.setBackground(new Color(242,209,166));
		//label1.setBackground(Color.LIGHT_GRAY);
		
		labelx.setForeground(Color.BLACK);
		labelx.setOpaque(true);
		jp1.add(labelx);
		
		
		//set up the combobox data for user to select input device	
		for (int i = 0; i < mixers.length; i++) {
			javax.sound.sampled.Mixer.Info mixerinfo = mixers[i];
			if (AudioSystem.getMixer(mixerinfo).getTargetLineInfo().length != 0)
				capMixers.add(mixerinfo);
		}
		mixer_selector = new JComboBox(capMixers.toArray());

		//main.add(mixer_selector);
		jp1.add(mixer_selector);

		analyzer.setInputDevice((javax.sound.sampled.Mixer.Info)mixer_selector.getSelectedItem());


		/***********
		 * create GUI for jp2 (JPanel Two)
		 ***********/
		jp2 = new HandyJPanel(burntGradient, 0, 0, 1024, 768);
		jp2.setLayout(new BorderLayout(20,20));
		jp2Label = new JLabel();
		jp2Label.setText("Pitch Warm Up Training");
		
		//JTextArea jp2InstructionsJTextArea  = new javax.swing.JTextArea();
		jp2ResultsJTextArea = new javax.swing.JTextArea();
		jp2InstructionsLabel = new javax.swing.JLabel();
		jp2ResultsLabel = new javax.swing.JLabel();

		//jp2InstructionsJTextArea.setColumns(20);
		//jp2InstructionsJTextArea.setRows(5);
		//jp2InstructionsJTextArea.setText("This is for Pitch Warm-Up exercises.");


		jp2ResultsJTextArea.setColumns(20);
		jp2ResultsJTextArea.setRows(5);
		jp2ResultsJTextArea.setText("This is for results");

		jp2InstructionsLabel.setText("INSTRUCTIONS:  Press the Start Recording button and sing a note");
		jp2ResultsLabel.setText("RESULTS");

		jp2Label.setBorder(raisedetched);
		jp2ResultsLabel.setBorder(raisedetched);
		jp2ResultsJTextArea.setBorder(raisedetched);
		//jp2InstructionsJTextArea.setBorder(raisedetched);

		jp2.add(jp2Label, BorderLayout.NORTH);
		jp2.add(jp2InstructionsLabel, BorderLayout.CENTER);
		jp2.add(jp2ResultsLabel, BorderLayout.SOUTH);
		jp2.add(jp2ResultsJTextArea, BorderLayout.SOUTH);
		//jp2.add(jp2InstructionsJTextArea, BorderLayout.CENTER);

		//buttons for jp2
		jp2WestButtonPanel = new JPanel();
		startbutton_jp2 = new JButton("Start Recording");
		startbutton_jp2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				analyzer.startAction();
			}
		});
		jp2WestButtonPanel.add(startbutton_jp2);


		stopbutton_jp2 = new JButton("Stop Recording and Submit");
		stopbutton_jp2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				analyzer.stopAction();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				analyzer.warmUpResultAction();
			}
		});
		
		jp2WestButtonPanel.add(stopbutton_jp2);
		jp2WestButtonPanel.setOpaque(false);
		jp2.add(jp2WestButtonPanel, BorderLayout.WEST);


		/*******
		 * create GUI for jp3 (JPanel Three)
		 *******/
		jp3 = new HandyJPanel(burntGradient, 0, 0, 1024, 768);
		jp3.setLayout(new BorderLayout(20,20));
		jp3Label = new JLabel();
		jp3Label.setText("Interval Training");
		

		//JTextArea jp3InstructionsJTextArea  = new javax.swing.JTextArea();
		jp3ResultsJTextArea = new javax.swing.JTextArea();
		jp3InstructionsLabel = new javax.swing.JLabel();
		jp3ResultsLabel = new javax.swing.JLabel();

		//jp3InstructionsJTextArea.setColumns(20);
		//jp3InstructionsJTextArea.setRows(5);
		//jp3InstructionsJTextArea.setText("This is for Interval Training exercises.");
		//jp3.add(jp3InstructionsJTextArea);

		jp3ResultsJTextArea.setColumns(20);
		jp3ResultsJTextArea.setRows(5);
		jp3ResultsJTextArea.setText("This is for results");

		jp3InstructionsLabel.setText("INSTRUCTIONS: Press play to get a note.  Record, get feedback.");
		jp3ResultsLabel.setText("RESULTS:");
		
		
		jp3Label.setBorder(raisedetched);
		jp3ResultsLabel.setBorder(raisedetched);
		jp3ResultsJTextArea.setBorder(raisedetched);
		
		
		jp3.add(jp3Label, BorderLayout.NORTH);
		jp3.add(jp3InstructionsLabel, BorderLayout.CENTER);
		jp3.add(jp3ResultsLabel, BorderLayout.SOUTH);
		jp3.add(jp3ResultsJTextArea, BorderLayout.SOUTH);
		//jp3.add(jp3InstructionsJTextArea, BorderLayout.CENTER);
		

		//buttons for jp3
		jp3WestButtonPanel = new JPanel();
		jp3WestButtonPanel.setSize(100, 50);
		startbutton_jp3 = new JButton("Start Recording");
		startbutton_jp3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				analyzer.startAction();
			}
		});
		jp3WestButtonPanel.add(startbutton_jp3);


		stopbutton_jp3 = new JButton("Stop Recording and Submit");
		stopbutton_jp3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				analyzer.stopAction();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				analyzer.intervalTrainingStopAction();
			}
		});
		jp3WestButtonPanel.add(stopbutton_jp3);
		jp3WestButtonPanel.setOpaque(false);
		jp3.add(jp3WestButtonPanel, BorderLayout.WEST);
		
	
		String[] ranges = {"Soprano", "Alto", "Tenor", "Bass"};
		jp3RangeJComboBox = new JComboBox<String>(ranges);
		jp3WestButtonPanel.add(jp3RangeJComboBox);
		
		jp3PlayNoteButton = new JButton("Play Note");
		jp3PlayNoteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				analyzer.playNote();
			}
		});
		jp3WestButtonPanel.add(jp3PlayNoteButton);
		

		/*
		 * create GUI for jp4 (JPanel Four)
		 */
		HandyJPanel jp4 = new HandyJPanel(burntGradient, 0, 0, 1024, 768);
		jp4.setLayout(new BorderLayout(20,20));
		JLabel jp4Label = new JLabel();
		jp4Label.setText("Note Identification Training");
		//jp4.add(jp4Label);

		//JTextArea jp4InstructionsJTextArea  = new javax.swing.JTextArea();
		jp4ResultsJTextArea = new javax.swing.JTextArea();
		JLabel jp4InstructionsLabel = new javax.swing.JLabel();
		JLabel jp4ResultsLabel = new javax.swing.JLabel();

		//jp4InstructionsJTextArea.setColumns(20);
		//jp4InstructionsJTextArea.setRows(5);
		//jp4InstructionsJTextArea.setText("This is for Note ID training exercises.");
		//jp4.add(jp4InstructionsJTextArea);

		jp4Label.setBorder(raisedetched);
		jp4ResultsLabel.setBorder(raisedetched);
		jp4ResultsJTextArea.setBorder(raisedetched);
		
		jp4ResultsJTextArea.setColumns(20);
		jp4ResultsJTextArea.setRows(5);
		jp4ResultsJTextArea.setText("This is for results");
		jp4.add(jp4ResultsJTextArea);

		jp4InstructionsLabel.setText("INSTRUCTIONS: Press play to hear a note, select the name of the note, and press submit.");
		//jp4.add(jp4InstructionsLabel);
		jp4ResultsLabel.setText("RESULTS");
		//jp4.add(jp4ResultsLabel);

		jp4.add(jp4Label, BorderLayout.NORTH);
		jp4.add(jp4InstructionsLabel, BorderLayout.CENTER);
		jp4.add(jp4ResultsLabel, BorderLayout.SOUTH);
		jp4.add(jp4ResultsJTextArea, BorderLayout.SOUTH);
		
		
		
		//buttons for jp4
		JPanel jp4WestButtonPanel = new JPanel();
		jp4WestButtonPanel.setSize(100, 50);
		JButton startbutton_jp4 = new JButton("Play Note");
    	
		startbutton_jp4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				analyzer.noteIdPlayNote();
				
			}
		});
		jp4WestButtonPanel.add(startbutton_jp4);

		noteList = analyzer.getNoteNameList();
		jp4NotesJComboBox = new JComboBox<String>(noteList);
		jp4WestButtonPanel.add(jp4NotesJComboBox);
		
		JButton stopbutton_jp4 = new JButton("Submit Answer");
		stopbutton_jp4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				analyzer.compareNoteId((String)jp4NotesJComboBox.getSelectedItem());
			}
		});
		jp4WestButtonPanel.add(stopbutton_jp4);
		jp4WestButtonPanel.setOpaque(false);
		jp4.add(jp4WestButtonPanel, BorderLayout.WEST);
		
		
		
		
		

		/*
		 * create GUI for jp5 (JPanel Five)
		 */
		HandyJPanel jp5 = new HandyJPanel(burntGradient, 0, 0, 1024, 768);
		jp5.setLayout(new BorderLayout(20,20));
		JLabel jp5Label = new JLabel();
		jp5Label.setText("Perfect Pitch");
		jp5.add(jp5Label);

		//JTextArea jp5InstructionsJTextArea  = new javax.swing.JTextArea();
		jp5ResultsJTextArea = new javax.swing.JTextArea();
		JLabel jp5InstructionsLabel = new javax.swing.JLabel();
		JLabel jp5ResultsLabel = new javax.swing.JLabel();

		//jp5InstructionsJTextArea.setColumns(20);
		//jp5InstructionsJTextArea.setRows(5);
		//jp5InstructionsJTextArea.setText("This is for Interval Training exercises.");
		//jp5.add(jp3InstructionsJTextArea);

		jp5ResultsJTextArea.setColumns(20);
		jp5ResultsJTextArea.setRows(5);
		jp5ResultsJTextArea.setText("This is for results");

		jp5InstructionsLabel.setText("INSTRUCTIONS: Press Select Note to get a note name.  Record your voice, get feedback.");
		jp5ResultsLabel.setText("RESULTS:");
		
		
		jp5Label.setBorder(raisedetched);
		jp5ResultsLabel.setBorder(raisedetched);
		jp5ResultsJTextArea.setBorder(raisedetched);
		
		
		jp5.add(jp5Label, BorderLayout.NORTH);
		jp5.add(jp5InstructionsLabel, BorderLayout.CENTER);
		jp5.add(jp5ResultsLabel, BorderLayout.SOUTH);
		jp5.add(jp5ResultsJTextArea, BorderLayout.SOUTH);
		//jp5.add(jp5InstructionsJTextArea, BorderLayout.CENTER);
		

		//buttons for jp5
		JPanel jp5WestButtonPanel = new JPanel();
		jp5WestButtonPanel.setSize(100, 50);
		
		String[] PPranges = {"Soprano", "Alto", "Tenor", "Bass"};
		jp5RangeJComboBox = new JComboBox<String>(PPranges);
		jp5WestButtonPanel.add(jp5RangeJComboBox);
		
		
		JButton getNotebutton_jp5 = new JButton("Get Note");
		getNotebutton_jp5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				analyzer.setPPJLabelToCurrentNote();
			}
		});
		jp5WestButtonPanel.add(getNotebutton_jp5);
		
		jp5NoteDisplayJLabel  = new JLabel("E2");
		jp5WestButtonPanel.add(jp5NoteDisplayJLabel);
		
		
		
		JButton startbutton_jp5 = new JButton("Start Recording");
		startbutton_jp5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				analyzer.startAction();
			}
		});
		jp5WestButtonPanel.add(startbutton_jp5);


		JButton stopbutton_jp5 = new JButton("Stop Recording and Submit");
		stopbutton_jp5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				analyzer.stopAction();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				analyzer.PPstopAction();
			}
		});
		jp5WestButtonPanel.add(stopbutton_jp5);
		jp5WestButtonPanel.setOpaque(false);
		jp5.add(jp5WestButtonPanel, BorderLayout.WEST);


		/*
		 * create GUI for jp6 (JPanel Six) -TODO
		 */
		HandyJPanel jp6 = new HandyJPanel(burntGradient,  0, 0, 1024, 768);

		JLabel label6 = new JLabel();
		label6.setText("Session Training");
		jp6.add(label6);

		JTextArea jp6InstructionsJTextArea  = new javax.swing.JTextArea();
		JTextArea jp6ResultsJTextArea = new javax.swing.JTextArea();
		JLabel jp6InstructionsLabel = new javax.swing.JLabel();
		JLabel jp6ResultsLabel = new javax.swing.JLabel();

		jp6InstructionsJTextArea.setColumns(20);
		jp6InstructionsJTextArea.setRows(5);
		jp6InstructionsJTextArea.setText("This is for Session Training exercises.");
		jp6.add(jp6InstructionsJTextArea);

		jp6ResultsJTextArea.setColumns(20);
		jp6ResultsJTextArea.setRows(5);
		jp6ResultsJTextArea.setText("This is for results");
		jp6.add(jp6ResultsJTextArea);

		jp6InstructionsLabel.setText("INSTRUCTIONS");
		jp6.add(jp6InstructionsLabel);
		jp6ResultsLabel.setText("RESULTS");
		jp6.add(jp6ResultsLabel);

		
		//adds panels jp1 - jp6 to the appropriate tabbed windows
		jtp.addTab("Welcome Page", jp1);
		jtp.addTab("Warm Up", jp2);
		jtp.addTab("Interval Training", jp3);
		jtp.addTab("Note ID", jp4);
		jtp.addTab("Perfect Pitch", jp5);
		//jtp.addTab("Session Training", jp6);

		getContentPane().add(jtp);
		setVisible(true); 
			
	      
	}
	
	public void setjp2ResultsJTextArea(String s){
		jp2ResultsJTextArea.setText(s);
	}
	
	public void setjp5ResultsJTextArea(String s){
		jp5ResultsJTextArea.setText(s);
	}
	
	public String getJjp3JComboBoxSelected(){
		return (String)jp3RangeJComboBox.getSelectedItem();
	}
	
	public String getJp5RangeJComboBoxSelected(){
		return (String)jp5RangeJComboBox.getSelectedItem();
	}
	
	public void setjp3ResultsJTextArea(String s){
		jp3ResultsJTextArea.setText(s);
	}
	
	public void setjp4ResultsJTextArea(String s){
		jp4ResultsJTextArea.setText(s);
	}
	
	public void setjp5NoteDisplayJLabel(String s){
		jp5NoteDisplayJLabel.setText(s);
	}
}


