package model.simulation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

import gui.element.Direction;
import gui.element.shape.ConveyorShape;
import gui.element.shape.GateDoorShape;
import gui.element.shape.SensorShape;
import javafx.scene.layout.Pane;
import model.UaClientUtil;
import model.elements.ActuatorDefinition;
import model.elements.BinaryActuator;
import model.elements.BinarySensor;
import model.elements.Conveyor;
import model.elements.Gate;
import model.elements.GateDoor;
import model.elements.SensorDefinition;
import model.elements.SimulationElementName;
import model.elements.StorageModule;

/**
 * Builder class to help setting up a simulation
 */
public class SimulationBuilder {

	private final static CompletableFuture<OpcUaClient> future = new CompletableFuture<>();

	private FtPlantSimulation simulation;
	private Pane pane;

	public FtPlantSimulation build(String opcUaServerEndpointUrl, int updateInterval, Pane pane) throws Exception {
		OpcUaClient client = UaClientUtil.createClient(opcUaServerEndpointUrl);
		client.connect().get();
		this.pane = pane;
		this.simulation = new FtPlantSimulation(client, updateInterval);
		addSimulationElements();
		return simulation;
	}

	private void addSimulationElements() {
		// Setup all sensors
		for (SensorDefinition sensorName : SensorDefinition.values()) {
			this.simulation.addSensor(sensorName, new BinarySensor(sensorName, new SensorShape(pane, sensorName.getX(), sensorName.getY(), sensorName.getDirection(), sensorName.getTagName()), simulation));
		}

		// Set storage module
		this.simulation.setStorageModule(new StorageModule());

		// Setup all conveyors
		Conveyor conveyor1 = new Conveyor(SimulationElementName.Conveyor1, new ConveyorShape(pane, 870, 235, true), ActuatorDefinition.B1_A01,
				ActuatorDefinition.NULL, simulation, 150);
		this.simulation.addUpdateable(conveyor1);

		Conveyor conveyor2 = new Conveyor(SimulationElementName.Conveyor2, new ConveyorShape(pane, 700, 235, true), ActuatorDefinition.B1_A02,
				ActuatorDefinition.NULL, simulation, 150);
		this.simulation.addUpdateable(conveyor2);

		Conveyor conveyor3 = new Conveyor(SimulationElementName.Conveyor3, new ConveyorShape(pane, 530, 235, true), ActuatorDefinition.B1_A07,
				ActuatorDefinition.NULL, simulation, 150);
		this.simulation.addUpdateable(conveyor3);

		Conveyor conveyor4 = new Conveyor(SimulationElementName.Conveyor4, new ConveyorShape(pane, 360, 235, true), ActuatorDefinition.B1_A08,
				ActuatorDefinition.NULL, simulation, 150);
		this.simulation.addUpdateable(conveyor4);

		Conveyor conveyor5 = new Conveyor(SimulationElementName.Conveyor5, new ConveyorShape(pane, 20, 235, true), ActuatorDefinition.B1_A23,
				ActuatorDefinition.NULL, simulation, 150);
		this.simulation.addUpdateable(conveyor5);

		Conveyor conveyor6 = new Conveyor(SimulationElementName.Conveyor6, new ConveyorShape(pane, 235, 20, false), ActuatorDefinition.B1_A24,
				ActuatorDefinition.NULL, simulation, 150);
		this.simulation.addUpdateable(conveyor6);

		// setup Gate
		BinarySensor leftDoorOpenSensor = new BinarySensor(SensorDefinition.B1_S12, new SensorShape(pane, 440, 310, Direction.West, "B1_S12"), simulation);
		BinarySensor leftDoorClosedSensor = new BinarySensor(SensorDefinition.B1_S13, new SensorShape(pane, 440, 400, Direction.West, "B1S13"), simulation);
		GateDoor leftDoor = new GateDoor(simulation, new GateDoorShape(pane, 440, 310, Direction.North, "Left Gate Door"), ActuatorDefinition.B1_A16,
				ActuatorDefinition.B1_A15, leftDoorOpenSensor, leftDoorClosedSensor, 100);
		
		BinarySensor rightDoorOpenSensor = new BinarySensor(SensorDefinition.B1_S10, new SensorShape(pane, 440, 230, Direction.West, "B1_S10"), simulation);
		BinarySensor rightDoorClosedSensor = new BinarySensor(SensorDefinition.B1_S11, new SensorShape(pane, 440, 180, Direction.West, "B1_S11"), simulation);
		GateDoor rightDoor = new GateDoor(simulation, new GateDoorShape(pane, 440, 230, Direction.South, "Right Gate Door"), ActuatorDefinition.B1_A14,
				ActuatorDefinition.B1_A13, rightDoorOpenSensor, rightDoorClosedSensor, 100);
		
		Gate gate = new Gate(SimulationElementName.Gate, leftDoor, rightDoor);
		this.simulation.addUpdateable(gate);

	}
}
