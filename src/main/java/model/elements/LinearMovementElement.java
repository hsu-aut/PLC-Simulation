package model.elements;

import model.simulation.FtPlantSimulation;

public class LinearMovementElement {

	private final int actuatorVelocity = 20; 	// in mm per sec
	protected int length;							// in mm
	protected int stepSize = 1;
	
	public LinearMovementElement(FtPlantSimulation simulation, int length) {

		this.length = length;
		
		int updateInterval = simulation.getUpdateInterval();
		
		// Calculate the duration it takes to move the workpiece along the whole conveyor (in ms)
		int transportDuration = (length / actuatorVelocity) * 1000;
		
		// Step size depends on the duration and the updateInterval. 
		this.stepSize = length / ( transportDuration / updateInterval);
	}

}
