package gui.element;

import gui.Controller;
import gui.element.shape.ConveyorShape;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.elements.BinaryActuator;
import model.elements.Conveyor;

public class ConveyorElement implements IGUI {

	private Conveyor logic;
	private ConveyorShape shape;
	private BinaryActuator left, right;
	
	private BooleanProperty workpiecePresent = new SimpleBooleanProperty(false);
	private FloatProperty workpiecePosition = new SimpleFloatProperty();
	
	private BooleanProperty leftActuator = new SimpleBooleanProperty(false);
	private BooleanProperty rightActuator = new SimpleBooleanProperty(false);

	public ConveyorElement(ConveyorShape shape, Conveyor logic, Controller controller, BinaryActuator left, BinaryActuator right) {
		this.logic = logic;
		this.shape = shape;
		this.left = left;
		this.right = right;
		
		workpiecePresent.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == false) {
					shape.resetPosition();
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
		
		leftActuator.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == true) {
					shape.activateLeft();
				}else {
					shape.deactivateLeft();
				}
			}
		});
		
		rightActuator.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == true) {
					shape.activateRight();
				}else {
					shape.deactivateRight();
				}
			}
		});
	}

	@Override
	public void update() {
		if(logic != null && logic.workpieceIsPresent()) {
			workpiecePosition.set(logic.getRelativeWorkpiecePosition());			
			workpiecePresent.set(true);
		}else {
			workpiecePresent.set(false);
		}
		if(left != null)
			leftActuator.set(left.isOn());
		if(right != null)
			rightActuator.set(right.isOn());
	}

	@Override
	public void reset() {
		shape.resetPosition();
		shape.deactivateLeft();
		shape.deactivateRight();
	}



}