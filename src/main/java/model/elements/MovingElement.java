package model.elements;

import model.simulation.FtPlantSimulation;

public abstract class MovingElement extends SimulationUpdateable {

	private final int actuatorVelocity = 20; 	// in mm per sec
	protected int distance;							// in mm
	protected int stepSize = 1;
	
	public MovingElement(FtPlantSimulation simulation, int movementDistance) {

		this.simulation = simulation;
		this.distance = movementDistance;
		
		int updateInterval = simulation.getUpdateInterval();
		
		// Calculate the duration it takes to move the workpiece along the whole conveyor (in ms)
		int transportDuration = (movementDistance / actuatorVelocity) * 1000;
		
		// Step size depends on the duration and the updateInterval. 
		this.stepSize = movementDistance / ( transportDuration / updateInterval);
	}

}
