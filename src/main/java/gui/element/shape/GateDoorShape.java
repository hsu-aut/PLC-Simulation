package gui.element.shape;

import gui.element.Direction;
import gui.element.ShapeHelper;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GateDoorShape {

	private Rectangle rec, recOpen, recClosed;

	public GateDoorShape(Pane pane, double posX, double posY, Direction direction, String lblText) {
		// direction to close the gate
		switch (direction) {
		case North: {
			recOpen = new Rectangle(posX - 5, posY + 90, 10, 10);
			recOpen.setFill(ShapeHelper.INACTIVE);
			recOpen.setStroke(Color.BLACK);
			pane.getChildren().add(recOpen);
					
			rec = new Rectangle(posX - 5, posY + 10, 10, 80);
			rec.setFill(ShapeHelper.INACTIVE);
			rec.setStroke(Color.BLACK);
			pane.getChildren().add(rec);
			
			recClosed = new Rectangle(posX - 5, posY, 10, 10);
			recClosed.setFill(ShapeHelper.INACTIVE);
			recClosed.setStroke(Color.BLACK);
			pane.getChildren().add(recClosed);
			
			break;
		}
		case West: {

			break;
		}
		case South: {
			recOpen = new Rectangle(posX - 5, posY - 100, 10, 10);
			recOpen.setFill(ShapeHelper.INACTIVE);
			recOpen.setStroke(Color.BLACK);
			pane.getChildren().add(recOpen);
					
			rec = new Rectangle(posX - 5, posY - 90, 10, 80);
			rec.setFill(ShapeHelper.INACTIVE);
			rec.setStroke(Color.BLACK);
			pane.getChildren().add(rec);
			
			recClosed = new Rectangle(posX - 5, posY - 10, 10, 10);
			recClosed.setFill(ShapeHelper.INACTIVE);
			recClosed.setStroke(Color.BLACK);
			pane.getChildren().add(recClosed);
			break;
		}
		case East: {

			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + direction);
		}
	}
	
	public void doorIsOpen() {
		rec.setFill(ShapeHelper.GATE_OPEN);
		recOpen.setFill(ShapeHelper.ACTIVE);
		recClosed.setFill(ShapeHelper.INACTIVE);
	}
	
	public void doorIsClosed() {
		rec.setFill(ShapeHelper.GATE_CLOSED);
		recOpen.setFill(ShapeHelper.INACTIVE);
		recClosed.setFill(ShapeHelper.ACTIVE);
	}
	
	public void doorIsMoving() {
		rec.setFill(ShapeHelper.ACTIVE);
		recOpen.setFill(ShapeHelper.INACTIVE);
		recClosed.setFill(ShapeHelper.INACTIVE);
	}
	
	public void doorNotMoving() {
		rec.setFill(ShapeHelper.INACTIVE);
	}

}
