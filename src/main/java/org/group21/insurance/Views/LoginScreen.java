package org.group21.insurance.Views;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginScreen extends Application {
    @FXML
    private TextField nameInput;

    @FXML
    private PasswordField passInput;

    @FXML
    private Button loginButton;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/group21/insurance/Views/LoginScreen.fxml"));
        loader.setController(this);
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Insurance Claim Management System - Login");
        stage.show();
    }

    @FXML
    private void handleLogin() {
        String username = nameInput.getText();
        String password = passInput.getText();
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Error: Username and password must not be empty.");
        } else {
            // Handle login logic here
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            DashboardScreen dashboardScreen = new DashboardScreen();
            try {
                dashboardScreen.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}