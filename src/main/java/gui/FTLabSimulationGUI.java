package gui;

import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FTLabSimulationGUI extends Application {

	private final static Logger logger = LoggerFactory.getLogger(FTLabSimulationGUI.class);
	
	@Override
	public void start(Stage stage) throws IOException {
		PropertyConfigurator.configure(this.getClass().getClassLoader().getResourceAsStream("config/log4j.properties"));
		
		logger.info("Java Version " + System.getProperty("java.version"));		
		logger.info("JavaFX Version " + System.getProperty("javafx.version"));

		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/window.fxml"));
		Parent root = loader.load();
		Controller controller = (Controller) loader.getController();
		controller.setStage(stage);
		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setTitle("FT Lab Simulation");
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
	
}