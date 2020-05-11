package model.elements;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import model.simulation.FtPlantSimulation;

/**
 * Special type of a {@link BinaryActuator} that represents a conveyor with a given length and methods to update workpiece position
 */
public class Conveyor extends BinaryActuator {

	private boolean workpiecePresent;
	private int workpiecePosition = 0;
	private final int workpieceVelocity = 20; 	// in mm per sec
	private int length;							// in mm
	private int stepSize = 1;

	public Conveyor(ActuatorDefinition actuatorDefinition, FtPlantSimulation simulation, int length) {
		super(actuatorDefinition, simulation);
		this.length = length;
		int updateInterval = simulation.getUpdateInterval();
		
		// Calculate the duration it takes to move the workpiece along the whole conveyor (in ms)
		int transportDuration = (length / workpieceVelocity) * 1000;
		
		// Step size depends on the duration and the updateInterval. 
		this.stepSize = length / ( transportDuration / updateInterval);
	}

	/**
	 * Checks whether or not a workpiece is currently on this conveyor
	 * @return True, if there's a workpiece on this conveyor
	 */
	public boolean workpieceIsPresent() {
		return this.workpiecePresent;
	}

	/**
	 * Moves a workpiece to this conveyor
	 */
	public void addWorkpiece() {
		this.workpiecePresent = true;
	}

	/**
	 * Removes a workpiece from this conveyor
	 */
	public void removeWorkpiece() {
		this.workpiecePresent = false;
		this.workpiecePosition = 0;
	}

	/**
	 * Updates method that is called in every simulation loop. Updates the current position of the workpiece if there is one on this conveyor.
	 */
	public void update() {
		if (this.workpiecePresent && this.isOn()) {
			this.workpiecePosition = Math.min(this.length, this.workpiecePosition + this.stepSize);
		}
	}

	/**
	 * Calculate the relative workpiece position
	 * @return Relative workpiece position (in a range from 0 to 100)
	 */
	public float getRelativeWorkpiecePosition() {
		if (this.workpiecePresent) {
			return (float)this.workpiecePosition / (float)this.length * 100;
		} else {
			return 0;
		}
	}

}
