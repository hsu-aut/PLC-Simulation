package model.elements;

import model.simulation.FtPlantSimulation;

public abstract class SimulationUpdateable {

	SimulationElementName simulationElementName;
	FtPlantSimulation simulation;
	
	public SimulationElementName getSimulationElementName() {
		return this.simulationElementName;
	}
	
	public abstract void update();
	
	public abstract void reset();
}
