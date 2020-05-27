package model.elements;

public class Gate extends SimulationUpdateable {
	
	private GateDoor leftDoor, rightDoor;
	
	public Gate(SimulationElementName elementName, GateDoor leftDoor, GateDoor rightDoor) {
		this.simulationElementName = elementName;
		this.leftDoor = leftDoor;
		this.rightDoor = rightDoor;
	}
	
	
	int getLeftDoorPosition() {
		return this.leftDoor.getPosition();
	}
	
	int getRightDoorPosition() {
		return this.rightDoor.getPosition();
	}
	
	public boolean isOpen() {
		return (this.leftDoor.isOpen() && this.rightDoor.isOpen());
	}

	
	@Override
	public void update() {
		this.leftDoor.update();
		this.rightDoor.update();
	}
	
	@Override
	public void reset() {
		this.leftDoor.reset();
		this.rightDoor.reset();
	}

}
