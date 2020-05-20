package gui.element;

import gui.Controller;
import gui.element.shape.SwitchShape;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.elements.BinarySensor;

public class SwitchElement implements IGUI {

	private SwitchShape shape;
	private BinarySensor logic;
	private BooleanProperty state = new SimpleBooleanProperty(false);

	public SwitchElement(SwitchShape shape, BinarySensor logic, Controller controller) {
		this.shape = shape;
		this.logic = logic;
		shape.getRec().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (controller.isON()) {
					if (logic.getState()) {
						logic.setState(false);
						shape.deactivateSwitch();
					} else {
						logic.setState(true);
						shape.activateSwitch();
					}
				}
			}
		});
	}

	@Override
	public void update() {
		if(logic != null)
			state.set(logic.getState());
	}

	@Override
	public void reset() {
		shape.deactivateSwitch();
	}

}
