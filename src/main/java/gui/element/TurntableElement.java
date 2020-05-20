package gui.element;

import gui.Controller;
import gui.element.shape.TurntableShape;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.elements.BinarySensor;

public class TurntableElement implements IGUI {

	private TurntableShape shape;
	private BinarySensor sensorLeft, sensorTop, sensorCenter;
	
	private BooleanProperty stateLeft = new SimpleBooleanProperty(false);
	private BooleanProperty stateTop = new SimpleBooleanProperty(false);
	private BooleanProperty stateCenter = new SimpleBooleanProperty(false);
	
	public TurntableElement(TurntableShape shape, Controller controller, BinarySensor sensorTop, BinarySensor sensorLeft, BinarySensor sensorCenter) {
		this.shape = shape;
		this.sensorLeft = sensorLeft;
		this.sensorTop = sensorTop;
		this.sensorCenter = sensorCenter;
		
		stateLeft.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					shape.conHorizontal();
					shape.getShapeLeft().activateSensor();
				} else {
					shape.getShapeLeft().deactivateSensor();
				}
			}
		});
		
		stateTop.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					shape.conVertical();
					shape.getShapeTop().activateSensor();
				} else {
					shape.getShapeTop().deactivateSensor();
				}
			}
		});
		
		stateCenter.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					shape.activateCenter();
				} else {
					shape.deactivateCenter();
				}
			}
		});
	}
	
	@Override
	public void update() {
		if (sensorLeft != null)
			stateLeft.set(sensorLeft.getState());
		if (sensorTop != null)
			stateTop.set(sensorTop.getState());
		if (sensorCenter != null)
			stateCenter.set(sensorCenter.getState());
	}

	@Override
	public void reset() {
		shape.conHorizontal();
		shape.deactivateCenter();
		shape.getShapeTop().deactivateSensor();
		shape.getShapeLeft().deactivateSensor();
	}

}
