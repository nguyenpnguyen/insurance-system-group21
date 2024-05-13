package org.group21.insurance.views;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.group21.insurance.models.BankingInfo;
import org.group21.insurance.models.Beneficiary;
import org.group21.insurance.models.Claim;
import org.group21.insurance.models.InsuranceCard;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class DashboardScreen extends Application {

    @Override
    public void start(Stage stage) {
        Scene dashboardScene = createDashboardUI(stage, "dependent");
        stage.setTitle("Insurance Claim Dashboard Portal");
        stage.setScene(dashboardScene);
        stage.show();
    }


    private Scene createDashboardUI(Stage stage, String role) {
        BorderPane root = new BorderPane();

        // Create sidebar
        root.setLeft(createSidebar(root, role));

        // Create the header
        root.setTop(createHeader(stage, root));

        return new Scene(root, 1200, 600);
    }

    private VBox createSidebar(BorderPane root, String role) {
        VBox sidebar = new VBox();
        sidebar.setStyle("-fx-background-color: #ffffff; -fx-border-color: grey; -fx-border-width: 0 2 0 0; -fx-padding: 0; -fx-min-width: 150px;");

        Button dashboardBtn = createSectionButton("Dashboard");
        Button dependentBtn = createSectionButton("Depedent");
        Button claimBtn = createSectionButton("Claim");
        Button customerBtn = createSectionButton("Customer");
        Button insuranceBtn = createSectionButton("Insurance Card");
        Button paidBtn = createSectionButton("Payment Calculation");
        Button propposedClaimBtn = createSectionButton("Proposed Claim");

        dashboardBtn.setOnAction(e -> root.setCenter(new Label("Content for Dashboard")));
        dependentBtn.setOnAction(e -> root.setCenter(new Label("Content for Dependent")));
        claimBtn.setOnAction(e -> {
            TableView claimTable = createClaimTable(root);
            root.setCenter(claimTable);
        });
        customerBtn.setOnAction(e -> root.setCenter(new Label("Content for Customer")));

        insuranceBtn.setOnAction(e -> root.setCenter(new Label("Content for Insurance Card")));
        paidBtn.setOnAction(e -> root.setCenter(new Label("Payment Content")));
        propposedClaimBtn.setOnAction(e -> root.setCenter(new Label("Propose Claim action")));

        switch (role) {
            case "dependent":
                sidebar.getChildren().addAll(dashboardBtn, claimBtn);
                break;
            case "policy holder":
                sidebar.getChildren().addAll(dashboardBtn, dependentBtn, claimBtn, customerBtn, insuranceBtn);
                break;
            case "policy owner":
                sidebar.getChildren().addAll(dashboardBtn, claimBtn, customerBtn, insuranceBtn, paidBtn);
                break;
            case "insurance surveyors":
                sidebar.getChildren().addAll(dashboardBtn, customerBtn, claimBtn, insuranceBtn);
                break;
            case "insurance managers":
                sidebar.getChildren().addAll(dashboardBtn, customerBtn, claimBtn, insuranceBtn);
                break;
            case "system admin":
                sidebar.getChildren().addAll(dashboardBtn, customerBtn, claimBtn, insuranceBtn);
                break;
            default:
                break;
        }

        DropShadow dsSidebar = new DropShadow();
        dsSidebar.setOffsetY(3.0);
        dsSidebar.setColor(Color.color(0.4, 0.4, 0.4));
        sidebar.setEffect(dsSidebar);

        return sidebar;
    }

    private HBox createHeader(Stage stage, BorderPane root) {
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
            GridPane profilePane = createProfile();
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

    private GridPane createProfile() {
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
        fullNameField.setEditable(false);
        gridPane.add(fullNameLabel, 0, 1);
        gridPane.add(fullNameField, 1, 1);

        Label roleLabel = new Label("Role:");
        TextField roleField = new TextField();
        roleField.setText("Default Role");
        roleField.setEditable(false);
        gridPane.add(roleLabel, 0, 2);
        gridPane.add(roleField, 1, 2);

        Label insuranceCardNumberLabel = new Label("Insurance Card Number:");
        TextField insuranceCardNumberField = new TextField();
        insuranceCardNumberField.setText("Default Insurance Card Number");
        insuranceCardNumberField.setEditable(false);
        gridPane.add(insuranceCardNumberLabel, 0, 3);
        gridPane.add(insuranceCardNumberField, 1, 3);

        Label claimsLabel = new Label("Claims:");
        TableView claimsTable = new TableView();
        gridPane.add(claimsLabel, 0, 4);
        gridPane.add(claimsTable, 1, 4);

        return gridPane;
    }

    private TableView createClaimTable(BorderPane root) {
        TableView<Claim> table = new TableView<>();

        TableColumn<Claim, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        idColumn.setPrefWidth(150);

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

        ObservableList<Claim> claimData = FXCollections.observableArrayList();
        for (int i = 0; i < 20; i++) {
            Claim claim = new Claim();
            claim.setId("ID" + i);
            claim.setClaimDate(LocalDate.now());
             claim.setInsuredPerson(new Beneficiary());
             claim.setExamDate(LocalDate.now());
             claim.setInsuranceCard(new InsuranceCard());
             claim.setDocumentList(new ArrayList<>());
             claim.setClaimAmount(new Random().nextInt(1000) + 1);
             claim.setReceiverBankingInfo(new BankingInfo());
//            claim.setStatus(Claim.getClaimStatus.PENDING);

            claimData.add(claim);
        }

        table.setItems(claimData);

        table.setRowFactory(tv -> {
            TableRow<Claim> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                Claim clickedClaim = row.getItem();
                GridPane claimPane = createClaimDetails(clickedClaim);
                root.setCenter(claimPane);
                System.out.println("Claim ID: " + clickedClaim.getId());
            });
            return row;
        });

        return table;
    }

    private static GridPane createClaimDetails(Claim claim) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        // Define column constraints for the GridPane
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(20);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(80);
        gridPane.getColumnConstraints().addAll(column1, column2);


        Label userIdLabel = new Label("User ID:");
        TextField userIdField = new TextField();
        userIdField.setText(claim.getId());
        userIdField.setEditable(false);
        gridPane.add(userIdLabel, 0, 0);
        gridPane.add(userIdField, 1, 0);

        Label fullNameLabel = new Label("Full Name:");
        TextField fullNameField = new TextField();
        fullNameField.setText("Default Full Name");
        fullNameField.setEditable(false);
        gridPane.add(fullNameLabel, 0, 1);
        gridPane.add(fullNameField, 1, 1);

        Label roleLabel = new Label("Role:");
        TextField roleField = new TextField();
        roleField.setText("Default Role");
        roleField.setEditable(false);
        gridPane.add(roleLabel, 0, 2);
        gridPane.add(roleField, 1, 2);

        Label insuranceCardNumberLabel = new Label("Insurance Card Number:");
        TextField insuranceCardNumberField = new TextField();
        insuranceCardNumberField.setText("Default Insurance Card Number");
        insuranceCardNumberField.setEditable(false);
        gridPane.add(insuranceCardNumberLabel, 0, 3);
        gridPane.add(insuranceCardNumberField, 1, 3);

        Label claimAmountLabel = new Label("Claim Amount:");
        TextField claimAmountField = new TextField();
        claimAmountField.setText("Default Claim Amount");
        claimAmountField.setEditable(false);
        gridPane.add(claimAmountLabel, 0, 4);
        gridPane.add(claimAmountField, 1, 4);

        Label claimDateLabel = new Label("Claim Date:");
        TextField claimDateField = new TextField();
        claimDateField.setText("Default Claim Date");
        claimDateField.setEditable(false);
        gridPane.add(claimDateLabel, 0, 5);
        gridPane.add(claimDateField, 1, 5);

        Label insuredPersonLabel = new Label("Insured Person:");
        TextField insuredPersonField = new TextField();
        insuredPersonField.setText("Default Insured Person");
        insuredPersonField.setEditable(false);
        gridPane.add(insuredPersonLabel, 0, 6);
        gridPane.add(insuredPersonField, 1, 6);

        Label examDateLabel = new Label("Exam Date:");
        TextField examDateField = new TextField();
        examDateField.setText("Default Exam Date");
        examDateField.setEditable(false);
        gridPane.add(examDateLabel, 0, 7);
        gridPane.add(examDateField, 1, 7);

        Label documentListLabel = new Label("Document List:");
        TextField documentListField = new TextField();
        documentListField.setText("Default Document List");
        documentListField.setEditable(false);
        gridPane.add(documentListLabel, 0, 8);
        gridPane.add(documentListField, 1, 8);

        return gridPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}