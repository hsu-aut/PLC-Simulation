package gui.elements;

import gui.Controller;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.elements.BinaryActuator;
import model.elements.Conveyor;

public class ConveyorElement {

	private Conveyor logic;
//	private BinaryActuator left, right;
	
	private BooleanProperty workpiecePresent = new SimpleBooleanProperty(false);
	private FloatProperty workpiecePosition = new SimpleFloatProperty();
	
	private BooleanProperty leftActuator = new SimpleBooleanProperty(false);
	private BooleanProperty rightActuator = new SimpleBooleanProperty(false);

	public ConveyorElement(ConveyorShape shape, Conveyor logic, Controller controller, BinaryActuator left, BinaryActuator right) {
		this.logic = logic;
//		this.left = left;
//		this.right = right;
		
		workpiecePresent.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == false) {
					shape.deactivate();
				}
			}
		});
		
		workpiecePosition.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(logic.workpieceIsPresent()) {
					shape.setPosition(workpiecePosition.get());
				}
			}
		});
		
//		leftActuator.addListener(new ChangeListener<Boolean>() {
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				if(newValue == true) {
//					shape.activateLeft();
//				}else {
//					shape.deactivateLeft();
//				}
//			}
//		});
//		
//		rightActuator.addListener(new ChangeListener<Boolean>() {
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				if(newValue == true) {
//					shape.activateRight();
//				}else {
//					shape.deactivateRight();
//				}
//			}
//		});
	}

	public void update() {
		System.out.println("Updating GUI");
		workpiecePresent.set(logic.workpieceIsPresent());
		workpiecePosition.set(logic.getRelativeWorkpiecePosition());
//		leftActuator.set(left.isOn());
//		rightActuator.set(right.isOn());
	}

}