package model.elements;

public class Gate extends SimulationUpdateable {
	
	private GateDoor leftDoor, rightDoor;
	private SimulationElementName elementName;
	
	public Gate(SimulationElementName elementName, GateDoor leftDoor, GateDoor rightDoor) {
		this.elementName = elementName;
		this.leftDoor = leftDoor;
		this.rightDoor = rightDoor;
	}
	
	@Override
	public void update() {
		this.leftDoor.update();
		this.rightDoor.update();
	}
	
	
	int getLeftDoorPosition() {
		return this.leftDoor.getPosition();
	}
	
	int getRightDoorPosition() {
		return this.rightDoor.getPosition();
	}
	
	boolean isOpen() {
		return (this.leftDoor.isOpen() && this.rightDoor.isOpen());
	}

}
