package org.group21.insurance.views;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.group21.insurance.Models.Claim;

public class DashboardScreen extends Application {

    @Override
    public void start(Stage stage) {
        Scene dashboardScene = createDashboardUI(stage);
        stage.setTitle("Insurance Claim Dashboard Portal");
        stage.setScene(dashboardScene);
        stage.show();
    }


    private Scene createDashboardUI(Stage stage){
        BorderPane root = new BorderPane();

        // Create sidebar
        root.setLeft(createSidebar(root));

        // Create the header
        root.setTop(createHeader(stage, root));

        return new Scene(root, 1200, 600);
    }
    private VBox createSidebar(BorderPane root){
        VBox sidebar = new VBox();
        sidebar.setStyle("-fx-background-color: #ffffff; -fx-border-color: grey; -fx-border-width: 0 2 0 0; -fx-padding: 0; -fx-min-width: 150px;");

        Button section1 = createSectionButton("Dashboard");
        Button section2 = createSectionButton("Customer");
        Button section3 = createSectionButton("Claim");
        Button section4 = createSectionButton("Insurance Card");

        sidebar.getChildren().addAll(section1, section2, section3, section4);

        section1.setOnAction(e -> root.setCenter(new Label("Content for Dashboard")));
        section2.setOnAction(e -> root.setCenter(new Label("Content for Customer")));
        section3.setOnAction(e -> {
            TableView claimTable = createClaimTable();
            root.setCenter(claimTable);
        });
        section4.setOnAction(e -> root.setCenter(new Label("Content for Insurance Card")));

        DropShadow dsSidebar = new DropShadow();
        dsSidebar.setOffsetY(3.0);
        dsSidebar.setColor(Color.color(0.4, 0.4, 0.4));
        sidebar.setEffect(dsSidebar);

        return sidebar;
    }

    private HBox createHeader(Stage stage, BorderPane root){
        HBox header = new HBox();
        header.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");

        Label welcomeText = new Label("Welcome Administrator");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Create a new button with "Hello Alex"
        Button helloAlexButton = new Button("Hello Alex");
        helloAlexButton.setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-border-radius: 0; -fx-background-radius: 0; -fx-padding: 0; -fx-line-spacing: 0;");
        helloAlexButton.setOnMouseEntered(e -> helloAlexButton.setStyle("-fx-background-color: #dddddd; -fx-border-width: 0; -fx-border-radius: 0; -fx-background-radius: 0; -fx-padding: 0; -fx-line-spacing: 0;"));
        helloAlexButton.setOnMouseExited(e -> helloAlexButton.setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-border-radius: 0; -fx-background-radius: 0; -fx-padding: 0; -fx-line-spacing: 0;"));
        helloAlexButton.setMaxHeight(Double.MAX_VALUE);

        // Create a context menu (popup) with "Profile" and "Logout" options
        ContextMenu contextMenu = new ContextMenu();
        MenuItem profileItem = new MenuItem("Profile");
        MenuItem logoutItem = new MenuItem("Logout");

        // Add the menu items to the context menu
        contextMenu.getItems().addAll(profileItem, logoutItem);

        // Set the context menu to the button
        helloAlexButton.setContextMenu(contextMenu);

        // Show the context menu when the button is clicked
        helloAlexButton.setOnMouseClicked(e -> contextMenu.show(helloAlexButton, e.getScreenX(), e.getScreenY()));

        // Set the action for the "Logout" menu item
        logoutItem.setOnAction(e -> {
            stage.close();
            try {
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Set the action for the "Profile" menu item
        profileItem.setOnAction(e -> {
            GridPane profilePane = createProfilePane();
            root.setCenter(profilePane);
        });

        header.getChildren().addAll(welcomeText, spacer, helloAlexButton);

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0);
        ds.setColor(Color.color(0.4, 0.4, 0.4));
        header.setEffect(ds);

        return header;
    }

    private Button createSectionButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMinHeight(60);
        button.setFont(Font.font("System", FontWeight.BOLD, 100));
        button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: #000000; -fx-font-size: 15px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #dddddd; -fx-border-color: transparent; -fx-text-fill: #000000; -fx-font-size: 15px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: #000000; -fx-font-size: 15px;"));
        return button;
    }

    private GridPane createProfilePane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        // Define column constraints for the GridPane
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(25);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(75);
        gridPane.getColumnConstraints().addAll(column1, column2);


        Label userIdLabel = new Label("User ID:");
        TextField userIdField = new TextField();
        userIdField.setText("Default User ID");
        gridPane.add(userIdLabel, 0, 0);
        gridPane.add(userIdField, 1, 0);

        Label fullNameLabel = new Label("Full Name:");
        TextField fullNameField = new TextField();
        fullNameField.setText("Default Full Name");
        gridPane.add(fullNameLabel, 0, 1);
        gridPane.add(fullNameField, 1, 1);

        Label roleLabel = new Label("Role:");
        TextField roleField = new TextField();
        roleField.setText("Default Role");
        gridPane.add(roleLabel, 0, 2);
        gridPane.add(roleField, 1, 2);

        Label insuranceCardNumberLabel = new Label("Insurance Card Number:");
        TextField insuranceCardNumberField = new TextField();
        insuranceCardNumberField.setText("Default Insurance Card Number");
        gridPane.add(insuranceCardNumberLabel, 0, 3);
        gridPane.add(insuranceCardNumberField, 1, 3);

        Label claimsLabel = new Label("Claims:");
        TableView claimsTable = new TableView();
        gridPane.add(claimsLabel, 0, 4);
        gridPane.add(claimsTable, 1, 4);

        return gridPane;
    }

    private TableView createClaimTable() {
        TableView<Claim> table = new TableView<>();

        TableColumn<Claim, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Claim, String> claimDateColumn = new TableColumn<>("Claim Date");
        claimDateColumn.setCellValueFactory(new PropertyValueFactory<>("claimDate"));

        TableColumn<Claim, String> customerColumn = new TableColumn<>("Customer");
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));

        TableColumn<Claim, String> insuranceCardNumberColumn = new TableColumn<>("Insurance Card");
        insuranceCardNumberColumn.setCellValueFactory(new PropertyValueFactory<>("insuranceCardNumber"));

        TableColumn<Claim, String> examDateColumn = new TableColumn<>("Exam Date");
        examDateColumn.setCellValueFactory(new PropertyValueFactory<>("examDate"));

        TableColumn<Claim, String> documentsColumn = new TableColumn<>("Documents");
        documentsColumn.setCellValueFactory(new PropertyValueFactory<>("documents"));

        TableColumn<Claim, String> claimAmountColumn = new TableColumn<>("Claim Amount");
        claimAmountColumn.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));

        TableColumn<Claim, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Claim, String> receiverBankingInfoColumn = new TableColumn<>("Banking Info");
        receiverBankingInfoColumn.setCellValueFactory(new PropertyValueFactory<>("receiverBankingInfo"));

        table.getColumns().add(idColumn);
        table.getColumns().add(claimDateColumn);
        table.getColumns().add(customerColumn);
        table.getColumns().add(insuranceCardNumberColumn);
        table.getColumns().add(examDateColumn);
        table.getColumns().add(documentsColumn);
        table.getColumns().add(claimAmountColumn);
        table.getColumns().add(statusColumn);
        table.getColumns().add(receiverBankingInfoColumn);

        return table;
    }

    public static void main(String[] args) {
        launch(args);
    }
}