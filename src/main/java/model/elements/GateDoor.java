package model.elements;

import gui.element.shape.GateDoorShape;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.simulation.FtPlantSimulation;
import model.simulation.SimulationBuilder;

public class GateDoor extends LinearMovementElement {

	private BinaryActuator openDoorActuator, closeDoorActuator;
	private BinarySensor sensorOpen, sensorClosed;

	private GateDoorShape shape;

	private int doorPosition = 0; // 0: closed, length: open

	/**
	 * 
	 * @param simulation
	 * @param openDoorActuator
	 * @param closeDoorActuator
	 * @param length
	 */
	public GateDoor(FtPlantSimulation simulation, GateDoorShape shape, ActuatorDefinition openActuator, ActuatorDefinition closeActuator, BinarySensor sensorOpen, BinarySensor sensorClosed, int length) {

		super(simulation, length);
		this.shape = shape;
		this.openDoorActuator = new BinaryActuator(openActuator, simulation);
		this.closeDoorActuator = new BinaryActuator(closeActuator, simulation);
		this.sensorOpen = sensorOpen;
		this.sensorClosed = sensorClosed;
	}

	public void update() {
		this.shape.doorNotMoving();
		if (openDoorActuator.isOn() && closeDoorActuator.isOn()) {
			System.out.println("WARNING: You are trying to open and close a door at the same time. In reality, this could destroy the actuator.");
		} else if (openDoorActuator.isOn() && !closeDoorActuator.isOn()) {
			this.shape.doorIsMoving();
			this.openDoor();
		} else if (closeDoorActuator.isOn() && !openDoorActuator.isOn()) {
			this.shape.doorIsMoving();
			this.closeDoor();
		}

//		stateOpenSensor.set(sensorOpen.getState());
//		stateCloseSensor.set(sensorClosed.getState());
	}

	
	private void openDoor() {
		this.doorPosition = Math.min(this.length, this.doorPosition + this.stepSize);
		if (this.doorPosition == this.length) {
			this.shape.doorIsOpen();
			this.sensorOpen.writeBooleanNode(true);
		} else {
			this.sensorOpen.writeBooleanNode(false);
		}
	}

	private void closeDoor() {
		this.doorPosition = Math.max(0, this.doorPosition - this.stepSize);
		if (this.doorPosition == 0) {
			this.shape.doorIsClosed();
			this.sensorClosed.writeBooleanNode(true);
		} else {
			this.sensorClosed.writeBooleanNode(false);
		}
	}

	public int getPosition() {
		return this.doorPosition;
	}

	boolean isOpen() {
		if (this.doorPosition == this.length) {
			return true;
		}
		return false;
	}

	@Override
	public void reset() {
		this.doorPosition = 0;
		this.sensorClosed.writeBooleanNode(true);
		this.sensorOpen.writeBooleanNode(true);
		shape.doorNotMoving();
	}
}
