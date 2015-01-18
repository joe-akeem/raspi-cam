package de.joeakeem.raspicam.welcomesound;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Plays a welcome sound when the PIR notices movement.
 * The PIR must be connected to GPIO2 (27).
 *  
 * @author joe
 *
 */
public class WelcomeSound {

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
