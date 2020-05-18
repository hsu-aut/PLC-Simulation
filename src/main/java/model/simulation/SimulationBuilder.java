package model.simulation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

import model.UaClientUtil;
import model.elements.ActuatorDefinition;
import model.elements.BinaryActuator;
import model.elements.BinarySensor;
import model.elements.Conveyor;
import model.elements.SensorDefinition;
import model.elements.StorageModule;

/**
 * Builder class to help setting up a simulation
 */
public class SimulationBuilder {

	private static Map<String, BinarySensor> sensors = new HashMap<>();
	private static Map<String, BinaryActuator> motors = new HashMap<>();
	private final static CompletableFuture<OpcUaClient> future = new CompletableFuture<>();
	
	private FtPlantSimulation simulation;
	
	public FtPlantSimulation build(String opcUaServerEndpointUrl, int updateInterval) throws Exception {
		OpcUaClient client = UaClientUtil.createClient(opcUaServerEndpointUrl);
		client.connect().get();
		this.simulation = new FtPlantSimulation(client, updateInterval);
		addSimulationElements();
		return simulation;
	}
	
	private void addSimulationElements() {
		// Setup all sensors
		for(SensorDefinition sensorName: SensorDefinition.values()) {
			this.simulation.addSensor(sensorName, new BinarySensor(sensorName, simulation));
		}
		
		// Setup all conveyors
		for(ActuatorDefinition conveyorDef: ActuatorDefinition.getConveyors()) {
			this.simulation.addConveyor(conveyorDef, new Conveyor(conveyorDef, simulation, 150));
		}
		
		// Set storage module
		this.simulation.setStorageModule(new StorageModule());
	}
}
