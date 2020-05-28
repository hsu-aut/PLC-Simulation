package model.simulation;

import model.elements.BinarySensor;

public class SensorTrigger implements Runnable {

	private BinarySensor sensor;
	private int triggerAmount, waitTime;

	SensorTrigger(BinarySensor sensor, int triggerAmount, int waitTime) {
		this.sensor = sensor;
		this.triggerAmount = triggerAmount;
		this.waitTime = waitTime;
	}

	@Override
	public void run() {
		for (int i = 0; i < triggerAmount; i++) {
			System.out.println("trigger sensor: " + this.sensor.getTagName() + " for time: " + i);
			
			try {
				Thread.sleep(this.waitTime);
				this.sensor.activate();
				Thread.sleep(this.waitTime);
				this.sensor.deactivate();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
