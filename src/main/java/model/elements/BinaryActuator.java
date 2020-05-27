package model.elements;

import java.util.concurrent.ExecutionException;

import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;

import model.UaClientUtil;
import model.simulation.FtPlantSimulation;

/**
 * Represents a simple binary actuator that can be switched on and off by a PLC Is directly connected to an output variable of the OPC UA server / the
 * PLC
 */
public class BinaryActuator extends BinaryPlantElement {

	private ActuatorDefinition actuatorDef;
	private boolean on;

	public BinaryActuator(ActuatorDefinition actuatorDef, FtPlantSimulation simulation) {
		super(actuatorDef.name(), actuatorDef.getNodeIdString(), actuatorDef.getComment(), simulation);
		this.actuatorDef = actuatorDef;
		if (actuatorDef != ActuatorDefinition.NULL) {
			try {
				UaClientUtil.setupSubscription(simulation.getUaClient(), this);
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public ActuatorDefinition getActuatorName () {
		return this.actuatorDef;
	}

	/**
	 * Checks whether or not the actuator is currently on
	 * 
	 * @return True, if on. False otherwise
	 */
	public boolean isOn() {
		return this.on;
	}

	/**
	 * Method that is called whenever the value of the corresponding actuator changes.
	 * 
	 * @param item      The monitored item
	 * @param dataValue The current value of the actuator
	 */
	public void onValueChanged(UaMonitoredItem item, DataValue dataValue) {
		logger.info("subscription value received: Actuator= {}, item={}, value={}", this.getTagName(), item.getReadValueId().getNodeId(),
				dataValue.getValue());
		boolean value = (boolean) dataValue.getValue().getValue();

		if (value) {
			this.on = true;
		} else {
			this.on = false;
		}

	}

}
