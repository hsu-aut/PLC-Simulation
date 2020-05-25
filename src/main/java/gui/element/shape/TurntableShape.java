package gui.element.shape;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TurntableShape {

	private SensorShape shapeCenter;
	private Pane pane, conveyorPane;
	private Circle circle;

	public TurntableShape(Pane pane, int posX, int posY, String lblCenter) {
		this.pane = pane;
		int radius = 80; 	// should later be passed in
		circle = new Circle(posX + radius, posY + radius, radius);
		circle.setFill(ShapeHelper.INACTIVE);
		circle.setStroke(Color.BLACK);
		pane.getChildren().add(circle);
	}

	public void conVertical() {
		this.conveyorPane.setRotate(90);
	}

	public void conHorizontal() {
		this.conveyorPane.setRotate(0);
	}

	
	public void addConveyorPane(Pane conveyorPane) {
		this.conveyorPane = conveyorPane;
		this.pane.getChildren().add(conveyorPane);
	}
	
	public void activateCenter() {
		circle.setFill(ShapeHelper.ACTIVE);
	}

	public void deactivateCenter() {
		circle.setFill(ShapeHelper.INACTIVE);
	}

}
