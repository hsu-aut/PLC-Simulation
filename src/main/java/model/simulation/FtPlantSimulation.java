package model.simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gui.Controller;
import model.elements.ActuatorDefinition;
import model.elements.BinarySensor;
import model.elements.Conveyor;
import model.elements.SensorDefinition;
import model.elements.StorageModule;

/**
 * Main simulation class that has a state machine with the different positions a
 * workpiece can have
 */
public class FtPlantSimulation {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private long startTime;
	private WorkpieceState wpState;
	private OpcUaClient client;
	private int updateInterval;
	private Controller controller;

	private ScheduledFuture<?> future;

	// Elements
	private StorageModule storageModule;
	private Map<ActuatorDefinition, Conveyor> conveyors = new HashMap<ActuatorDefinition, Conveyor>();
	private Map<SensorDefinition, BinarySensor> sensors = new HashMap<SensorDefinition, BinarySensor>();

	protected FtPlantSimulation(OpcUaClient client, int updateInterval) {
		this.wpState = WorkpieceState.AtStorage;
		this.client = client;
		startTime = System.currentTimeMillis();
		this.updateInterval = Math.max(20, updateInterval);
	}

	void setStorageModule(StorageModule storageModule) {
		this.storageModule = storageModule;
	}

	void addSensor(SensorDefinition sensorName, BinarySensor sensor) {
		this.sensors.put(sensorName, sensor);
	}

	public Map<SensorDefinition, BinarySensor> getSensors() {
		return sensors;
	}

	void addConveyor(ActuatorDefinition actuatorName, Conveyor conveyor) {
		this.conveyors.put(actuatorName, conveyor);
	}

	public Map<ActuatorDefinition, Conveyor> getConveyors() {
		return conveyors;
	}

	public OpcUaClient getUaClient() {
		return this.client;
	}

	public int getUpdateInterval() {
		return this.updateInterval;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * Run the simulation
	 */
	public void run() {
		Runnable simulationRunnable = new Runnable() {
			public void run() {
				update();
			}
		};

		// Runs the simulation by creating a new Runnable that is periodically executed
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		this.future = executor.scheduleAtFixedRate(simulationRunnable, 0, this.updateInterval, TimeUnit.MILLISECONDS);
	}

	/**
	 * Stop the current simulation
	 */
	public void stop() {
		this.future.cancel(true);
	}

	/**
	 * Update-method that is periodically called to update the simulation state
	 */
	public void update() {
		System.out.println("Updating simulation");
		long runtime = System.currentTimeMillis() - this.startTime;
		logger.debug("Updating simulation, runtime: " + runtime);

		// Update all conveyor and door states
		for (Conveyor conv : this.conveyors.values()) {
			conv.update();
		}
		
		// Update GUI
		if(controller != null)
			controller.update();
		
		switch (this.wpState) {
		case AtStorage: {
			Conveyor conveyor1 = this.conveyors.get(ActuatorDefinition.B1_A01);
			BinarySensor sensorConveyor1 = this.sensors.get(SensorDefinition.B1_S02);
			if (this.storageModule.isGettingWorkpiece()) {
				logger.info("Simulation update: Getting workpiece from storage");
				this.wpState = WorkpieceState.OnConveyor1;
				// assumption: workpiece gets always placed on first conveyor so that sensor can
				// detect it
				sensorConveyor1.setState(true);
				conveyor1.addWorkpiece();
			}
			break;
		}
		case OnConveyor1: {
			Conveyor conveyor1 = this.conveyors.get(ActuatorDefinition.B1_A01);
			Conveyor conveyor2 = this.conveyors.get(ActuatorDefinition.B1_A02);
			BinarySensor sensorConveyor1 = this.sensors.get(SensorDefinition.B1_S02);

			// Set sensor to false if workpiece moves out of the detection area
			if (conveyor1.getRelativeWorkpiecePosition() > 60) {
				sensorConveyor1.setState(false);
			}

			// Push workpiece to conveyor 2
			if (conveyor1.getRelativeWorkpiecePosition() == 100 && conveyor1.isOn() && conveyor2.isOn()) {
				conveyor1.removeWorkpiece();
				conveyor2.addWorkpiece();
				this.wpState = WorkpieceState.OnConveyor2;
				logger.info("Simulation update: Moving workpiece to conveyor 2");
			}
			break;
		}
		case OnConveyor2: {
			Conveyor conveyor2 = this.conveyors.get(ActuatorDefinition.B1_A02);
			Conveyor conveyor3 = this.conveyors.get(ActuatorDefinition.B1_A07);
			BinarySensor sensorConveyor2 = this.sensors.get(SensorDefinition.B1_S03);

			// switch on sensor if workpiece gets in detection area
			if (conveyor2.getRelativeWorkpiecePosition() > 40 && conveyor2.getRelativeWorkpiecePosition() < 75) {
				sensorConveyor2.setState(true);
			} else {
				sensorConveyor2.setState(false);
			}

			// Push workpiece to conveyor 3
			if (conveyor2.getRelativeWorkpiecePosition() == 100 && conveyor2.isOn() && conveyor3.isOn()) {
				conveyor2.removeWorkpiece();
				conveyor3.addWorkpiece();
				this.wpState = WorkpieceState.OnConveyor3;
				logger.info("Simulation update: Moving workpiece to conveyor 3");
			}
			break;
		}
		case OnConveyor3: {
			Conveyor conveyor3 = this.conveyors.get(ActuatorDefinition.B1_A07);
			Conveyor conveyor4 = this.conveyors.get(ActuatorDefinition.B1_A08);
			BinarySensor sensorConveyor3 = this.sensors.get(SensorDefinition.B1_S07);

			// switch on sensor if workpiece gets in detection area
			if (conveyor3.getRelativeWorkpiecePosition() > 40 && conveyor3.getRelativeWorkpiecePosition() < 75) {
				sensorConveyor3.setState(true);
			} else {
				sensorConveyor3.setState(false);
			}

			// Push workpiece to conveyor 4
			if (conveyor3.getRelativeWorkpiecePosition() == 100 && conveyor3.isOn() && conveyor4.isOn()) {
				conveyor3.removeWorkpiece();
				conveyor4.addWorkpiece();
				this.wpState = WorkpieceState.BeginConveyor4;
				logger.info("Simulation update: Moving workpiece to conveyor 4");
			}
			break;
		}
		case BeginConveyor4:

			break;
		case FrontOfGate:

			break;
		case BehindGate:

			break;
		case EndConveyor4:

			break;

		}
	}

}
