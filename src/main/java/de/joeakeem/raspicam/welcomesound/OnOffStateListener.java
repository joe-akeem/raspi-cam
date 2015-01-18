package de.joeakeem.raspicam.welcomesound;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class OnOffStateListener implements GpioPinListenerDigital {
	
	private static final String HEIGHT = "720";
	private static final String WIDTH = "960";
	private static final String DEST_DIR = "/home/pi/capture/";
	
	private long lastPlayed = 0;
 
	// Remember to add filename and extension!
	private static final String START_INSTRUCTION = "/usr/bin/raspivid -t 0 -h " +
			HEIGHT + " -w "+ WIDTH + " -o " + DEST_DIR;
 
	@Override
	public void handleGpioPinDigitalStateChangeEvent(
			GpioPinDigitalStateChangeEvent event)
	{
		long now = System.currentTimeMillis();
		if (event.getState() == PinState.HIGH) {
			if (lastPlayed == 0 || now - lastPlayed >= 600000) { // after 10 minutes
				lastPlayed = now;
				playSound();
			}
		}
	}

	private void playSound() {
		
	}

	private void startCapture() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd-HH-mm-ss");
		String filename = START_INSTRUCTION + "vid-"
				+ dateFormat.format(date) + ".h264";
		System.out.println("Starting te record to '" + filename + "'");
		executeCommand(filename);
	}

	private void executeCommand(String cmd) {
		Runtime r = Runtime.getRuntime();
		try {
			r.exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
