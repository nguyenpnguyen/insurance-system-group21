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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;

public class LoginScreen extends Application {
    private TextField nameInput;
    private PasswordField passInput;
    private Button loginButton;

    private Label errorLabel;
    private ComboBox<String> roleComboBox;


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
        errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        roleComboBox = new ComboBox<>();

        roleComboBox.getItems().addAll("Dependent", "Policy holder", "Policy owner", "Insurance surveyor", "Insurance manager", "System admin"); // Add roles as needed
        roleComboBox.setPromptText("Select Role");

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
        gridPane.add(new Label("Role:"), 0, 3);
        gridPane.add(roleComboBox, 1, 3);
        gridPane.add(loginButton, 1, 4);
        gridPane.add(errorLabel, 0, 5, 2, 1);

        loginButton.setOnAction(e -> handleLogin(stage, roleComboBox.getValue()));

        return new Scene(gridPane, 400, 300);
    }

    private void handleLogin(Stage stage, String role) {
        String username = nameInput.getText();
        String password = passInput.getText();
//        boolean error = false;

        nameInput.setStyle("");
        passInput.setStyle("");
        roleComboBox.setStyle("");
        errorLabel.setText("");


        if (username.isEmpty()) {
            nameInput.setStyle("-fx-border-color: red;");
            errorLabel.setText("Error: Username must not be empty.");
//            error = true;
        }

        if (password.isEmpty()) {
            passInput.setStyle("-fx-border-color: red;");
            errorLabel.setText("Error: Password must not be empty.");
//            error = true;
        }

        if (role == null || role.isEmpty()) {
            roleComboBox.setStyle("-fx-border-color: red;");
            errorLabel.setText("Error: Role must be selected.");

        }


        if (username.isEmpty() || password.isEmpty() || role == null || role.isEmpty()) {

        } else {
            // TODO: Handle login logic here with the databse
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);

            stage.close();
            DashboardScreen dashboardScreen = new DashboardScreen(role);
            dashboardScreen.start(stage);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
