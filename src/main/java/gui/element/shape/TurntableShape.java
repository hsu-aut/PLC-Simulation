package gui.element.shape;

import gui.element.Direction;
import gui.element.ShapeHelper;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TurntableShape {

	private SensorShape shapeTop, shapeLeft, shapeCenter;
	private Circle cirC, cirCS, cirH1, cirH2, cirH3, cirH4, cirV1, cirV2, cirV3, cirV4;

	public TurntableShape(Pane pane, double posX, double posY, String lblTop, String lblLeft, String lblCenter) {
		Circle circle = new Circle(posX, posY, 80);
		circle.setFill(ShapeHelper.INACTIVE);
		circle.setStroke(Color.BLACK);
		pane.getChildren().add(circle);

		shapeTop = new SensorShape(pane, posX + 40, posY - 80, Direction.East, lblTop);
		shapeLeft = new SensorShape(pane, posX - 80, posY + 40, Direction.North, lblLeft);

		cirCS = new Circle(posX, posY, 15);
		cirCS.setFill(ShapeHelper.INACTIVE);
		cirCS.setStroke(Color.BLACK);

		cirC = new Circle(posX, posY, 10);
		cirC.setFill(ShapeHelper.INACTIVE);
		cirC.setStroke(Color.BLACK);

		cirH1 = new Circle(posX + 60, posY, 10);
		cirH1.setFill(ShapeHelper.INACTIVE);
		cirH1.setStroke(Color.BLACK);

		cirH2 = new Circle(posX + 30, posY, 10);
		cirH2.setFill(ShapeHelper.INACTIVE);
		cirH2.setStroke(Color.BLACK);

		cirH3 = new Circle(posX - 30, posY, 10);
		cirH3.setFill(ShapeHelper.INACTIVE);
		cirH3.setStroke(Color.BLACK);

		cirH4 = new Circle(posX - 60, posY, 10);
		cirH4.setFill(ShapeHelper.INACTIVE);
		cirH4.setStroke(Color.BLACK);

		cirV1 = new Circle(posX, posY + 60, 10);
		cirV1.setFill(ShapeHelper.INACTIVE);
		cirV1.setStroke(ShapeHelper.INACTIVE);

		cirV2 = new Circle(posX, posY + 30, 10);
		cirV2.setFill(ShapeHelper.INACTIVE);
		cirV2.setStroke(ShapeHelper.INACTIVE);

		cirV3 = new Circle(posX, posY - 30, 10);
		cirV3.setFill(ShapeHelper.INACTIVE);
		cirV3.setStroke(ShapeHelper.INACTIVE);

		cirV4 = new Circle(posX, posY - 60, 10);
		cirV4.setFill(ShapeHelper.INACTIVE);
		cirV4.setStroke(ShapeHelper.INACTIVE);

		pane.getChildren().addAll(cirCS, cirC, cirH1, cirH2, cirH3, cirH4, cirV1, cirV2, cirV3, cirV4);
	}

	public void conVertical() {
		cirH1.setStroke(ShapeHelper.INACTIVE);
		cirH2.setStroke(ShapeHelper.INACTIVE);
		cirH3.setStroke(ShapeHelper.INACTIVE);
		cirH4.setStroke(ShapeHelper.INACTIVE);
		cirV1.setStroke(Color.BLACK);
		cirV2.setStroke(Color.BLACK);
		cirV3.setStroke(Color.BLACK);
		cirV4.setStroke(Color.BLACK);
	}

	public void conHorizontal() {
		cirH1.setStroke(Color.BLACK);
		cirH2.setStroke(Color.BLACK);
		cirH3.setStroke(Color.BLACK);
		cirH4.setStroke(Color.BLACK);
		cirV1.setStroke(ShapeHelper.INACTIVE);
		cirV2.setStroke(ShapeHelper.INACTIVE);
		cirV3.setStroke(ShapeHelper.INACTIVE);
		cirV4.setStroke(ShapeHelper.INACTIVE);
	}

	public SensorShape getShapeTop() {
		return shapeTop;
	}

	public SensorShape getShapeLeft() {
		return shapeLeft;
	}

	public void activateCenter() {
		cirCS.setFill(ShapeHelper.ACTIVE);
	}

	public void deactivateCenter() {
		cirCS.setFill(ShapeHelper.INACTIVE);
	}

}
