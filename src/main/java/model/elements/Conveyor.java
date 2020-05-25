package model.elements;

import gui.element.shape.ConveyorShape;
import model.simulation.FtPlantSimulation;

/**
 * Special type of a {@link BinaryActuator} that represents a conveyor with a given length and methods to update workpiece position
 */
public class Conveyor extends MovingElement {

	private ConveyorShape shape;

	private BinaryActuator motorLeft, motorRight;

	private boolean workpiecePresent = false;
	private boolean workpieceBlocked = false;
	private int workpiecePosition = 0;

	public Conveyor(SimulationElementName elementName, ConveyorShape shape, ActuatorDefinition motorLeft, ActuatorDefinition motorRight,
			FtPlantSimulation simulation, int length) {
		super(simulation, length);
		this.shape = shape;
		this.shape.setLength(length);
		this.simulationElementName = elementName;
		this.motorLeft = new BinaryActuator(motorLeft, simulation);
		this.motorRight = new BinaryActuator(motorRight, simulation);

	}

	/**
	 * Checks whether or not a workpiece is currently on this conveyor
	 * 
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
		this.shape.resetPosition();
		this.workpiecePosition = 0;
	}

	public void blockWorkpiece() {
		this.workpieceBlocked = true;
	}

	public void unblockWorkpiece() {
		this.workpieceBlocked = false;
	}

	/**
	 * Calculate the relative workpiece position
	 * 
	 * @return Relative workpiece position (in a range from 0 to 100)
	 */
	public float getRelativeWorkpiecePosition() {
		if (this.workpiecePresent) {
			return (float) this.workpiecePosition / (float) this.distance * 100;
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

	/**
	 * Updates method that is called in every simulation loop. Updates the current position of the workpiece if there is one on this conveyor.
	 */
	public void update() {
		if (this.motorLeft.isOn() && this.workpiecePresent && !this.workpieceBlocked) {
			this.workpiecePosition = Math.min(this.distance, this.workpiecePosition + this.stepSize);

			this.shape.setRelativePosition(this.getRelativeWorkpiecePosition());
		}

		if (this.motorLeft.isOn()) {
			shape.activateLeft();
		} else {
			shape.deactivateLeft();
		}

		if (this.motorRight.isOn()) {
			shape.activateRight();
		} else {
			shape.deactivateRight();
		}
	}

	@Override
	public void reset() {
		this.unblockWorkpiece();
		this.removeWorkpiece();
		this.shape.resetPosition();
		this.shape.deactivateLeft();
		this.shape.deactivateRight();
	}

	
	public ConveyorShape getShape() {
		return this.shape;
	}
	
}
