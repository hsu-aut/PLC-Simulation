package gui;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class PreferencesController implements Initializable {

	private Properties prop = new Properties();
	private Controller controller;
	private Stage stage;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	@FXML
	private void btnApply() {
		
	}
	
	@FXML
	private void btnCancel() {
		this.stage.close();
	}
	
	public void setStage(Stage stage, Controller controller) {
		this.stage = stage;
		this.controller = controller;
	}

}
