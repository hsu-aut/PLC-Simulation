package model.elements;

import java.util.concurrent.ExecutionException;

import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.UaClientUtil;
import model.simulation.FtPlantSimulation;

/**
 * A basic class defining common attributes and methods of all elements of the FT plant
 */
public class PlantElement {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String tagName;
	protected NodeId nodeId;
	private String comment;
	protected FtPlantSimulation simulation;
	
	public PlantElement(String tagName, String nodeIdString, String comment, FtPlantSimulation simulation) {
		this.tagName = tagName;
		this.nodeId = NodeId.parse(nodeIdString);
		this.comment = comment;
		this.simulation = simulation;
	}
	
	public NodeId getNodeId() {
		return this.nodeId;
	}
	
	public String getTagName() {
		return this.tagName;
	}
	
	public String getComment() {
		return this.comment;
	}
	
	/**
	 * Can be used to write a value to the corresponding OPC UA Variable (all variables are read / write)
	 * @param value Value to write to the corresponding node
	 * @return
	 */
	public boolean writeBooleanNode(boolean value) {
		try {
			return UaClientUtil.writeBooleanNode(simulation.getUaClient(), this.nodeId, value);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
