package org.group21.insurance;

import org.group21.insurance.views.LoginScreen;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

  @Override
  public void start(Stage stage) {
      LoginScreen loginScreen = new LoginScreen();
      loginScreen.start(stage);

  }

  public static void main(String[] args) {
		launch(args);
	}
}