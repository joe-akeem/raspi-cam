package de.joeakeem.raspicam.buttoncam;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Starts the camera when the push button is pressed, stops it
 * when the button is released.
 * The button must be connected to GPIO2 (27).
 *  
 * @author joe
 *
 */
public class ButtonCamera {

	public static void main(String[] args) throws InterruptedException {
		final GpioController gpio = GpioFactory.getInstance();

		// provision gpio pin #02 as an input pin with its internal pull down
		// resistor enabled
		final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(
				RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);

		myButton.addListener(new OnOffStateListener());

		for (;;) {
			Thread.sleep(500);
		}
	}
}
