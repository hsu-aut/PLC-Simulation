package model.elements;

import gui.Controller;
import gui.element.shape.BinaryShape;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.simulation.FtPlantSimulation;

public class BinarySwitch extends BinarySensor {

	public BinarySwitch(SensorDefinition sensorName, BinaryShape shape, FtPlantSimulation simulation, Controller controller) {
		super(sensorName, shape, simulation);
		shape.getRec().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (controller.isON()) {
					if (getState()) {
						deactivate();
						shape.deactivateShape();
					} else {
						activate();
						shape.activateShape();
					}
				}
			}
		});
	}

}
