package org.group21.insurance;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginScreen extends Application {
    public Scene setLoginScreenUI(Stage stage ){
        stage.setTitle("Insurance Claim Management System - Login");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Label titleLabel = new Label("Insurance Claim Management System");
        titleLabel.setStyle("-fx-font-size: 24; -fx-text-fill: blue;"); // Set color and size for the title
        grid.add(titleLabel, 0, 0, 2, 1); // Add the title to the grid
        GridPane.setHalignment(titleLabel, HPos.CENTER);
        GridPane.setValignment(titleLabel, VPos.CENTER);

        Label nameLabel = new Label("Username:");
        TextField nameInput = new TextField();
        nameInput.setPrefWidth(300); // Increase the width of the TextField
        Label passLabel = new Label("Password:");
        PasswordField passInput = new PasswordField();
        passInput.setPrefWidth(300); // Increase the width of the PasswordField

        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(300); // Increase the width of the Button
        loginButton.setOnAction(e -> {
            // Handle login logic here
            System.out.println("Username: " + nameInput.getText());
            System.out.println("Password: " + passInput.getText());
        });

        grid.add(nameLabel, 0, 1);
        grid.add(nameInput, 1, 1);
        grid.add(passLabel, 0, 2);
        grid.add(passInput, 1, 2);
        grid.add(loginButton, 1, 3);

        GridPane.setHalignment(nameLabel, HPos.CENTER);
        GridPane.setValignment(nameLabel, VPos.CENTER);
        GridPane.setHalignment(nameInput, HPos.CENTER);
        GridPane.setValignment(nameInput, VPos.CENTER);
        GridPane.setHalignment(passLabel, HPos.CENTER);
        GridPane.setValignment(passLabel, VPos.CENTER);
        GridPane.setHalignment(passInput, HPos.CENTER);
        GridPane.setValignment(passInput, VPos.CENTER);
        GridPane.setHalignment(loginButton, HPos.CENTER);
        GridPane.setValignment(loginButton, VPos.CENTER);

        Scene scene = new Scene(grid, 1000, 800);
        return scene;
    }

    @Override
    public void start(Stage stage) {
        stage.setScene(setLoginScreenUI(stage));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}