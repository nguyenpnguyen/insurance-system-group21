package org.group21.insurance.views;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginScreen extends Application {
    private TextField nameInput;
    private PasswordField passInput;
    private Button loginButton;


    @Override
    public void start(Stage stage) {
        Scene loginScene = setLoginScreenUI(stage);
        stage.setScene(loginScene);
        stage.setTitle("Insurance Claim Management System - Authentication");
        stage.show();
    }

    public Scene setLoginScreenUI(Stage stage) {
        nameInput = new TextField();
        passInput = new PasswordField();
        loginButton = new Button("Login");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.add(new Label("Insurance Claim Management System"), 0, 0, 2, 1);
        gridPane.add(new Label("Username:"), 0, 1);
        gridPane.add(nameInput, 1, 1);
        gridPane.add(new Label("Password:"), 0, 2);
        gridPane.add(passInput, 1, 2);
        gridPane.add(loginButton, 1, 3);

        loginButton.setOnAction(e -> handleLogin(stage));

        return new Scene(gridPane, 400, 300);
    }

    private void handleLogin(Stage stage) {
        String username = nameInput.getText();
        String password = passInput.getText();
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Error: Username and password must not be empty.");
        } else {
            // Handle login logic here
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);

            stage.close();
            DashboardScreen dashboardScreen = new DashboardScreen();
            dashboardScreen.start(stage);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
