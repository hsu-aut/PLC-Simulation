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
			simulation = new SimulationBuilder(this, pane).build(properties.getProperty("opcUaServerEndpointUrl"),
					Integer.valueOf(properties.getProperty("updateInterval")));
			simulation.setController(this);

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
		if(!simulation.isRunning()) {
			simulation.reset();
		} else {
			this.logConsole("The simulation has to be stopped before it can be resetted.");
		}
	}

}