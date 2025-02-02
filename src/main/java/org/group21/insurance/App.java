package org.group21.insurance;

import javafx.application.Application;
import javafx.stage.Stage;
import org.group21.insurance.data.DataSeeder;
import org.group21.insurance.utils.EntityManagerFactorySingleton;
import org.group21.insurance.views.LoginScreen;

/**
 * JavaFX App
 *
 * @author Group 21
 */
public class App extends Application {
	
	@Override
	public void start(Stage stage) {
		LoginScreen loginScreen = new LoginScreen();
		loginScreen.start(stage);
		
	}
	
	public static void main(String[] args) {
		
		// Close EntityManagerFactory when the application is closed
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			EntityManagerFactorySingleton.close();
			System.out.println("Shutting down...");
		}));
		
		// Uncomment the following line before submission
		DataSeeder.seedData();
		
		
		launch(args);
	}
}