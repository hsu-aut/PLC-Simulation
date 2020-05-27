package model.simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gui.Controller;
import gui.element.shape.ShapeHelper;
import javafx.scene.paint.Color;
import model.elements.BinarySensor;
import model.elements.Conveyor;
import model.elements.Gate;
import model.elements.SensorDefinition;
import model.elements.SimulationElementName;
import model.elements.SimulationUpdateable;
import model.elements.StorageModule;
import model.elements.Turntable;

/**
 * Main simulation class that has a state machine with the different positions a workpiece can have
 */
public class FtPlantSimulation {

	private long startTime;
	private WorkpieceState wpState;
	private OpcUaClient client;
	private int updateInterval;
	private Controller controller;

	private ScheduledFuture<?> future;
	private boolean running = false;
	private boolean colorCodeSent = false; // Flag whether or sensors were switched on / off for color detection
	private int numberOfMagnetsRight = 0;
	private int numberOfMagnetsLeft = 0;

	// Elements
	private StorageModule storageModule;
	private Map<SimulationElementName, SimulationUpdateable> updateables = new HashMap<SimulationElementName, SimulationUpdateable>();
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

	void addUpdateable(SimulationUpdateable updateable) {
		this.updateables.put(updateable.getSimulationElementName(), updateable);
	}

	public Map<SimulationElementName, SimulationUpdateable> getConveyors() {
		return updateables;
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
		this.running = true;
	}

	/**
	 * Stop the current simulation
	 */
	public void stop() {
		this.future.cancel(true);
		this.running = false;
	}

	public void reset() {
		this.colorCodeSent = false;
		this.wpState = WorkpieceState.AtStorage;
		for (BinarySensor sensor : this.sensors.values()) {
			sensor.reset();
		}
		for (SimulationUpdateable updateable : this.updateables.values()) {
			updateable.reset();
		}
	}

	public boolean isRunning() {
		return this.running;
	}

	/**
	 * Update-method that is periodically called to update the simulation state
	 */
	public void update() {
		long runtime = System.currentTimeMillis() - this.startTime;

		// Update all conveyor and door states
		for (SimulationUpdateable updateable : this.updateables.values()) {
			updateable.update();
		}

		switch (this.wpState) {
		case AtStorage: {
			setRandomWorkpieceColor();
			Conveyor conveyor1 = (Conveyor) this.updateables.get(SimulationElementName.Conveyor1);
			BinarySensor sensorConveyor1 = this.sensors.get(SensorDefinition.B1_S02);
			if (this.storageModule.isGettingWorkpiece()) {
				this.controller.log("Simulation update: Getting workpiece from storage");
				this.wpState = WorkpieceState.OnConveyor1;
				// assumption: workpiece gets always placed on first conveyor so that sensor can detect it
				sensorConveyor1.activate();
				conveyor1.addWorkpiece(true);
			}
			break;
		}
		case OnConveyor1: {
			Conveyor conveyor1 = (Conveyor) this.updateables.get(SimulationElementName.Conveyor1);
			Conveyor conveyor2 = (Conveyor) this.updateables.get(SimulationElementName.Conveyor2);
			BinarySensor sensorConveyor1 = this.sensors.get(SensorDefinition.B1_S02);

			// Set sensor to false if workpiece moves out of the detection area
			if (conveyor1.getRelativeWorkpiecePosition() < 60) {
				sensorConveyor1.deactivate();
			}

			if (conveyor1.getRelativeWorkpiecePosition() < 20 && !colorCodeSent) {
				triggerColorSensors();
			}

			// Push workpiece to conveyor 2
			if (conveyor1.getRelativeWorkpiecePosition() == 0 && conveyor1.getMotorLeft().isOn() && conveyor2.getMotorLeft().isOn()) {
				conveyor1.removeWorkpiece();
				conveyor2.addWorkpiece(true);
				this.wpState = WorkpieceState.OnConveyor2;
				this.controller.log("Simulation update: Moving workpiece to conveyor 2");
			}
			break;
		}
		case OnConveyor2: {
			Conveyor conveyor2 = (Conveyor) this.updateables.get(SimulationElementName.Conveyor2);
			Conveyor conveyor3 = (Conveyor) this.updateables.get(SimulationElementName.Conveyor3);
			BinarySensor sensorConveyor2 = this.sensors.get(SensorDefinition.B1_S03);

			// switch on sensor if workpiece gets in detection area
			if (conveyor2.getRelativeWorkpiecePosition() < 60 && conveyor2.getRelativeWorkpiecePosition() > 40) {
				sensorConveyor2.activate();
			} else {
				sensorConveyor2.deactivate();
			}

			// Push workpiece to conveyor 3
			if (conveyor2.getRelativeWorkpiecePosition() == 0 && conveyor2.getMotorLeft().isOn() && conveyor3.getMotorLeft().isOn()) {
				conveyor2.removeWorkpiece();
				conveyor3.addWorkpiece(true);
				this.wpState = WorkpieceState.OnConveyor3;
				this.controller.log("Simulation update: Moving workpiece to conveyor 3");
			}
			break;
		}
		case OnConveyor3: {
			Conveyor conveyor3 = (Conveyor) this.updateables.get(SimulationElementName.Conveyor3);
			Conveyor conveyor4 = (Conveyor) this.updateables.get(SimulationElementName.Conveyor4);
			BinarySensor sensorConveyor3 = this.sensors.get(SensorDefinition.B1_S07);

			// switch on sensor if workpiece gets in detection area
			if (conveyor3.getRelativeWorkpiecePosition() < 60 && conveyor3.getRelativeWorkpiecePosition() > 40) {
				sensorConveyor3.activate();
			} else {
				sensorConveyor3.deactivate();
			}

			// Push workpiece to conveyor 4
			if (conveyor3.getRelativeWorkpiecePosition() == 0 && conveyor3.getMotorLeft().isOn() && conveyor4.getMotorLeft().isOn()) {
				conveyor3.removeWorkpiece();
				conveyor4.addWorkpiece(true);
				this.wpState = WorkpieceState.BeginConveyor4;
				this.controller.log("Simulation update: Moving workpiece to conveyor 4");
			}
			break;
		}
		case BeginConveyor4: {
			Conveyor conveyor4 = (Conveyor) this.updateables.get(SimulationElementName.Conveyor4);
			BinarySensor b1_s08 = this.sensors.get(SensorDefinition.B1_S08);
			BinarySensor b1_s16 = this.sensors.get(SensorDefinition.B1_S16);
			Gate gate = (Gate) this.updateables.get(SimulationElementName.Gate);

			// switch on sensor if workpiece gets in detection area
			if (conveyor4.getRelativeWorkpiecePosition() <92 && conveyor4.getRelativeWorkpiecePosition() > 85) {
				b1_s08.activate();
			} else {
				b1_s08.deactivate();
			}

			if (conveyor4.getRelativeWorkpiecePosition() < 65 && conveyor4.getRelativeWorkpiecePosition() > 55) {
				b1_s16.activate();
			} else {
				b1_s16.deactivate();
			}

			if (conveyor4.getRelativeWorkpiecePosition() <= 50 && conveyor4.getMotorLeft().isOn()) {

				if (gate.isOpen()) {
					this.controller.log("Simulation update: Transporting Workpiece trough the gate");
					conveyor4.unblockWorkpiece();
					this.wpState = WorkpieceState.BehindGate;
				} else {
					this.controller.warn("The workpiece is crashing into the gate!");
					conveyor4.blockWorkpiece();
				}
			}

			break;
		}
		case BehindGate: {
			Conveyor conveyor4 = (Conveyor) this.updateables.get(SimulationElementName.Conveyor4);
			Turntable turntable = (Turntable) this.updateables.get(SimulationElementName.Turntable);
			Conveyor conveyorOnTurntable = turntable.getConveyor();
			BinarySensor b1_s17 = this.sensors.get(SensorDefinition.B1_S17);
			BinarySensor b1_s09 = this.sensors.get(SensorDefinition.B1_S09);

			if (conveyor4.getRelativeWorkpiecePosition() < 40 && conveyor4.getRelativeWorkpiecePosition() > 30) {
				b1_s17.activate();
			} else {
				b1_s17.deactivate();
			}

			if (conveyor4.getRelativeWorkpiecePosition() < 12) {
				b1_s09.activate();
			}

			if (conveyor4.getMotorLeft().isOn() && conveyor4.getRelativeWorkpiecePosition() == 0) {

				if (turntable.isVertical()) {
					this.controller.warn("Workpiece is crashing into the Turntable! The turntable is turned vertically.");
				}

				if (turntable.isHorizontal() && turntable.getConveyor().getMotorLeft().isOn()) {
					this.controller.log("Simulation update: Moving workpiece onto the turntable");
					this.wpState = WorkpieceState.OnTurntable;
					conveyor4.removeWorkpiece();
					conveyorOnTurntable.addWorkpiece(true);
				}
			}

			break;
		}
		case OnTurntable: {
			Turntable turntable = (Turntable) this.updateables.get(SimulationElementName.Turntable);
			Conveyor conveyorOnTurntable = turntable.getConveyor();
			Conveyor conveyorLeft = (Conveyor) this.updateables.get(SimulationElementName.ConveyorLeft);
			Conveyor conveyorTop = (Conveyor) this.updateables.get(SimulationElementName.ConveyorTop);

			BinarySensor b1_s20 = this.sensors.get(SensorDefinition.B1_S20);

			if (conveyorOnTurntable.getRelativeWorkpiecePosition() < 55 && conveyorOnTurntable.getRelativeWorkpiecePosition() > 40) {
				b1_s20.activate();
			} else {
				b1_s20.deactivate();
			}

			if (conveyorOnTurntable.getRelativeWorkpiecePosition() == 0 && conveyorOnTurntable.getMotorLeft().isOn()) {

				if (turntable.isHorizontal() && conveyorLeft.getMotorLeft().isOn()) {
					this.controller.log("Simulation update: Moving workpiece to left conveyor");
					this.wpState = WorkpieceState.OnLeftConveyor;
					conveyorOnTurntable.removeWorkpiece();
					conveyorLeft.addWorkpiece(true);
				}
				if (turntable.isVertical() && conveyorLeft.getMotorLeft().isOn()) {
					this.controller.log("Moving workpiece to upper conveyor");
					this.wpState = WorkpieceState.OnUpperConveyor;
					conveyorOnTurntable.removeWorkpiece();
					conveyorTop.addWorkpiece(true);
				}
			}

			break;
		}
		case OnLeftConveyor: {
			Conveyor conveyorLeft = (Conveyor) this.updateables.get(SimulationElementName.ConveyorLeft);
			BinarySensor b1_s23 = this.sensors.get(SensorDefinition.B1_S23);

			if (conveyorLeft.getRelativeWorkpiecePosition() < 55 && conveyorLeft.getRelativeWorkpiecePosition() > 40) {
				b1_s23.activate();
			} else {
				b1_s23.deactivate();
			}
			break;
		}
		case OnUpperConveyor: {
			Conveyor conveyorTop = (Conveyor) this.updateables.get(SimulationElementName.ConveyorTop);
			BinarySensor b1_s24 = this.sensors.get(SensorDefinition.B1_S24);

			if (conveyorTop.getRelativeWorkpiecePosition() < 55 && conveyorTop.getRelativeWorkpiecePosition() > 40) {
				b1_s24.activate();
			} else {
				b1_s24.deactivate();
			}
			break;
		}
		}
	}

	private void setRandomWorkpieceColor() {
		double random = Math.random();
		if (random <= 0.25) {
			ShapeHelper.WORKPIECE_COLOR = Color.YELLOW;
			numberOfMagnetsLeft = 1;
			numberOfMagnetsRight = 0;
		}
		if (random > 0.25 && random <= 0.5) {
			ShapeHelper.WORKPIECE_COLOR = Color.RED;
			numberOfMagnetsLeft = 1;
			numberOfMagnetsRight = 1;
		}
		if (random > 0.5 && random <= 0.75) {
			ShapeHelper.WORKPIECE_COLOR = Color.GREEN;
			numberOfMagnetsLeft = 2;
			numberOfMagnetsRight = 1;
		}
		if (random > 0.75 && random <= 1) {
			ShapeHelper.WORKPIECE_COLOR = Color.BLUE;
			numberOfMagnetsLeft = 2;
			numberOfMagnetsRight = 2;
		}
	}

	
	private void triggerColorSensors() {
		
		this.colorCodeSent = true;
		
		BinarySensor sensorRight = this.sensors.get(SensorDefinition.B1_S05);
		BinarySensor sensorLeft = this.sensors.get(SensorDefinition.B1_S04);
		
		SensorTrigger sensorRightTrigger = new SensorTrigger(sensorRight, numberOfMagnetsRight, 200);
		SensorTrigger sensorLeftTrigger = new SensorTrigger(sensorLeft, numberOfMagnetsLeft, 200);
		
		ExecutorService executorRight = Executors.newCachedThreadPool();
		executorRight.submit(sensorRightTrigger);
		executorRight.submit(sensorLeftTrigger);
		executorRight.shutdown();
	}
	

}
