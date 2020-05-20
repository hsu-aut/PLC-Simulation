package gui.element.shape;

import gui.element.ShapeHelper;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

public class SwitchShape {

	private Rectangle rec;

	public SwitchShape(Pane pane, double posX, double posY, String lblText) {
		rec = new Rectangle(posX - 10, posY - 20, 20, 20);
		rec.setFill(ShapeHelper.INACTIVE);
		rec.setStroke(Color.BLACK);
		pane.getChildren().add(rec);

		Polyline poly = new Polyline();
		poly.getPoints().addAll(new Double[] { posX - 2.5, posY - 20, posX, posY - 40, posX + 5, posY - 40, posX + 2.5,
				posY - 20, posX - 2.5, posY - 20 });
		pane.getChildren().add(poly);

		Label lbl = new Label(lblText);
		lbl.setLayoutX(posX - 20);
		lbl.setLayoutY(posY - 60);
		lbl.setStyle("-fx-font-weight: bold");
		pane.getChildren().add(lbl);
	}

	public void activateSwitch() {
		rec.setFill(ShapeHelper.ACTIVE);
	}

	public void deactivateSwitch() {
		rec.setFill(ShapeHelper.INACTIVE);
	}

	public Rectangle getRec() {
		return rec;
	}

}
