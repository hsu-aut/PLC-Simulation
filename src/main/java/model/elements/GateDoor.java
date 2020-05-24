package model.elements;

import model.simulation.FtPlantSimulation;
import model.simulation.SimulationBuilder;

public class GateDoor extends LinearMovementElement {

	private BinaryActuator openDoorActuator;
	private BinaryActuator closeDoorActuator;

	private int doorPosition = 0; // 0: closed, length: open

	/**
	 * 
	 * @param simulation
	 * @param openDoorActuator
	 * @param closeDoorActuator
	 * @param length
	 */
	public GateDoor(FtPlantSimulation simulation, ActuatorDefinition openDoorActuatorDefinition, ActuatorDefinition closeDoorActuatorDefinition, int length) {
		super(simulation, length);
		this.openDoorActuator = new BinaryActuator(openDoorActuatorDefinition, simulation);
		this.closeDoorActuator = new BinaryActuator(closeDoorActuatorDefinition, simulation);
	}

	public void update() {
		if (openDoorActuator.isOn() && closeDoorActuator.isOn()) {
			System.out.println("WARNING: You are trying to open and close a door at the same time. In reality, this could destroy the actuator.");
		} else if (openDoorActuator.isOn() && !closeDoorActuator.isOn()) {
			this.openDoor();
		} else if (closeDoorActuator.isOn() && !openDoorActuator.isOn()) {
			this.closeDoor();
		}
	}

	private void openDoor() {
		this.doorPosition = Math.min(this.length, this.doorPosition + this.stepSize);
	}

	private void closeDoor() {
		this.doorPosition = Math.max(0, this.doorPosition - this.stepSize);
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
		// TODO Auto-generated method stub
		
	}
}
