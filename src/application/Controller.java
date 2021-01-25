package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Controller implements Initializable {
	@FXML
	private WebView webView;
	@FXML
	private TextField addressBar;
	@FXML
	private HBox hBox;
	private WebEngine engine;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		engine = webView.getEngine();
		engine.getLoadWorker().stateProperty().addListener((obserable, oldState, newState) -> {
			if (newState == Worker.State.SUCCEEDED) {
				addressBar.setText(engine.getLocation());
			}
		});
	}

	public void keyHandle(KeyEvent key) {
		if (key.getCode().equals(KeyCode.ENTER)) {
			String address = addressBar.getText();
			if (!address.contains("http://")) {
				address = "http://" + address;
				engine.load(address);
			} else if (!address.contains("https://")) {
				address = "https://" + address;
				engine.load(address);
			} else {
				engine.load("https://google.com/");
			}

		}
	}

	public void backHandle(ActionEvent event) {
		if (engine.getHistory().getCurrentIndex() > 0) {

			engine.getHistory().go(-1);

		}
	}

	public void forwardHandle(ActionEvent event) {
		if (engine.getHistory().getCurrentIndex() + 1 < engine.getHistory().getEntries().size()) {

			engine.getHistory().go(1);

		}
	}

}
