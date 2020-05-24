package gui;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import gui.element.ConveyorElement;
import gui.element.Direction;
import gui.element.GateElement;
import gui.element.IGUI;
import gui.element.SensorElement;
import gui.element.SwitchElement;
import gui.element.TurntableElement;
import gui.element.shape.ConveyorShape;
import gui.element.shape.GateDoorShape;
import gui.element.shape.SensorShape;
import gui.element.shape.SwitchShape;
import gui.element.shape.TurntableShape;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.elements.ActuatorDefinition;
import model.elements.BinarySensor;
import model.elements.SensorDefinition;
import model.simulation.FtPlantSimulation;
import model.simulation.SimulationBuilder;

public class Controller implements Initializable {

	@FXML
	private Button btnStart;
	@FXML
	private TextArea tAConsole;
	@FXML
	private Pane pane;

//	private Stage primaryStage;
	private Properties properties = new Properties();
	private FtPlantSimulation simulation;
	private boolean isON = false;
	private List<IGUI> guiElements = new ArrayList<IGUI>();

	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			properties.load(this.getClass().getClassLoader().getResourceAsStream("config/config.properties"));
			System.out.println(properties);
		} catch (IOException e) {
			e.printStackTrace();
			logConsole(e.getMessage());
		}
	}

	@FXML
	private void btnStart_oA() {
		if (btnStart.getText().equals("Start")) {
			btnStart.setText("Stop");
			logConsole("Start Simulation");
			simulation.run();
			isON = true;
		} else {
			btnStart.setText("Start");
			logConsole("Stop Simulation");
			simulation.stop();
			isON = false;
		}
	}
	
	@FXML
	private void btnReset_oA() {
		reset();
	}

	public void logConsole(String msg) {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		tAConsole.appendText("[" + df.format(date) + "] " + msg + "\n");
	}

	public void setStage(Stage stage) {
//		this.primaryStage = stage;

		try {
			simulation = new SimulationBuilder().build(properties.getProperty("opcUaServerEndpointUrl"),
					Integer.valueOf(properties.getProperty("updateInterval")), pane);
			simulation.setController(this);

			// Switch
			SensorDefinition switchSensor = SensorDefinition.B1_S06;
			IGUI element = new SwitchElement(new SwitchShape(pane, switchSensor.getX(), switchSensor.getY(),switchSensor.getTagName()), simulation.getSensors().get(switchSensor), this);
			guiElements.add(element);

			// Sensor
//			element = new SensorElement(new SensorShape(pane, 1010, 310, Direction.North, "B1_S02"),
//					simulation.getSensors().get(SensorDefinition.B1_S02), this);
//			guiElements.add(element);
//
//			element = new SensorElement(new SensorShape(pane, 780, 230, Direction.South, "B1_S03"),
//					simulation.getSensors().get(SensorDefinition.B1_S03), this);
//			guiElements.add(element);
//
//			element = new SensorElement(new SensorShape(pane, 610, 310, Direction.North, "B1_S07"),
//					simulation.getSensors().get(SensorDefinition.B1_S07), this);
//			guiElements.add(element);
//
//			element = new SensorElement(new SensorShape(pane, 500, 310, Direction.North, "B1_S08"),
//					simulation.getSensors().get(SensorDefinition.B1_S08), this);
//			guiElements.add(element);
//
//			element = new SensorElement(new SensorShape(pane, 470, 230, Direction.South, "B1_S16"),
//					simulation.getSensors().get(SensorDefinition.B1_S16), this);
//			guiElements.add(element);
//
//			element = new SensorElement(new SensorShape(pane, 410, 230, Direction.South, "B1_S17"),
//					simulation.getSensors().get(SensorDefinition.B1_S17), this);
//			guiElements.add(element);
//
//			element = new SensorElement(new SensorShape(pane, 380, 310, Direction.North, "B1_S09"),
//					simulation.getSensors().get(SensorDefinition.B1_S09), this);
//			guiElements.add(element);

//			// TODO S21
//			element = new SensorElement(new SensorShape(pane, 310, 190, Direction.East, "B1_S21"),
//					simulation.getSensors().get(SensorDefinition.B1_S09), this);
//			guiElements.add(element);
//
//			// TODO S22
//			element = new SensorElement(new SensorShape(pane, 190, 310, Direction.North, "B1_S22"),
//					simulation.getSensors().get(SensorDefinition.B1_S09), this);
//			guiElements.add(element);

			// TODO S23
//			element = new SensorElement(new SensorShape(pane, 100, 230, Direction.South, "B1_S23"),
//					simulation.getSensors().get(SensorDefinition.B1_S09), this);
//			guiElements.add(element);
//
//			// TODO S24
//			element = new SensorElement(new SensorShape(pane, 310, 100, Direction.East, "B1_S24"),
//					simulation.getSensors().get(SensorDefinition.B1_S09), this);
//			guiElements.add(element);

//			// Gate
//			// TODO 
//			element = new GateElement(new GateDoorShape(pane, 440, 230, Direction.South, "Gate1"), this,
//					simulation.getSensors().get(SensorDefinition.B1_S02),
//					simulation.getSensors().get(SensorDefinition.B1_S03));
//			guiElements.add(element);
//
//			element = new GateElement(new GateDoorShape(pane, 440, 310, Direction.North, "Gate2"), this,
//					simulation.getSensors().get(SensorDefinition.B1_S02),
//					simulation.getSensors().get(SensorDefinition.B1_S03));
//			guiElements.add(element);

			// Turntable
			// TODO S20, S21, S22
			element = new TurntableElement(new TurntableShape(pane, 270, 270, "B1_S21", "B1_S22", "B1_S20"), this,
					simulation.getSensors().get(SensorDefinition.B1_S02),
					simulation.getSensors().get(SensorDefinition.B1_S03),
					simulation.getSensors().get(SensorDefinition.B1_S07));
			guiElements.add(element);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Properties getProp() {
		return properties;
	}

	public void setProp(Properties prop) {
		this.properties = prop;
	}

	public boolean isON() {
		return isON;
	}

	public void update() {
		for (IGUI element : guiElements) {
			element.update();
		}
	}
	
	public void reset() {
		if(btnStart.getText().equals("Start")) {
			for (IGUI element : guiElements) {
				element.reset();
			}
		}
	}

}