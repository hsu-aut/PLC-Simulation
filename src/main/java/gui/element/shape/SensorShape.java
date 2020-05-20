package gui.element.shape;

import gui.element.Direction;
import gui.element.ShapeHelper;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SensorShape {

	private Rectangle rec;

	public SensorShape(Pane pane, double posX, double posY, Direction direction, String lblText) {
		switch (direction) {
		case North: {
			Rectangle rectangle = new Rectangle(posX - 5, posY, 10, 20);
			rectangle.setFill(ShapeHelper.SENSOR_FRONT);
			rectangle.setStroke(Color.BLACK);
			pane.getChildren().add(rectangle);

			rec = new Rectangle(posX - 10, posY + 20, 20, 20);
			rec.setFill(ShapeHelper.INACTIVE);
			rec.setStroke(Color.BLACK);
			pane.getChildren().add(rec);

			Label lbl = new Label(lblText);
			lbl.setLayoutX(posX - 20);
			lbl.setLayoutY(posY + 45);
			lbl.setStyle("-fx-font-weight: bold");
			pane.getChildren().add(lbl);
			break;
		}
		case West: {

			break;
		}
		case South: {
			Rectangle rectangle = new Rectangle(posX - 5, posY - 20, 10, 20);
			rectangle.setFill(ShapeHelper.SENSOR_FRONT);
			rectangle.setStroke(Color.BLACK);
			pane.getChildren().add(rectangle);

			rec = new Rectangle(posX - 10, posY - 40, 20, 20);
			rec.setFill(ShapeHelper.INACTIVE);
			rec.setStroke(Color.BLACK);
			pane.getChildren().add(rec);

			Label lbl = new Label(lblText);
			lbl.setLayoutX(posX - 20);
			lbl.setLayoutY(posY - 60);
			lbl.setStyle("-fx-font-weight: bold");
			pane.getChildren().add(lbl);
			break;
		}
		case East: {
			Rectangle rectangle = new Rectangle(posX, posY - 5, 20, 10);
			rectangle.setFill(ShapeHelper.SENSOR_FRONT);
			rectangle.setStroke(Color.BLACK);
			pane.getChildren().add(rectangle);

			rec = new Rectangle(posX + 20, posY - 10, 20, 20);
			rec.setFill(ShapeHelper.INACTIVE);
			rec.setStroke(Color.BLACK);
			pane.getChildren().add(rec);

			Label lbl = new Label(lblText);
			lbl.setLayoutX(posX + 45);
			lbl.setLayoutY(posY - 10);
			lbl.setStyle("-fx-font-weight: bold");
			pane.getChildren().add(lbl);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + direction);
		}
	}

	public void activateSensor() {
		rec.setFill(ShapeHelper.ACTIVE);
	}

	public void deactivateSensor() {
		rec.setFill(ShapeHelper.INACTIVE);
	}

}
