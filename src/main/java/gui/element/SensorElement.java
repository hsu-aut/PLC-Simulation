package gui.element;
//
//import gui.Controller;
//import gui.element.shape.SensorShape;
//import javafx.beans.property.BooleanProperty;
//import javafx.beans.property.SimpleBooleanProperty;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import model.elements.BinarySensor;
//
public class SensorElement {

}
//
//	private SensorShape shape;
//	private BinarySensor logic;
//	private BooleanProperty state = new SimpleBooleanProperty(false);
//
//	public SensorElement(SensorShape shape, BinarySensor logic, Controller controller) {
//		this.shape = shape;
//		this.logic = logic;
//		
//		state.addListener(new ChangeListener<Boolean>() {
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				if (newValue) {
//					shape.activateSensor();
//				} else {
//					shape.deactivateSensor();
//				}
//			}
//		});
//	}
//
//	@Override
//	public void update() {
//		if (logic != null)
//			state.set(logic.getState());
//	}
//
//	@Override
//	public void reset() {
//		shape.deactivateSensor();
//	}
//
//}
