package model.elements;

import gui.element.Direction;
import gui.element.shape.ConveyorShape;
import javafx.scene.layout.Pane;
import model.simulation.FtPlantSimulation;

/**
 * Special type of a {@link BinaryActuator} that represents a conveyor with a given length and methods to update workpiece position
 */
public class Conveyor extends MovingElement {

	private ConveyorShape shape;

	private BinaryActuator motorLeft, motorRight;

	private boolean workpiecePresent = false;
	private boolean workpieceBlocked = false;
	private int workpiecePosition;

	public Conveyor(SimulationElementName elementName, Pane pane, double x, double y, boolean horizontal, ActuatorDefinition motorLeft, ActuatorDefinition motorRight,
			FtPlantSimulation simulation, int length) {
		super(simulation, length);
		this.shape = new ConveyorShape(pane, x, y, length, horizontal);
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
	public void addWorkpiece(boolean onRight) {
		this.shape.addWorkpiece(true);
		this.workpiecePresent = true;
		
		if(onRight) {
			this.workpiecePosition = this.distance;
		} else {
			this.workpiecePosition = 0;
		}
	}

	/**
	 * Removes a workpiece from this conveyor
	 */
	public void removeWorkpiece() {
		this.workpiecePresent = false;
		this.shape.resetPosition();
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
			this.workpiecePosition = Math.max(0, this.workpiecePosition - this.stepSize);
			this.shape.moveLeft(this.getRelativeWorkpiecePosition());
//			this.shape.setRelativePosition(this.getRelativeWorkpiecePosition());
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
