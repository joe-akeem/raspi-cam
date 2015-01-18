package de.joeakeem.raspicam.buttoncam;

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
 
	// Remember to add filename and extension!
	private static final String START_INSTRUCTION = "/usr/bin/raspivid -t 0 -h " +
			HEIGHT + " -w "+ WIDTH + " -o " + DEST_DIR;
 
	private static final String KILL_INSTRUCTION = "killall raspivid";

	@Override
	public void handleGpioPinDigitalStateChangeEvent(
			GpioPinDigitalStateChangeEvent event)
	{
		if (event.getState() == PinState.HIGH) {
			startCapture();
		} else {
			killCapture();
		}
	}

	private void killCapture() {
		System.out.println("Killing all recording");
		executeCommand(KILL_INSTRUCTION);
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
