package de.joeakeem.raspicam.welcomesound;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class OnOffStateListener implements GpioPinListenerDigital {
	
	public static final Logger LOG = Logger.getLogger(OnOffStateListener.class);
	
	private static final String HEIGHT = "720";
	private static final String WIDTH = "960";
	private static final String DEST_DIR = "/home/pi/capture/";
	
	// Remember to add filename and extension!
	private static final String START_INSTRUCTION = "/usr/bin/raspivid -t 30000 -h " +
			HEIGHT + " -w "+ WIDTH + " -o " + DEST_DIR;
	
	private static final String OXM_PLAYER_INTRUCTION = "omxplayer";
	
	private long lastPlayed = 0;
	private Random rand = new Random();
	private boolean capturing = false;

 
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
			if (!capturing) {
				capturing = true;
				try {
					startCapture();
				} catch (InterruptedException e) {
					// ignore
				}
			} else {
				LOG.info("Already capturing - do nothing.");
			}
		}
	}

	private void playSound() {
		File soundsDir = new File("src/main/resources/sounds");
		File[] soundFiles = soundsDir.listFiles();
		if (soundFiles != null) {
			int soundToPlay = rand.nextInt(soundFiles.length);
			LOG.info("Playing sound '" + soundFiles[soundToPlay] + "'");
			executeCommand(OXM_PLAYER_INTRUCTION + " " + soundFiles[soundToPlay]);
		} else {
			LOG.error("No sounds to play!");
		}
	}

	private void startCapture() throws InterruptedException {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd-HH-mm-ss");
		String filename = START_INSTRUCTION + "vid-"
				+ dateFormat.format(date) + ".h264";
		LOG.info("Starting to record to '" + filename + "'");
		executeCommand(filename).waitFor();
		LOG.info("Done recording to '" + filename + "'");
		capturing = false;
	}

	private Process executeCommand(String cmd) {
		Runtime r = Runtime.getRuntime();
		try {
			return r.exec(cmd);
		} catch (IOException e) {
			LOG.error("Failed to execute command '" + cmd + "'", e);
		}
		return  null;
	}
}
