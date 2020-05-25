package gui.element.shape;

import javafx.scene.shape.Rectangle;

public abstract class BinaryShape {

	Rectangle rec;
	
	public abstract void activateShape();
	public abstract void deactivateShape();


	public Rectangle getRec() {
		return rec;
	}
}
