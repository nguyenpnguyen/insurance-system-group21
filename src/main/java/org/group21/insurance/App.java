package org.group21.insurance;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

  @Override
  public void start(Stage stage) {
    var javaVersion = SystemInfo.javaVersion();
    var javafxVersion = SystemInfo.javafxVersion();

//    var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
//    var scene = new Scene(new StackPane(label), 1000, 800);
//    stage.setTitle("Insurance Claim Management System");
    stage.setScene(new LoginScreen().setLoginScreenUI(stage));
    stage.show();
  }

  public static void main(String[] args) {
		launch();
	}
}