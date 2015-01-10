package de.joeakeem.raspicam;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Camera {

	public static void main(String[] args) throws InterruptedException {
		final GpioController gpio = GpioFactory.getInstance();

		// provision gpio pin #02 as an input pin with its internal pull down
		// resistor enabled
		final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(
				RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);

		myButton.addListener(new GpioPinListenerDigital() {

			@Override
			public void handleGpioPinDigitalStateChangeEvent(
					GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: "
						+ event.getPin() + " = " + event.getState());
			}

		});

		for (;;) {
			Thread.sleep(500);
		}
	}
}
