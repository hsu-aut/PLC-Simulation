package model.elements;

import gui.element.shape.BinaryShape;
import gui.element.shape.SensorShape;
import model.simulation.FtPlantSimulation;

/**
 * Represents a simple binary sensor that is read by a PLC and that can be switched on and off by the simulation.
 * Is directly connected to an input variable of the OPC UA server / the PLC
 */
public class BinarySensor extends BinaryPlantElement {
	
	private boolean state;
	BinaryShape shape;
	
	public BinarySensor(SensorDefinition sensorName, BinaryShape shape, FtPlantSimulation simulation) {
		super(sensorName.name(), sensorName.getNodeIdString(), sensorName.getComment(), simulation);
		this.shape = shape;
	}
	
	/**
	 * Returns the current state of this sensor
	 * @return
	 */
	public boolean getState() {
		return this.state;
	}
	
	public void activate() {
		this.setState(true);
		this.shape.activateShape();
	}
	
	public void deactivate() {
		this.setState(false);
		this.shape.deactivateShape();
	}
	
	
	
	/**
	 * Can be used to change the state of the corresponding variable on the OPC UA server
	 * @param newState
	 */
	private void setState(boolean newState) {
		// Write only if newState is different from old
		if (newState != this.state) {
			logger.info("Writing new state {} to sensor {}", newState, this.getTagName());
			boolean success = super.writeBooleanNode(newState);
			
			if (success) {
				this.state = newState;
			}
		}
	}
	
	public void reset() {
		System.out.println("resetting sensors");
		this.deactivate();
	}

}
