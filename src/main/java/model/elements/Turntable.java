package model.elements;

import gui.element.shape.ConveyorShape;
import gui.element.shape.TurntableShape;
import javafx.scene.layout.Pane;
import model.simulation.FtPlantSimulation;


public class Turntable extends MovingElement {

	private BinaryActuator turnClockwise, turnCounterClockwise;
	private BinarySensor sensorHorizontal, sensorVertical;
	private Conveyor conveyor;

	private TurntableShape shape;

	private int turntablePosition = 40; // 0: horizontal, distance: vertical

	/**
	 * 
	 * @param simulation
	 * @param turnClockwise
	 * @param turnCounterClockwise
	 * @param radius
	 */
	public Turntable(SimulationElementName elementName, FtPlantSimulation simulation, TurntableShape shape, ActuatorDefinition turnClockwise, ActuatorDefinition turnCounterClockwise, BinarySensor sensorHorizontal, BinarySensor sensorVertical, int radius, ActuatorDefinition actuatorConveyorLeft, ActuatorDefinition actuatorConveyorRight) {

		super(simulation, radius);
		this.simulationElementName = elementName;
		this.shape = shape;
		Pane conveyorPane = new Pane();
		int conveyorLength = (int) ((float) 0.95 * 2 * radius);
		this.conveyor = new Conveyor(SimulationElementName.ConveyorOnTurntable, conveyorPane, 10, 45, true, actuatorConveyorLeft, actuatorConveyorRight, simulation, conveyorLength);
		this.shape.addConveyorPane(conveyorPane);
		this.turnClockwise = new BinaryActuator(turnClockwise, simulation);
		this.turnCounterClockwise = new BinaryActuator(turnCounterClockwise, simulation);
		this.sensorHorizontal = sensorHorizontal;
		this.sensorVertical = sensorVertical;
	}

	public void update() {
		this.shape.deactivateCenter();

		this.conveyor.update();
		if (turnClockwise.isOn() && turnCounterClockwise.isOn()) {
			System.out.println("WARNING: You are trying to turn the actuator in two directions. In reality, this could destroy the actuator.");
		} else if (turnClockwise.isOn() && !turnCounterClockwise.isOn()) {
			this.shape.activateCenter();
			this.turnClockwise();
		} else if (turnCounterClockwise.isOn() && !turnClockwise.isOn()) {
			this.shape.activateCenter();
			this.turnCounterClockwise();
		}

	}

	
	private void turnClockwise() {
		this.sensorHorizontal.deactivate();
		this.turntablePosition = Math.min(this.distance, this.turntablePosition + this.stepSize);
		if (this.turntablePosition == this.distance) {
			this.shape.conVertical();
			this.sensorVertical.activate();
		} else {
			this.sensorVertical.deactivate();
		}
	}

	private void turnCounterClockwise() {
		this.sensorVertical.deactivate();
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

	public boolean isHorizontal() {
		return (this.turntablePosition ==  0);
	}
	
	
	public boolean isVertical() {
		return (this.turntablePosition == this.distance);
	}
	
	public Conveyor getConveyor() {
		return this.conveyor;
	}

	@Override
	public void reset() {
		this.conveyor.reset();
		this.turntablePosition = 40;
		this.sensorVertical.reset();
		this.sensorHorizontal.reset();
		shape.deactivateCenter();
	}
}
