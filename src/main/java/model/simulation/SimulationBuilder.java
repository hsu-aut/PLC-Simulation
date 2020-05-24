package model.simulation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

import gui.element.shape.ConveyorShape;
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
		for(SensorDefinition sensorName: SensorDefinition.values()) {
			this.simulation.addSensor(sensorName, new BinarySensor(sensorName, simulation));
		}
		
		// Set storage module
		this.simulation.setStorageModule(new StorageModule());
		
		// Setup all conveyors
		Conveyor conveyor1 = new Conveyor(SimulationElementName.Conveyor1, new ConveyorShape(pane, 870, 235, true), ActuatorDefinition.B1_A01, ActuatorDefinition.NULL, simulation, 150);
		this.simulation.addUpdateable(conveyor1);
		
		Conveyor conveyor2 = new Conveyor(SimulationElementName.Conveyor2, new ConveyorShape(pane, 700, 235, true),  ActuatorDefinition.B1_A02, ActuatorDefinition.NULL, simulation, 150);
		this.simulation.addUpdateable(conveyor2);
		
		Conveyor conveyor3 = new Conveyor(SimulationElementName.Conveyor3, new ConveyorShape(pane, 530, 235, true), ActuatorDefinition.B1_A07, ActuatorDefinition.NULL, simulation, 150);
		this.simulation.addUpdateable(conveyor3);
		
		Conveyor conveyor4 = new Conveyor(SimulationElementName.Conveyor4, new ConveyorShape(pane, 360, 235, true), ActuatorDefinition.B1_A08, ActuatorDefinition.NULL, simulation, 150);
		this.simulation.addUpdateable(conveyor4);
		
		Conveyor conveyor5 = new Conveyor(SimulationElementName.Conveyor5, new ConveyorShape(pane, 20, 235, true), ActuatorDefinition.B1_A23, ActuatorDefinition.NULL, simulation, 150);
		this.simulation.addUpdateable(conveyor5);
		
		Conveyor conveyor6 = new Conveyor(SimulationElementName.Conveyor6, new ConveyorShape(pane, 235, 20, false), ActuatorDefinition.B1_A24, ActuatorDefinition.NULL, simulation, 150);
		this.simulation.addUpdateable(conveyor6);
		
		
		
		// setup Gate
		GateDoor leftDoor = new GateDoor(simulation, ActuatorDefinition.B1_A16, ActuatorDefinition.B1_A15, 100);
		GateDoor rightDoor = new GateDoor(simulation, ActuatorDefinition.B1_A14, ActuatorDefinition.B1_A13, 100);
		Gate gate = new Gate(SimulationElementName.Gate, leftDoor, rightDoor);
		this.simulation.addUpdateable(gate);
		
	}
}
