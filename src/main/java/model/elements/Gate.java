package model.elements;

public class Gate {
	
	private GateDoor leftDoor, rightDoor;
	
	
	public Gate(GateDoor leftDoor, GateDoor rightDoor) {
		this.leftDoor = leftDoor;
		this.rightDoor = rightDoor;
	}
	
	
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
