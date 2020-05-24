package gui.element;
//
//import gui.Controller;
//import gui.element.shape.GateDoorShape;
//import javafx.beans.property.BooleanProperty;
//import javafx.beans.property.SimpleBooleanProperty;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import model.elements.BinarySensor;
//
public class GateElement { }
//
//	private GateDoorShape shape;
//	private BinarySensor openSensor, closeSensor;
//	private BooleanProperty stateOpenSensor = new SimpleBooleanProperty(false);
//	private BooleanProperty stateCloseSensor = new SimpleBooleanProperty(false);
//
//	public GateElement(GateDoorShape shape, Controller controller, BinarySensor openSensor, BinarySensor closeSensor) {
//		this.shape = shape;
//		this.openSensor = openSensor;
//		this.closeSensor = closeSensor;
//		
//		stateOpenSensor.addListener(new ChangeListener<Boolean>() {
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				if (newValue) {
//					shape.gateIsOpen();
//				} else {
//					shape.gateIsMoving();
//				}
//			}
//		});
//
//		stateCloseSensor.addListener(new ChangeListener<Boolean>() {
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				if (newValue) {
//					shape.gateIsClosed();
//				} else {
//					shape.gateIsMoving();
//				}
//			}
//		});
//	}
//
//	@Override
//	public void update() {
//		if (openSensor != null)
//			stateOpenSensor.set(openSensor.getState());
//		if (closeSensor != null)
//			stateCloseSensor.set(closeSensor.getState());
//	}
//
//	@Override
//	public void reset() {
//		shape.gateIsMoving();
//	}
//
//}
