package gui;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import model.simulation.FtPlantSimulation;
import model.simulation.SimulationBuilder;

public class Controller implements Initializable {

	@FXML
	private Button btnStart;
	@FXML
	private TextFlow tAConsole;
	@FXML
	private Pane pane;

//	private Stage primaryStage;
	private Properties properties = new Properties();
	private FtPlantSimulation simulation;
	private boolean isON = false;

	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			properties.load(this.getClass().getClassLoader().getResourceAsStream("config/config.properties"));
			System.out.println(properties);
		} catch (IOException e) {
			e.printStackTrace();
			log(e.getMessage());
		}
	}

	@FXML
	private void btnStart_oA() {
		if (btnStart.getText().equals("Start")) {
			btnStart.setText("Stop");
			log("Start Simulation");
			simulation.run();
			isON = true;
		} else {
			btnStart.setText("Start");
			log("Stop Simulation");
			simulation.stop();
			isON = false;
		}
	}
	

	@FXML
	private void btnReset_oA() {
		reset();
	}

	private void printMessage(String type, String msg, Color color) {
		
		Platform.runLater(() -> {
			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String completeMsg = "[" + df.format(date) + "] " + type + " : " + msg + "\n";
			
			Text text = new Text(completeMsg);
			text.setFill(color);
			tAConsole.getChildren().add(text);
		});
		
	}

	
	public void log(String msg) {
		this.printMessage("INFO", msg, Color.BLACK);
	}

	
	public void warn(String msg) {
		this.printMessage("WARNING", msg, Color.RED);
	}

	
	public void setStage(Stage stage) {
//		this.primaryStage = stage;

		try {
			simulation = new SimulationBuilder(this).build(properties.getProperty("opcUaServerEndpointUrl"),
					Integer.valueOf(properties.getProperty("updateInterval")));

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

	public Pane getPane() {
		return this.pane;
	}
	
	public void reset() {
		if (!simulation.isRunning()) {
			simulation.reset();
		} else {
			this.log("The simulation has to be stopped before it can be resetted.");
		}
	}

}