package model.elements;

import model.simulation.FtPlantSimulation;

/**
 * Special type of a {@link BinaryActuator} that represents a conveyor with a given length and methods to update workpiece position
 */
public class Conveyor extends LinearMovementElement implements SimulationUpdateable{

	private boolean workpiecePresent;
	private int workpiecePosition = 0;

	private BinaryActuator motorLeft, motorRight;

	public Conveyor(ActuatorDefinition motorLeft, ActuatorDefinition motorRight, FtPlantSimulation simulation, int length) {
		super(simulation, length);
		this.motorLeft = new BinaryActuator(motorLeft, simulation);
		this.motorRight = new BinaryActuator(motorRight, simulation);
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
		if (this.workpiecePresent && this.motorLeft.isOn()) {
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

	
	public BinaryActuator getMotorLeft() {
		return this.motorLeft;
	}
	
	
	public BinaryActuator getMotorRight() {
		return this.motorRight;
	}
	
	
}
