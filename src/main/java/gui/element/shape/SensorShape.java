package gui.element.shape;

import gui.element.Direction;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SensorShape extends BinaryShape {

	public SensorShape(Pane pane, double posX, double posY, Direction direction, String lblText) {
		Rectangle rectangle;
		Label lbl = new Label(lblText);
		
		switch (direction) {
		case North: {
			rectangle = new Rectangle(posX - 5, posY, 10, 20);
			rec = new Rectangle(posX - 10, posY + 20, 20, 20);

			lbl.setLayoutX(posX - 20);
			lbl.setLayoutY(posY + 45);
			break;
		}
		case West: {
			rectangle = new Rectangle(posX+20, posY - 5, 20, 10);
			rec = new Rectangle(posX + 40, posY - 10, 20, 20);

			lbl.setLayoutX(posX + 70);
			lbl.setLayoutY(posY - 10);
			break;
		}
		case South: {
			rectangle = new Rectangle(posX - 5, posY - 20, 10, 20);
			rec = new Rectangle(posX - 10, posY - 40, 20, 20);

			lbl.setLayoutX(posX - 20);
			lbl.setLayoutY(posY - 60);
			break;
		}
		case East: {
			rectangle = new Rectangle(posX, posY - 5, 20, 10);
			rec = new Rectangle(posX + 20, posY - 10, 20, 20);

			lbl.setLayoutX(posX + 45);
			lbl.setLayoutY(posY - 10);
			break;
		}
		case None: {
			rectangle = new Rectangle(posX, posY, 10, 10);
			rec = new Rectangle(posX, posY, 10, 10);
			lbl.setLayoutX(posX + 20);
			lbl.setLayoutY(posY - 5);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + direction);
		}
		
		rectangle.setFill(ShapeHelper.SENSOR_FRONT);
		rectangle.setStroke(Color.BLACK);
		pane.getChildren().add(rectangle);
		rec.setFill(ShapeHelper.INACTIVE);
		rec.setStroke(Color.BLACK);
		pane.getChildren().add(rec);
		lbl.setStyle("-fx-font-weight: bold");
		pane.getChildren().add(lbl);
	}

	@Override
	public void activateShape() {
		rec.setFill(ShapeHelper.ACTIVE);
	}

	@Override
	public void deactivateShape() {
		rec.setFill(ShapeHelper.INACTIVE);
	}

}
