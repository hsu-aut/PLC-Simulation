package gui.elements;

import gui.Controller;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import model.elements.Conveyor;

public class GUIElement {

	private Conveyor logic;
	private BooleanProperty workpiecePresent = new SimpleBooleanProperty(false);

	public GUIElement(Shape shape, Conveyor logic, Controller controller) {
		this.logic = logic;
		
		workpiecePresent.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(logic.workpieceIsPresent()) {
					shape.setFill(Color.web("#95cc88"));
				}else {
					shape.setFill(Color.web("#dcdcdc"));
				}
			}
		});
	}

	public void update() {
		workpiecePresent.set(logic.workpieceIsPresent());
	}

}
