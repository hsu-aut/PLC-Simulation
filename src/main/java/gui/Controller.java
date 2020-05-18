package gui;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import gui.elements.ConveyorElement;
import gui.elements.ConveyorShape;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.elements.ActuatorDefinition;
import model.simulation.FtPlantSimulation;
import model.simulation.SimulationBuilder;

public class Controller implements Initializable {

	@FXML
	private Button btnStart;
	@FXML
	private TextArea tAConsole;
	@FXML
	private Rectangle con1;
	@FXML
	private Rectangle con2;
	@FXML
	private Rectangle con3;
	@FXML
	private Rectangle con4;
	@FXML 
	private Pane pane;

	private Stage primaryStage;
	private Properties properties = new Properties();
	private FtPlantSimulation simulation;
	private List<ConveyorElement> conveyors = new ArrayList<ConveyorElement>();

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
		if(btnStart.getText().equals("Start")) {
			btnStart.setText("Stop");
			logConsole("Start Simulation");
			simulation.run();	
		}else {
			btnStart.setText("Start");
			logConsole("Stop Simulation");
			simulation.stop();
		}
	}
	
	public void logConsole(String msg) {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		tAConsole.appendText("[" + df.format(date) + "] " + msg + "\n");
	}

	public void setStage(Stage stage) {
		this.primaryStage = stage;
		
		try {
			simulation = new SimulationBuilder().build(properties.getProperty("opcUaServerEndpointUrl"), Integer.valueOf(properties.getProperty("updateInterval")));
			simulation.setController(this);
			
			// Binding Logic and GUI-Elements
//			new ConveyorShape(pane, 220, 50, true);
			ConveyorElement element = new ConveyorElement(new ConveyorShape(pane, 220, 50, true), simulation.getConveyors().get(ActuatorDefinition.B1_A01), this, simulation.getConveyors().get(ActuatorDefinition.B1_A01), null);
			conveyors.add(element);
			element = new ConveyorElement(new ConveyorShape(pane, 50, 50, true), simulation.getConveyors().get(ActuatorDefinition.B1_A02), this, simulation.getConveyors().get(ActuatorDefinition.B1_A02), null);
			conveyors.add(element);
			
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
	
	public void updateConveyors() {
		for (ConveyorElement conveyor : conveyors) {
			conveyor.update();
		}
	}

}