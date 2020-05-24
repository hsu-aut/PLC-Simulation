package model.elements;

public abstract class SimulationUpdateable {

	SimulationElementName simulationElementName;

	
	public SimulationElementName getSimulationElementName() {
		return this.simulationElementName;
	}
	
	public abstract void update();
	
	public abstract void reset();
}
