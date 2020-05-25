package model.elements;

import gui.element.shape.TurntableShape;
import model.simulation.FtPlantSimulation;

//TODO: Turntable and GateDoor are very similar, should be handled in one class

public class Turntable extends MovingElement {

	private BinaryActuator turnClockwise, turnCounterClockwise;
	private BinarySensor sensorHorizontal, sensorVertical;

	private TurntableShape shape;

	private int turntablePosition = 40; // 0: horizontal, distance: vertical

	/**
	 * 
	 * @param simulation
	 * @param turnClockwise
	 * @param turnCounterClockwise
	 * @param length
	 */
	public Turntable(SimulationElementName elementName, FtPlantSimulation simulation, TurntableShape shape, ActuatorDefinition turnClockwise, ActuatorDefinition turnCounterClockwise, BinarySensor sensorHorizontal, BinarySensor sensorVertical, int length) {

		super(simulation, length);
		this.simulationElementName = elementName;
		this.shape = shape;
		this.turnClockwise = new BinaryActuator(turnClockwise, simulation);
		this.turnCounterClockwise = new BinaryActuator(turnCounterClockwise, simulation);
		this.sensorHorizontal = sensorHorizontal;
		this.sensorVertical = sensorVertical;
	}

	public void update() {
		this.shape.deactivateCenter();
		if (turnClockwise.isOn() && turnCounterClockwise.isOn()) {
			System.out.println("WARNING: You are trying to turn the actuator in two directions. In reality, this could destroy the actuator.");
		} else if (turnClockwise.isOn() && !turnCounterClockwise.isOn()) {
			this.shape.activateCenter();
			this.turnClockwise();
		} else if (turnCounterClockwise.isOn() && !turnClockwise.isOn()) {
			this.shape.activateCenter();
			this.turnCounterClockwise();
		}

//		stateOpenSensor.set(sensorOpen.getState());
//		stateCloseSensor.set(sensorClosed.getState());
	}

	
	private void turnClockwise() {
		this.turntablePosition = Math.min(this.distance, this.turntablePosition + this.stepSize);
		if (this.turntablePosition == this.distance) {
			this.shape.conVertical();
			this.sensorVertical.activate();
		} else {
			this.sensorVertical.deactivate();
		}
	}

	private void turnCounterClockwise() {
		this.turntablePosition = Math.max(0, this.turntablePosition - this.stepSize);
		if (this.turntablePosition == 0) {
			this.shape.conHorizontal();
			this.sensorHorizontal.activate();
		} else {
			this.sensorHorizontal.deactivate();
		}
	}

	public int getPosition() {
		return this.turntablePosition;
	}

	boolean isHorizontal() {
		return (this.turntablePosition ==  0);
	}
	
	
	boolean isVertical() {
		return (this.turntablePosition == this.distance);
	}

	@Override
	public void reset() {
		this.turntablePosition = 40;
		this.sensorVertical.reset();
		this.sensorHorizontal.reset();
		shape.deactivateCenter();
	}
}
