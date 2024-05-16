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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.group21.insurance.models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class DashboardScreen extends Application {
    private String userRole;
    private ArrayList<Claim> claimList = new ArrayList<>();
    private ArrayList<Beneficiary> beneficiaryList = new ArrayList<>();
    private ArrayList<InsuranceCard> insuranceCardList = new ArrayList<>();

    public DashboardScreen(String role) {
        this.userRole = role;
    }
    @Override
    public void start(Stage stage) {
        Scene dashboardScene = createDashboardUI(stage, this.userRole);
        stage.setTitle("Insurance Claim Dashboard Portal");
        stage.setScene(dashboardScene);
        stage.show();
    }

    private Scene createDashboardUI(Stage stage, String role) {
        BorderPane root = new BorderPane();
        // Create sidebar
        root.setLeft(createSidebar(stage, root));
        // Create the header
        root.setTop(createHeader(stage, root));
        return new Scene(root, 1200, 600);
    }

    private HBox createHeader(Stage stage, BorderPane root) {
        HBox header = new HBox();
        header.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");

        Label welcomeText = new Label("Welcome " + userRole);
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
            GridPane profilePane = showBeneficiaryProfile();
            root.setCenter(profilePane);
        });

        header.getChildren().addAll(welcomeText, spacer, helloAlexButton);

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0);
        ds.setColor(Color.color(0.4, 0.4, 0.4));
        header.setEffect(ds);

        return header;
    }


    private VBox createSidebar(Stage stage, BorderPane root) {
        VBox sidebar = new VBox();
        sidebar.setStyle("-fx-background-color: #ffffff; -fx-border-color: grey; -fx-border-width: 0 2 0 0; -fx-padding: 0; -fx-min-width: 150px;");

        Button dashboardBtn = createSectionButton("Dashboard");
        Button dependentBtn = createSectionButton("Depedent");
        Button claimBtn = createSectionButton("Claim");
        Button beneficiaryBtn = createSectionButton("Beneficiary");
        Button insuranceBtn = createSectionButton("Insurance Card");
        Button paidBtn = createSectionButton("Payment Calculation");
        Button propposedClaimBtn = createSectionButton("Proposed Claim");

        dashboardBtn.setOnAction(e -> root.setCenter(new Label("Content for Dashboard")));
        dependentBtn.setOnAction(e -> root.setCenter(new Label("Content for Dependent")));


        claimBtn.setOnAction(e -> {
            TableView claimTable = createClaimTable(root);
            Button addClaimBtn = new Button("Add Claim");
            root.setCenter(claimTable);
            if(this.userRole != "Dependent"){
                root.setRight(addClaimBtn);
                addClaimBtn.setOnAction(e2 -> {
                    root.setRight(null);
                });
            }
        });

        beneficiaryBtn.setOnAction(e -> {
            TableView beneficiaryTable = createBeneficiaryTable(root);
            root.setCenter(beneficiaryTable);
        });

        insuranceBtn.setOnAction(e -> {
            TableView insuranceTable = createInsuranceTable(root);
            root.setCenter(insuranceTable);
        });

        paidBtn.setOnAction(e -> root.setCenter(new Label("Payment Content")));
        propposedClaimBtn.setOnAction(e -> root.setCenter(new Label("Propose Claim action")));



        switch (userRole) {
            case "Dependent":
                sidebar.getChildren().addAll(dashboardBtn, claimBtn);
                break;
            case "Policy holder":
                sidebar.getChildren().addAll(dashboardBtn, dependentBtn, claimBtn, insuranceBtn);
                break;
            case "Policy owner":
                sidebar.getChildren().addAll(dashboardBtn, beneficiaryBtn, claimBtn, insuranceBtn, paidBtn);
                break;
            case "Insurance surveyor":
                sidebar.getChildren().addAll(dashboardBtn, beneficiaryBtn, claimBtn, insuranceBtn);
                break;
            case "Insurance manager":
                sidebar.getChildren().addAll(dashboardBtn, beneficiaryBtn, claimBtn, insuranceBtn);
                break;
            case "System admin":
                sidebar.getChildren().addAll(dashboardBtn, beneficiaryBtn, claimBtn, insuranceBtn);
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

    private TableView createClaimTable(BorderPane root) {
        TableView<Claim> table = new TableView<>();

        TableColumn<Claim, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("claimId"));
        idColumn.setPrefWidth(40);
        idColumn.setCellFactory(tc -> new TableCell<Claim, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item);
                    setAlignment(Pos.CENTER);
                }
            }
        });

        TableColumn<Claim, String> claimDateColumn = new TableColumn<>("Claim Date");
        claimDateColumn.setCellValueFactory(new PropertyValueFactory<>("claimDate"));

        TableColumn<Claim, String> beneficiaryColumn = new TableColumn<>("Beneficiary");
        beneficiaryColumn.setCellValueFactory(new PropertyValueFactory<>("beneficiary"));
        beneficiaryColumn.setCellFactory(tc -> new TableCell<Claim, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item);
                    setAlignment(Pos.CENTER);
                }
            }
        });

        TableColumn<Claim, String> insuranceCardNumberColumn = new TableColumn<>("Insurance Card");
        insuranceCardNumberColumn.setCellValueFactory(new PropertyValueFactory<>("insuranceCardNumber"));
        insuranceCardNumberColumn.setCellFactory(tc -> new TableCell<Claim, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item);
                    setAlignment(Pos.CENTER);
                }
            }
        });

        TableColumn<Claim, String> examDateColumn = new TableColumn<>("Exam Date");
        examDateColumn.setCellValueFactory(new PropertyValueFactory<>("examDate"));
//        examDateColumn.setCellFactory(tc -> new TableCell<Claim, String>() {
//            @Override
//            protected void updateItem(String item, boolean empty) {
//                super.updateItem(item, empty);
//                if (empty) {
//                    setText(null);
//                } else {
//                    setText(item.toString());
//                    setAlignment(Pos.CENTER);
//                }
//            }
//        });

        TableColumn<Claim, String> documentsColumn = new TableColumn<>("Documents");
        documentsColumn.setCellValueFactory(new PropertyValueFactory<>("documents"));

        TableColumn<Claim, String> claimAmountColumn = new TableColumn<>("Claim Amount");
        claimAmountColumn.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));

        TableColumn<Claim, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Claim, String> receiverBankingInfoColumn = new TableColumn<>("Banking Info");
        receiverBankingInfoColumn.setCellValueFactory(new PropertyValueFactory<>("receiverBankingInfo"));

        table.getColumns().addAll(idColumn, claimDateColumn, beneficiaryColumn, insuranceCardNumberColumn, examDateColumn, documentsColumn, claimAmountColumn, statusColumn, receiverBankingInfoColumn);

        ObservableList<Claim> claimData = FXCollections.observableArrayList();
        for (int i = 1; i <= 30; i++) {
            Claim claim = new Claim();
            claim.setClaimId("ID" + i);
            claim.setClaimDate(LocalDate.now());
            claim.setInsuredPerson(new Beneficiary());
            claim.setExamDate(LocalDate.now());
            claim.setInsuranceCard(new InsuranceCard());
            claim.setDocumentList(new ArrayList<>());
            claim.setClaimAmount(new Random().nextInt(1000) + 1);
            claim.setReceiverBankingInfo(new BankingInfo());
//          claim.setStatus(Claim.getClaimStatus.PENDING);

            claimData.add(claim);
        }

        table.setItems(claimData);

        table.setRowFactory(tv -> {
            TableRow<Claim> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                Claim clickedClaim = row.getItem();
                GridPane claimPane = showClaimDetails(clickedClaim);
                root.setCenter(claimPane);
                System.out.println("Claim ID: " + clickedClaim.getClaimId());
            });
            return row;
        });

        return table;
    }

    private static GridPane showClaimDetails(Claim claim) {
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
        userIdField.setText(claim.getClaimId());
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

    private static GridPane addNewClaimForm(){
        GridPane gridPane = new GridPane();
//        gridPane.setAlignment(Pos.TOP_CENTER);
//        gridPane.setHgap(10);
//        gridPane.setVgap(10);
//        gridPane.setPadding(new Insets(25, 25, 25, 25));
//
//        // Define column constraints for the GridPane
//        ColumnConstraints column1 = new ColumnConstraints();
//        column1.setPercentWidth(20);
//        ColumnConstraints column2 = new ColumnConstraints();
//        column2.setPercentWidth(80);
//        gridPane.getColumnConstraints().addAll(column1, column2);
//
//        Label userIdLabel = new Label("User ID:");
//        TextField userIdField = new TextField();
//        userIdField.setText(claim.getClaimId());
//        userIdField.setEditable(false);
//        gridPane.add(userIdLabel, 0, 0);
//        gridPane.add(userIdField, 1, 0);
//
//        Label fullNameLabel = new Label("Full Name:");
//        TextField fullNameField = new TextField();
//        fullNameField.setText("Default Full Name");
//        fullNameField.setEditable(false);
//        gridPane.add(fullNameLabel, 0, 1);
//        gridPane.add(fullNameField, 1, 1);
//
//        Label roleLabel = new Label("Role:");
//        TextField roleField = new TextField();
//        roleField.setText("Default Role");
//        roleField.setEditable(false);
//        gridPane.add(roleLabel, 0, 2);
//        gridPane.add(roleField, 1, 2);
//
//        Label insuranceCardNumberLabel = new Label("Insurance Card Number:");
//        TextField insuranceCardNumberField = new TextField();
//        insuranceCardNumberField.setText("Default Insurance Card Number");
//        insuranceCardNumberField.setEditable(false);
//        gridPane.add(insuranceCardNumberLabel, 0, 3);
//        gridPane.add(insuranceCardNumberField, 1, 3);
//
//        Label claimAmountLabel = new Label("Claim Amount:");
//        TextField claimAmountField = new TextField();
//        claimAmountField.setText("Default Claim Amount");
//        claimAmountField.setEditable(false);
//        gridPane.add(claimAmountLabel, 0, 4);
//        gridPane.add(claimAmountField, 1, 4);
//
//        Label claimDateLabel = new Label("Claim Date:");
//        TextField claimDateField = new TextField();
//        claimDateField.setText("Default Claim Date");
//        claimDateField.setEditable(false);
//        gridPane.add(claimDateLabel, 0, 5);
//        gridPane.add(claimDateField, 1, 5);
//
//        Label insuredPersonLabel = new Label("Insured Person:");
//        TextField insuredPersonField = new TextField();
//        insuredPersonField.setText("Default Insured Person");
//        insuredPersonField.setEditable(false);
//        gridPane.add(insuredPersonLabel, 0, 6);
//        gridPane.add(insuredPersonField, 1, 6);
//
//        Label examDateLabel = new Label("Exam Date:");
//        TextField examDateField = new TextField();
//        examDateField.setText("Default Exam Date");
//        examDateField.setEditable(false);
//        gridPane.add(examDateLabel, 0, 7);
//        gridPane.add(examDateField, 1, 7);
//
//        Label documentListLabel = new Label("Document List:");
//        TextField documentListField = new TextField();
//        documentListField.setText("Default Document List");
//        documentListField.setEditable(false);
//        gridPane.add(documentListLabel, 0, 8);
//        gridPane.add(documentListField, 1, 8);
//
//        Button submitButton = new Button("Submit");
//        gridPane.add(submitButton, 1, 9);

        return gridPane;
    }

    private TableView createInsuranceTable(BorderPane root) {
        TableView<InsuranceCard> table = new TableView<>();

        TableColumn<InsuranceCard, String> cardNumberColumn = new TableColumn<>("Card Number");
        cardNumberColumn.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
        cardNumberColumn.setPrefWidth(40);
        cardNumberColumn.setCellFactory(tc -> new TableCell<InsuranceCard, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item);
                    setAlignment(Pos.CENTER);
                }
            }
        });

        TableColumn<InsuranceCard, String> policyOwnerColumn = new TableColumn<>("Policy Owner");
        policyOwnerColumn.setCellValueFactory(new PropertyValueFactory<>("policyOwner"));

        TableColumn<InsuranceCard, String> cardHolderColumn = new TableColumn<>("Policy Holder");
        cardHolderColumn.setCellValueFactory(new PropertyValueFactory<>("policyHolder"));

        TableColumn<InsuranceCard, String> exprirationColumn = new TableColumn<>("Expiration Date");
        exprirationColumn.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));

        table.getColumns().add(cardNumberColumn);
        table.getColumns().add(policyOwnerColumn);
        table.getColumns().add(cardHolderColumn);
        table.getColumns().add(exprirationColumn);

        ObservableList<InsuranceCard> insuranceCardData = FXCollections.observableArrayList();
        for (int i = 1; i <= 30; i++) {
            InsuranceCard insuranceCard = new InsuranceCard();
            insuranceCard.setCardNumber(""+i);
            insuranceCard.setPolicyOwner(new PolicyOwner());
            insuranceCard.setCardHolder(new Beneficiary());
            insuranceCard.setExpirationDate(LocalDate.now());

            insuranceCardData.add(insuranceCard);
        }

        table.setItems(insuranceCardData);

        table.setRowFactory(tv -> {
            TableRow<InsuranceCard> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                InsuranceCard clickedInsuranceCard = row.getItem();
                GridPane claimPane = showInsuranceCardDetails(clickedInsuranceCard);
                root.setCenter(claimPane);
            });
            return row;
        });

        return table;
    }

    private static GridPane showInsuranceCardDetails(InsuranceCard insuranceCard) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(20);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(80);
        gridPane.getColumnConstraints().addAll(column1, column2);

        Label cardNumberLabel = new Label("Card Number:");
        TextField cardNumberField = new TextField();
        cardNumberField.setText(insuranceCard.getCardNumber());
        cardNumberField.setEditable(false);
        gridPane.add(cardNumberLabel, 0, 0);
        gridPane.add(cardNumberField, 1, 0);

        Label policyOwnerLabel = new Label("Policy Owner:");
        TextField policyOwnerField = new TextField();
        policyOwnerField.setText("Default Full Name");
        policyOwnerField.setEditable(false);
        gridPane.add(policyOwnerLabel, 0, 1);
        gridPane.add(policyOwnerField, 1, 1);

        Label cardHolderLabel = new Label("Card Holder:");
        TextField cardHolderField = new TextField();
        cardHolderField.setText("Default Role");
        cardHolderField.setEditable(false);
        gridPane.add(cardHolderLabel, 0, 2);
        gridPane.add(cardHolderField, 1, 2);

        Label expirationDateLabel = new Label("Expiration Date:");
        TextField expirationDateField = new TextField();
        expirationDateField.setText("Default Expiration Date");
        expirationDateField.setEditable(false);
        gridPane.add(expirationDateLabel, 0, 3);
        gridPane.add(expirationDateField, 1, 3);

        return gridPane;
    }


    private TableView createBeneficiaryTable(BorderPane root) {
        TableView<Beneficiary> table = new TableView<>();

        TableColumn<Beneficiary, String> beneficiaryIdColumn = new TableColumn<>("ID");
        beneficiaryIdColumn.setCellValueFactory(new PropertyValueFactory<>("beneficiaryId"));

        beneficiaryIdColumn.setCellFactory(tc -> new TableCell<Beneficiary, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item);
                    setAlignment(Pos.CENTER);
                }
            }
        });

        TableColumn<Beneficiary, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<Beneficiary, String> beneficiaryFullNameColumn = new TableColumn<>("Full Name");
        beneficiaryFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        TableColumn<Beneficiary, String> phoneNumberColumn = new TableColumn<>("Phone Number");
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<Beneficiary, String> insuranceCardNumberColumn = new TableColumn<>("Insurance Card Number");
        insuranceCardNumberColumn.setCellValueFactory(new PropertyValueFactory<>("insuranceCardNumber"));

        TableColumn<Beneficiary, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Beneficiary, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        table.getColumns().add(beneficiaryIdColumn);
        table.getColumns().add(beneficiaryFullNameColumn);
        table.getColumns().add(roleColumn);
        table.getColumns().add(phoneNumberColumn);
        table.getColumns().add(insuranceCardNumberColumn);
        table.getColumns().add(addressColumn);
        table.getColumns().add(emailColumn);

        ObservableList<Beneficiary> claimData = FXCollections.observableArrayList();
        for (int i = 1; i <= 30; i++) {
            Beneficiary beneficiary = new Beneficiary();
            beneficiary.setCustomerId("ID" + i);
            beneficiary.setFullName("Full Name " + i);
//            beneficiary.setRole("Role " + i);
            beneficiary.setPhoneNumber("Phone Number " + i);
//            beneficiary.setInsuranceCardNumber("Insurance Card Number " + i);
            beneficiary.setAddress("Address " + i);
            beneficiary.setEmail("Email " + i);

            claimData.add(beneficiary);
        }

        table.setItems(claimData);

        table.setRowFactory(tv -> {
            TableRow<Beneficiary> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                Beneficiary clickedBeneficiary = row.getItem();
                GridPane beneficiaryPane = showBeneficiaryDetails(clickedBeneficiary);
                root.setCenter(beneficiaryPane);
            });
            return row;
        });

        return table;
    }


    private GridPane showBeneficiaryProfile() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        // Define column constraints for the GridPane
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(15);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(85);
        gridPane.getColumnConstraints().addAll(column1, column2);

        Label userIdLabel = new Label("User ID:");
        TextField userIdField = new TextField();
        userIdField.setText("Default User ID");
        gridPane.add(userIdLabel, 0, 0);
        gridPane.add(userIdField, 1, 0);

        Label userRoleLabel = new Label("Role");
        TextField userRoleField = new TextField();
        userRoleField.setText(this.userRole);
        gridPane.add(userRoleLabel, 0, 1);
        gridPane.add(userRoleField, 1, 1);

        Label fullNameLabel = new Label("Full Name:");
        TextField fullNameField = new TextField();
        fullNameField.setText("Default Full Name");
        fullNameField.setEditable(false);
        gridPane.add(fullNameLabel, 0, 2);
        gridPane.add(fullNameField, 1, 2);

        Label roleLabel = new Label("Phone Number:");
        TextField roleField = new TextField();
        roleField.setText("Default Phone Number");
        roleField.setEditable(false);
        gridPane.add(roleLabel, 0, 3);
        gridPane.add(roleField, 1, 3);

        Label insuranceCardNumberLabel = new Label("Insurance Card Number:");
        TextField insuranceCardNumberField = new TextField();
        insuranceCardNumberField.setText("Default Insurance Card Number");
        insuranceCardNumberField.setEditable(false);
        gridPane.add(insuranceCardNumberLabel, 0, 4);
        gridPane.add(insuranceCardNumberField, 1, 4);

        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField();
        addressField.setText("Default Address");
        addressField.setEditable(false);
        gridPane.add(addressLabel, 0, 5);
        gridPane.add(addressField, 1, 5);

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setText("Default Email");
        emailField.setEditable(false);
        gridPane.add(emailLabel, 0, 6);
        gridPane.add(emailField, 1, 6);

        return gridPane;
    }

    private GridPane showBeneficiaryDetails(Beneficiary beneficiary) {
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

        Label beneficiaryIdLabel = new Label("Beneficiary ID:");
        TextField beneficiaryIdField = new TextField();
        beneficiaryIdField.setText(beneficiary.getCustomerId());
        beneficiaryIdField.setEditable(false);
        gridPane.add(beneficiaryIdLabel, 0, 0);
        gridPane.add(beneficiaryIdField, 1, 0);

        Label fullNameLabel = new Label("Full Name:");
        TextField fullNameField = new TextField();
        fullNameField.setText(beneficiary.getFullName());
        fullNameField.setEditable(false);
        gridPane.add(fullNameLabel, 0, 1);
        gridPane.add(fullNameField, 1, 1);

        Label phoneNumberLabel = new Label("Phone Number:");
        TextField phoneNumberField = new TextField();
        phoneNumberField.setText(beneficiary.getPhoneNumber());
        phoneNumberField.setEditable(false);
        gridPane.add(phoneNumberLabel, 0, 2);
        gridPane.add(phoneNumberField, 1, 2);

        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField();
        addressField.setText(beneficiary.getAddress());
        addressField.setEditable(false);
        gridPane.add(addressLabel, 0, 3);
        gridPane.add(addressField, 1, 3);

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setText(beneficiary.getEmail());
        emailField.setEditable(false);
        gridPane.add(emailLabel, 0, 4);
        gridPane.add(emailField, 1, 4);

        return gridPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}