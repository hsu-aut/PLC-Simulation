package model.elements;

import gui.element.shape.ConveyorShape;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.simulation.FtPlantSimulation;

/**
 * Special type of a {@link BinaryActuator} that represents a conveyor with a given length and methods to update workpiece position
 */
public class Conveyor extends LinearMovementElement {

	private ConveyorShape shape;
	
	private BinaryActuator motorLeft, motorRight;

	private BooleanProperty workpiecePresent = new SimpleBooleanProperty(false);
	private SimpleIntegerProperty workpiecePosition = new SimpleIntegerProperty(0);
	
	private BooleanProperty motorLeftRunning = new SimpleBooleanProperty(false);
	private BooleanProperty motorRightRunning = new SimpleBooleanProperty(false);
	
	public Conveyor(SimulationElementName elementName, ConveyorShape shape, ActuatorDefinition motorLeft, ActuatorDefinition motorRight, FtPlantSimulation simulation, int length) {
		super(simulation, length);
		this.shape = shape;
		this.simulationElementName = elementName;
		this.motorLeft = new BinaryActuator(motorLeft, simulation);
		this.motorRight = new BinaryActuator(motorRight, simulation);
		
		workpiecePresent.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == false) {
					shape.resetPosition();
				}
			}
		});
		
		workpiecePosition.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(workpieceIsPresent()) {
					shape.setPosition(workpiecePosition.get());
				}
			}
		});
		
		motorLeftRunning.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == true) {
					shape.activateLeft();
				}else {
					shape.deactivateLeft();
				}
			}
		});
		
		motorRightRunning.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == true) {
					shape.activateRight();
				}else {
					shape.deactivateRight();
				}
			}
		});
	}

	/**
	 * Checks whether or not a workpiece is currently on this conveyor
	 * @return True, if there's a workpiece on this conveyor
	 */
	public boolean workpieceIsPresent() {
		return this.workpiecePresent.getValue();
	}

	/**
	 * Moves a workpiece to this conveyor
	 */
	public void addWorkpiece() {
		this.workpiecePresent.set(true);
	}

	/**
	 * Removes a workpiece from this conveyor
	 */
	public void removeWorkpiece() {
		this.workpiecePresent.set(false);
		this.workpiecePosition.set(0);
	}

	

	/**
	 * Calculate the relative workpiece position
	 * @return Relative workpiece position (in a range from 0 to 100)
	 */
	public float getRelativeWorkpiecePosition() {
		if (this.workpiecePresent.getValue()) {
			return this.workpiecePosition.floatValue() / (float)this.length * 100;
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
		if (this.workpiecePresent.getValue() && this.motorLeft.isOn()) {
			int newValue = Math.min(this.length, this.workpiecePosition.add(this.stepSize).intValue() );
			this.workpiecePosition.set(newValue);
		}
		
		motorLeftRunning.set(motorLeft.isOn());
		motorRightRunning.set(motorRight.isOn());	
	}
	
	@Override
	public void reset() {
		shape.resetPosition();
		shape.deactivateLeft();
		shape.deactivateRight();
	}
	
}
