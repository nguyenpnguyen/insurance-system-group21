package org.group21.insurance.Views;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardScreen extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        VBox sidebar = new VBox();
        Button section1 = new Button("Section 1");
        Button section2 = new Button("Section 2");
        Button section3 = new Button("Section 3");
        Button section4 = new Button("Section 4");
        Button section5 = new Button("Section 5");
        sidebar.getChildren().addAll(section1, section2, section3, section4, section5);

        section1.setOnAction(e -> root.setCenter(new Label("Content for Section 1")));
        section2.setOnAction(e -> root.setCenter(new Label("Content for Section 2")));
        section3.setOnAction(e -> root.setCenter(new Label("Content for Section 3")));
        section4.setOnAction(e -> root.setCenter(new Label("Content for Section 4")));
        section5.setOnAction(e -> root.setCenter(new Label("Content for Section 5")));

        root.setLeft(sidebar);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}