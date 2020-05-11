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

import gui.elements.GUIElement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.elements.ActuatorDefinition;
import model.simulation.FtPlantSimulation;
import model.simulation.SimulationBuilder;

public class Controller implements Initializable {

	@FXML
	private MenuItem mIPreferences;
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

	private Stage primaryStage;
	private Properties properties = new Properties();
	private FtPlantSimulation simulation;
	private List<GUIElement> conveyors = new ArrayList<GUIElement>();

	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			properties.load(new FileInputStream(this.getClass().getResource("config.properties").getPath()));
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
	
	@FXML
	private void btnClose_oA() {
		primaryStage.close();
	}
	
	@FXML
	private void mIPreferencesAction() {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/preferences.fxml"));
			Parent root = loader.load();
			PreferencesController controller = (PreferencesController) loader.getController();
			controller.setStage(stage, this);

			Scene scene = new Scene(root);
			stage.setTitle("Preferences");
			stage.setScene(scene);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(primaryStage);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
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

			//Manuelles Mapping
			GUIElement guiElement = new GUIElement(con1, simulation.getConveyors().get(ActuatorDefinition.B1_A01), this);
			conveyors.add(guiElement);
			guiElement = new GUIElement(con2, simulation.getConveyors().get(ActuatorDefinition.B1_A02), this);
			conveyors.add(guiElement);
			guiElement = new GUIElement(con3, simulation.getConveyors().get(ActuatorDefinition.B1_A07), this);
			conveyors.add(guiElement);
			guiElement = new GUIElement(con1, simulation.getConveyors().get(ActuatorDefinition.B1_A08), this);
			conveyors.add(guiElement);
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
		for (GUIElement guiElement : conveyors) {
			guiElement.update();
		}
	}

}
