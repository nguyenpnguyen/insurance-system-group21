package org.group21.insurance.Views;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DashboardScreen extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Create sidebar
        VBox sidebar = new VBox();
        sidebar.setStyle("-fx-background-color: #ffffff; -fx-border-color: grey; -fx-border-width: 0 2 0 0; -fx-padding: 0; -fx-min-width: 150px;");

        Button section1 = createSectionButton("Dashboard");
        Button section2 = createSectionButton("Customer");
        Button section3 = createSectionButton("Claim");
        Button section4 = createSectionButton("Insurance Card");

        sidebar.getChildren().addAll(section1, section2, section3, section4);

        section1.setOnAction(e -> root.setCenter(new Label("Content for Dashboard")));
        section2.setOnAction(e -> root.setCenter(new Label("Content for Customer")));
        section3.setOnAction(e -> root.setCenter(new Label("Content for Claim")));
        section4.setOnAction(e -> root.setCenter(new Label("Content for Insurance Card")));

        DropShadow dsSidebar = new DropShadow();
        dsSidebar.setOffsetY(3.0);
        dsSidebar.setColor(Color.color(0.4, 0.4, 0.4));
        sidebar.setEffect(dsSidebar);
        root.setLeft(sidebar);

        // Create the header
        HBox header = new HBox();
        header.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");

        Label welcomeText = new Label("Welcome Administrator");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Button logoutButton = new Button("Logout");

        logoutButton.setOnAction(e -> {
            LoginScreen loginScreen = new LoginScreen();
            try {
                loginScreen.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            primaryStage.close();
        });



        header.getChildren().addAll(welcomeText, spacer, logoutButton);

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0);
        ds.setColor(Color.color(0.4, 0.4, 0.4));
        header.setEffect(ds);

        root.setTop(header);



        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createSectionButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE); // Make the button take up all available horizontal space
        button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: #000000; -fx-font-size: 15px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #dddddd; -fx-border-color: transparent; -fx-text-fill: #000000; -fx-font-size: 15px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: #000000; -fx-font-size: 15px;"));
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}