package edu.neumont.harmonius.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import edu.neumont.harmonius.note.Note;
import edu.neumont.harmonius.note.WesternScale;
import edu.neumont.harmonius.vendor.AudioFloatInputStream;
import edu.neumont.harmonius.vendor.Yin;
import edu.neumont.harmonius.vendor.Yin.DetectedPitchHandler;

public class AnalyzerController {

	javax.sound.sampled.Mixer.Info target;

	public AnalyzerController() {

	}

	private static final long serialVersionUID = 1L;

	
	// volatile because it runs on its own thread - volatile guarantees the most updated value
	volatile AudioInputProcessor aiprocessor;

	String fileName = null;

	// can replace with other tonal scales with their notes and pitch values
	WesternScale ws = new WesternScale();
	
	ArrayList<Note> notes = ws.getNotes();
	
	//
	private double[] pitchHistoryTotal = new double[6000];
	private double averagePitch;
	int pitchCounter = 0;

	public void setInputDevice(javax.sound.sampled.Mixer.Info target) {
		this.target = target;

	}

	class AudioInputProcessor implements Runnable {

		private final int sampleRate;
		private final double audioBufferSize;

		public AudioInputProcessor() {
			sampleRate = 22050; // Hz
			audioBufferSize = 0.1;// Seconds
		}

		public void run() {
			javax.sound.sampled.Mixer.Info selected = (javax.sound.sampled.Mixer.Info) target; // mixer_selector.getSelectedItem();
			if (selected == null)
				return;
			try {

				Mixer mixer = AudioSystem.getMixer(selected);

				AudioFormat format = new AudioFormat(sampleRate, 16, 1, true,
						false);
				
				DataLine.Info dataLineInfo = new DataLine.Info(
						TargetDataLine.class, format);
				/*Besides the class information inherited from its superclass, DataLine.Info provides additional information 
				 * specific to data lines. This information includes
				 *  -the audio formats supported by the data line
				 *  -and the minimum and maximum sizes of its internal buffer
				 */
				
				
				TargetDataLine line = (TargetDataLine) mixer
						.getLine(dataLineInfo);
				/*A target data line is a type of DataLine from which audio data can be read. The most common example is a data line that gets its 
				 * data from an audio capture device. (The device is implemented as a mixer that writes to the target data line.)
				 *Note that the naming convention for this interface reflects the relationship between the line and its mixer. From the perspective
				 * of an application, a target data line may act as a source for audio data.
				 * The target data line can be obtained from a mixer by invoking the getLine method of Mixer with an appropriate DataLine.Info object.
				 */
			
				
				int numberOfSamples = (int) (audioBufferSize * sampleRate);

				line.open(format, numberOfSamples);

				line.start();

				AudioInputStream ais = new AudioInputStream(line);
				if (fileName != null) {
					ais = AudioSystem.getAudioInputStream(new File(fileName));

				}
				AudioFloatInputStream afis = AudioFloatInputStream
						.getInputStream(ais);
				processAudio(afis);
				line.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// destroy current audioprocessor
			aiprocessor = null;

		}

		public void processAudio(AudioFloatInputStream afis)
				throws IOException, UnsupportedAudioFileException {

			Yin.processStream(afis, new DetectedPitchHandler() {

				double[] pitchHistory = new double[6000]; // 600 is default
				// value to get a
				// pretty good
				// visual line of
				// pitch
				int pitchHistoryPos = 0;

				@Override
				public void handleDetectedPitch(float time, float pitch) {

				
					boolean noteDetected = pitch != -1;

					double detectedNote = 69D + (12D * Math.log(pitch / 440D))
							/ Math.log(2D);
					// noteDetected = noteDetected && Math.abs(detectedNote - Math.round(detectedNote)) > 0.3;

					if (pitchHistoryPos == pitchHistory.length)
						pitchHistoryPos = 0;

					pitchHistoryTotal[pitchCounter] = pitch;
					pitchCounter++;

					// ternary for pitch_history as a regular array
					pitchHistory[pitchHistoryPos] = noteDetected ? detectedNote
							: 0.0;

					int jj = pitchHistoryPos;
					
					pitchHistoryPos++;
					
				}

			});

			double pitchAccum = 1.0;
			int pitchCount = 1;
			averagePitch = 0;
			for (int i = 0; i < pitchHistoryTotal.length; i++) {
				
				System.out.println(pitchHistoryTotal[i] + "\t");
				
				if (pitchHistoryTotal[i] > 0) { // filter out zeros and -1 (no signal)

					// filter to get rid of overtones
					if (Math.abs(averagePitch - pitchHistoryTotal[i]) < 300) { // tighten up

						pitchAccum += pitchHistoryTotal[i];
						pitchCount++;
					}
				}
				averagePitch = pitchAccum / pitchCount;
			}
			System.out.println("\n Average pitch = " + averagePitch);
			
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

	public double stopAction() {
		if (aiprocessor == null) {
			return 0;
		} else {
			Yin.stop();
			double returnable = averagePitch;
			//for(int i = 0; i < pitchHistoryTotal.length; i++){pitchHistoryTotal[i] = 0;}
			averagePitch = 0.0;
			return returnable;
		}
	}
}
