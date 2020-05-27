package gui.element.shape;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GateDoorShape {

	private Rectangle rec;

	public GateDoorShape(Pane pane, double posX, double posY, String lblText) {
		rec = new Rectangle(posX, posY, 10, 80);
		rec.setFill(ShapeHelper.INACTIVE);
		rec.setStroke(Color.BLACK);
		pane.getChildren().add(rec);

	}

	public void doorIsMoving() {
		rec.setFill(ShapeHelper.ACTIVE);
	}

	public void doorNotMoving() {
		rec.setFill(ShapeHelper.INACTIVE);
	}

}
