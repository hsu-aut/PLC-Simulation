package model.elements;

import gui.element.shape.GateDoorShape;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.simulation.FtPlantSimulation;
import model.simulation.SimulationBuilder;

public class GateDoor extends MovingElement {

	private BinaryActuator openDoorActuator, closeDoorActuator;
	private BinarySensor sensorOpen, sensorClosed;

	private GateDoorShape shape;

	private int doorPosition = 0; // 0: closed, distance: open

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
		this.sensorClosed.activate();
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
	}

	
	private void openDoor() {
		// as soon is door is opening, deactivate closed sensor
		this.sensorClosed.deactivate();
		this.doorPosition = Math.min(this.distance, this.doorPosition + this.stepSize);
		if (this.doorPosition == this.distance) {
			this.sensorOpen.activate();
		} else {
			this.sensorOpen.deactivate();
		}
	}

	private void closeDoor() {
		// as soon is door is closing, deactivate open sensor
		this.sensorOpen.deactivate();
		this.doorPosition = Math.max(0, this.doorPosition - this.stepSize);
		if (this.doorPosition == 0) {
			this.sensorClosed.activate();
		} else {
			this.sensorClosed.deactivate();
		}
	}

	public int getPosition() {
		return this.doorPosition;
	}

	boolean isOpen() {
		if (this.doorPosition == this.distance) {
			return true;
		}
		return false;
	}

	@Override
	public void reset() {
		this.doorPosition = 0;
		this.sensorClosed.activate();
		this.sensorOpen.deactivate();
		shape.doorNotMoving();
	}
}
