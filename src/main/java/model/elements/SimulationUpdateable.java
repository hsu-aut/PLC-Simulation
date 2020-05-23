package model.elements;

public abstract class SimulationUpdateable {

	SimulationElementName simulationElementName;
	
	public abstract void update();
	
	public SimulationElementName getSimulationElementName() {
		return this.simulationElementName;
	}
}
