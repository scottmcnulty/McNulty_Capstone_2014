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

				/*
				 * JAVA SOUND.SAMPLED.MIXER documentation: The Mixer interface
				 * represents a mixer which as we have seen
				 * represents either a hardware or a software device. The Mixer
				 * interface provides methods for obtaining a mixer's lines.
				 * These include source lines, which feed audio to the mixer,
				 * and target lines, to which the mixer delivers its mixed
				 * audio. For an audio-input mixer, the source lines are input
				 * ports such as the microphone input, and the target lines are
				 * TargetDataLines (described below), which deliver audio to the
				 * application program. For an audio-output mixer, on the other
				 * hand, the source lines are Clips or SourceDataLines
				 * (described below), to which the application program feeds
				 * audio data, and the target lines are output ports such as the
				 * speaker. A Mixer is defined as having one or more source
				 * lines and one or more target lines. Note that this definition
				 * means that a mixer need not actually mix data; it might have
				 * only a single source line. The Mixer API is intended to
				 * encompass a variety of devices, but the typical case supports
				 * mixing. The Mixer interface supports synchronization; that
				 * is, you can specify that two or more of a mixer's lines be
				 * treated as a synchronized group. Then you can start, stop, or
				 * close all those data lines by sending a single message to any
				 * line in the group, instead of having to control each line
				 * individually. With a mixer that supports this feature, you
				 * can obtain sample-accurate synchronization between lines.
				 */
				Mixer mixer = AudioSystem.getMixer(selected);

				AudioFormat format = new AudioFormat(sampleRate, 16, 1, true,
						false);

				/*
				 * JAVA SOUND.SAMPLED.LINE documentation:
				 * 
				 * The generic Line interface does not provide a means to start
				 * and stop playback or recording. For that you need a data
				 * line. The DataLine interface supplies the following
				 * additional media-related features beyond those of a Line:
				 * 
				 * Audio format – Each data line has an audio format associated
				 * with its data stream. Media position – A data line can report
				 * its current position in the media, expressed in sample
				 * frames. This represents the number of sample frames captured
				 * by or rendered from the data line since it was opened. Buffer
				 * size – This is the size of the data line's internal buffer in
				 * bytes. For a source data line, the internal buffer is one to
				 * which data can be written, and for a target data line it's
				 * one from which data can be read. Level (the current amplitude
				 * of the audio signal) Start and stop playback or capture Pause
				 * and resume playback or capture Flush (discard unprocessed
				 * data from the queue) Drain (block until all unprocessed data
				 * has been drained from the queue, and the data line's buffer
				 * has become empty) Active status – A data line is considered
				 * active if it is engaged in active presentation or capture of
				 * audio data to or from a mixer. Events – START and STOP events
				 * are produced when active presentation or capture of data from
				 * or to the data line starts or stops.
				 * 
				 * A TargetDataLine receives audio data from a mixer. Commonly,
				 * the mixer has captured audio data from a port such as a
				 * microphone; it might process or mix this captured audio
				 * before placing the data in the target data line's buffer. The
				 * TargetDataLine interface provides methods for reading the
				 * data from the target data line's buffer and for determining
				 * how much data is currently available for reading.
				 * 
				 * A SourceDataLine receives audio data for playback. It
				 * provides methods for writing data to the source data line's
				 * buffer for playback, and for determining how much data the
				 * line is prepared to receive without blocking.
				 * 
				 * A Clip is a data line into which audio data can be loaded
				 * prior to playback. Because the data is pre-loaded rather than
				 * streamed, the clip's duration is known before playback, and
				 * you can choose any starting position in the media. Clips can
				 * be looped, meaning that upon playback, all the data between
				 * two specified loop points will repeat a specified number of
				 * times, or indefinitely.
				 */
				
				
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
			for(int i = 0; i < pitchHistoryTotal.length; i++){pitchHistoryTotal[i] = 0;}
			averagePitch = 0.0;
			return returnable;
		}
	}
}
