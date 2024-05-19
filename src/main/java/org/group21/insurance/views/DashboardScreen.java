package org.group21.insurance.views;

/**
 * @author Group 21
 */

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.group21.insurance.controllers.*;
import org.group21.insurance.models.*;
import org.group21.insurance.utils.ClaimCalculator;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class DashboardScreen extends Application {
	private static String userRole;
	private static String userId;
	private Stage stage;
	
	public DashboardScreen(String userId, String role) {
		this.userRole = role;
		this.userId = userId;
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
		
		Button helloUserButton = new Button("Hello user " + userId);
		helloUserButton.setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-border-radius: 0; -fx-background-radius: 0; -fx-padding: 0; -fx-line-spacing: 0;");
		helloUserButton.setOnMouseEntered(e -> helloUserButton.setStyle("-fx-background-color: #dddddd; -fx-border-width: 0; -fx-border-radius: 0; -fx-background-radius: 0; -fx-padding: 0; -fx-line-spacing: 0;"));
		helloUserButton.setOnMouseExited(e -> helloUserButton.setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-border-radius: 0; -fx-background-radius: 0; -fx-padding: 0; -fx-line-spacing: 0;"));
		helloUserButton.setMaxHeight(Double.MAX_VALUE);
		
		// Create a context menu (popup) with "Profile" and "Logout" options
		ContextMenu contextMenu = new ContextMenu();
		MenuItem profileItem = new MenuItem("Profile");
		MenuItem logoutItem = new MenuItem("Logout");
		
		// Add the menu items to the context menu
		contextMenu.getItems().addAll(profileItem, logoutItem);
		
		// Set the context menu to the button
		helloUserButton.setContextMenu(contextMenu);
		
		// Show the context menu when the button is clicked
		helloUserButton.setOnMouseClicked(e -> contextMenu.show(helloUserButton, e.getScreenX(), e.getScreenY()));
		
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
			GridPane profilePane = showUserProfile();
			root.setCenter(profilePane);
		});
		
		header.getChildren().addAll(welcomeText, spacer, helloUserButton);
		
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
		Button addClaimBtn = new Button("Add Claim");
		addClaimBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px;");
		Button addInsuranceBtn = new Button("Add Insurance Card");
		addInsuranceBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px;");
		Button addBeneficiaryBtn = new Button("Add Beneficiary");
		addBeneficiaryBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px;");
		
		dashboardBtn.setOnAction(e -> {
			root.setCenter(new Label("Content for Dashboard"));
		});
		dependentBtn.setOnAction(e -> {
			root.setCenter(getDependentTable(root));
			root.setRight(null);
		});
		claimBtn.setOnAction(e -> {
			root.setCenter(getClaimTable(root));
		});
		
		beneficiaryBtn.setOnAction(e -> {
			root.setCenter(getBeneficiaryTable(root));
		});
		
		insuranceBtn.setOnAction(e -> {
			root.setCenter(getInsuranceTable(root));
		});
		
		paidBtn.setOnAction(e -> {
			root.setCenter(getPaymentContent());
		});
		
		propposedClaimBtn.setOnAction(e -> {
			root.setCenter(new Label("Propose Claim action"));
		});
		
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
	
	private GridPane getClaimTable(BorderPane root) {
		ClaimController claimController = ClaimController.getInstance();
		List<Claim> claims = new ArrayList<>();
		
		switch (userRole) {
			case "Dependent" ->
				// Get all claims for the current user
					claims = claimController.readAllByInsuredPerson(userId);
			case "Policy holder" -> {
				// Get all claims for the policy holder
				BeneficiaryController beneficiaryController = BeneficiaryController.getInstance();
				Optional<Beneficiary> policyHolder = beneficiaryController.readPolicyHolder(userId);
				if (policyHolder.isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Policy Holder Not Found");
					alert.setContentText("The policy holder with ID " + userId + " does not exist.");
				}
				
				claims = claimController.readAllByInsuredPerson(String.valueOf(policyHolder.get().getCustomerId()));
				
				List<Beneficiary> dependents = policyHolder.get().getDependents();
				for (Beneficiary dependent : dependents) {
					claims.addAll(claimController.readAllByInsuredPerson(dependent.getCustomerId()));
				}
			}
			case "Policy owner" -> {
				List<Claim> allClaims = claimController.readAll();
				for (Claim claim : allClaims) {
					if (claim.getInsuredPerson().getPolicyOwner() != null &&
							claim.getInsuredPerson().getPolicyOwner().getCustomerId().equals(userId)) {
						claims.add(claim);
					}
				}
			}
			case null, default -> claims = claimController.readAll();
		}
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.TOP_CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));
		
		ColumnConstraints column = new ColumnConstraints();
		column.setPercentWidth(100);
		gridPane.getColumnConstraints().addAll(column);
		
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
		
		TableColumn<Claim, String> beneficiaryColumn = new TableColumn<>("Insured Person");
		beneficiaryColumn.setCellValueFactory(new PropertyValueFactory<>("insuredPerson"));
		
		TableColumn<Claim, String> insuranceCardNumberColumn = new TableColumn<>("Insurance Card");
		insuranceCardNumberColumn.setCellValueFactory(new PropertyValueFactory<>("insuranceCard"));
		
		TableColumn<Claim, String> examDateColumn = new TableColumn<>("Exam Date");
		examDateColumn.setCellValueFactory(new PropertyValueFactory<>("examDate"));
		
		TableColumn<Claim, String> documentsColumn = new TableColumn<>("Documents");
		documentsColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDocumentList().stream()
				.map(Document::getFileName)
				.collect(Collectors.joining(", "))));
		
		TableColumn<Claim, String> claimAmountColumn = new TableColumn<>("Claim Amount");
		claimAmountColumn.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
		
		TableColumn<Claim, String> statusColumn = new TableColumn<>("Status");
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		
		TableColumn<Claim, String> receiverBankingInfoColumn = new TableColumn<>("Banking Info");
		receiverBankingInfoColumn.setCellValueFactory(new PropertyValueFactory<>("receiverBankingInfo"));
		
		table.getColumns().addAll(idColumn, claimDateColumn, beneficiaryColumn, insuranceCardNumberColumn, examDateColumn, documentsColumn, claimAmountColumn, statusColumn, receiverBankingInfoColumn);
		
		ContextMenu deleteOrUpdateContextMenu = new ContextMenu();
		MenuItem updateItem = new MenuItem("Update");
		MenuItem deleteItem = new MenuItem("Delete");
		deleteOrUpdateContextMenu.getItems().addAll(updateItem, deleteItem);
		
		deleteItem.setOnAction(e -> {
			Claim selectedClaim = table.getSelectionModel().getSelectedItem();
			table.getItems().remove(selectedClaim);
		});
		
		updateItem.setOnAction(e -> {
			Claim selectedClaim = table.getSelectionModel().getSelectedItem();
			GridPane claimPane = showClaimDetails(selectedClaim, "update", table);
			root.setCenter(claimPane);
		});
		
		ObservableList<Claim> claimData = FXCollections.observableArrayList();
		
		claimData.addAll(claims);
		
		table.setItems(claimData);
		
		table.setRowFactory(tv -> {
			TableRow<Claim> row = new TableRow<>();
			row.setContextMenu(deleteOrUpdateContextMenu);
			row.setOnMouseClicked(e -> {
				if (e.getButton() == MouseButton.SECONDARY && (!row.isEmpty()) && this.userRole != "Dependent") {
					deleteOrUpdateContextMenu.show(row, e.getScreenX(), e.getScreenY());
				} else if (e.getButton() == MouseButton.PRIMARY && (!row.isEmpty())) {
					Claim clickedClaim = row.getItem();
					GridPane claimPane = showClaimDetails(clickedClaim, "view", table);
					root.setCenter(claimPane);
				}
			});
			
			return row;
		});
		
		HBox header = new HBox();
		header.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");
		Label headerText = new Label("Claim List");
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		Button addClaimBtn = new Button("+");
		addClaimBtn.setOnAction(e -> {
			root.setCenter(addNewClaimForm(root, table));
		});
		addClaimBtn.setStyle("-fx-background-color: #62a0ff; -fx-text-fill: white; -fx-font-size: 14px;");
		
		header.getChildren().addAll(headerText, spacer, addClaimBtn);
		
		gridPane.add(header, 0, 0);
		gridPane.add(table, 0, 1);
		
		return gridPane;
	}
	
	private static GridPane showClaimDetails(Claim claim, String action, TableView<Claim> table) {
		ClaimController claimController = ClaimController.getInstance();
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
		
		Label claimIdLabel = new Label("Claim ID:");
		TextField claimIdField = new TextField();
		claimIdField.setText(claim.getClaimId());
		claimIdField.setEditable(false);
		gridPane.add(claimIdLabel, 0, 0);
		gridPane.add(claimIdField, 1, 0);
		
		Label fullNameLabel = new Label("Insured Person:");
		TextField fullNameField = new TextField();
		fullNameField.setText(claim.getInsuredPerson().toString());
		fullNameField.setEditable(false);
		gridPane.add(fullNameLabel, 0, 1);
		gridPane.add(fullNameField, 1, 1);
		
		String role = claim.getInsuredPerson().isPolicyHolder() ? "Policy Holder" : "Dependent";
		Label roleLabel = new Label("Role:");
		TextField roleField = new TextField();
		roleField.setText(role);
		roleField.setEditable(false);
		gridPane.add(roleLabel, 0, 2);
		gridPane.add(roleField, 1, 2);
		
		Label insuranceCardNumberLabel = new Label("Insurance Card Number:");
		TextField insuranceCardNumberField = new TextField();
		insuranceCardNumberField.setText(claim.getInsuranceCard().getCardNumber());
		insuranceCardNumberField.setEditable(false);
		gridPane.add(insuranceCardNumberLabel, 0, 3);
		gridPane.add(insuranceCardNumberField, 1, 3);
		
		Label claimAmountLabel = new Label("Claim Amount:");
		TextField claimAmountField = new TextField();
		claimAmountField.setText(String.valueOf(claim.getClaimAmount()));
		claimAmountField.setEditable(false);
		gridPane.add(claimAmountLabel, 0, 4);
		gridPane.add(claimAmountField, 1, 4);
		
		Label claimDateLabel = new Label("Claim Date:");
		TextField claimDateField = new TextField();
		claimDateField.setText(String.valueOf(claim.getClaimDate()));
		claimDateField.setEditable(false);
		gridPane.add(claimDateLabel, 0, 5);
		gridPane.add(claimDateField, 1, 5);
		
		Label examDateLabel = new Label("Exam Date:");
		TextField examDateField = new TextField();
		examDateField.setText(String.valueOf(claim.getExamDate()));
		examDateField.setEditable(false);
		gridPane.add(examDateLabel, 0, 7);
		gridPane.add(examDateField, 1, 7);
		
		Label documentListLabel = new Label("Document List:");
		TextField documentListField = new TextField();
		documentListField.setText(claim.getDocumentList().toString());
		documentListField.setEditable(false);
		gridPane.add(documentListLabel, 0, 8);
		gridPane.add(documentListField, 1, 8);
		
		Label statusLabel = new Label("Status:");
		TextField statusField = new TextField();
		statusField.setText(String.valueOf(claim.getStatus()));
		statusField.setEditable(false);
		gridPane.add(statusLabel, 0, 9);
		gridPane.add(statusField, 1, 9);
		
		if (action.equals("update")) {
			claimIdField.setEditable(false);
			fullNameField.setEditable(false);
			roleField.setEditable(false);
			insuranceCardNumberField.setEditable(false);
			claimAmountField.setEditable(true);
			claimDateField.setEditable(true);
			examDateField.setEditable(true);
			documentListField.setEditable(false);
			statusField.setEditable(true);
			Button saveBtn = new Button("Save");
			gridPane.add(saveBtn, 1, 9);
			saveBtn.setOnAction(e -> {
				String updatedClaimAmount = claimAmountField.getText();
				String updatedClaimDate = claimDateField.getText();
				String updatedExamDate = examDateField.getText();
				String updatedStatus = statusField.getText();
				
				claim.setClaimDate(LocalDate.parse(updatedClaimDate));
				claim.setExamDate(LocalDate.parse(updatedExamDate));
				claim.setStatus(Claim.ClaimStatus.valueOf(updatedStatus));
				claim.setClaimAmount(Long.parseLong(updatedClaimAmount));
				
				claimController.update(claim);
				
				Optional<Claim> updatedClaimOptional = claimController.read(claim.getClaimId());
				
				updatedClaimOptional.ifPresent(updatedClaim -> {
					int index = table.getItems().indexOf(claim);
					if (index != -1) {
						table.getItems().set(index, updatedClaim);
					}
				});
			});
		}
		
		return gridPane;
	}
	
	private GridPane addNewClaimForm(BorderPane root, TableView<Claim> table) {
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
		
		// Insured Person Field
		Label insuredPersonLabel = new Label("Insured Person ID:");
		TextField insuredPersonField = new TextField();
		gridPane.add(insuredPersonLabel, 0, 0);
		gridPane.add(insuredPersonField, 1, 0);
		
		// Claim Amount Field
		Label claimAmountLabel = new Label("Claim Amount:");
		TextField claimAmountField = new TextField();
		gridPane.add(claimAmountLabel, 0, 2);
		gridPane.add(claimAmountField, 1, 2);
		
		// Banking Info Field
		Label bankingInfoLabel = new Label("Bank account number:");
		TextField bankingInfoField = new TextField();
		gridPane.add(bankingInfoLabel, 0, 3);
		gridPane.add(bankingInfoField, 1, 3);
		
		// Claim Date Picker
		Label claimDateLabel = new Label("Claim Date:");
		DatePicker claimDatePicker = new DatePicker();
		gridPane.add(claimDateLabel, 0, 4);
		gridPane.add(claimDatePicker, 1, 4);
		
		// Exam Date Picker
		Label examDateLabel = new Label("Exam Date:");
		DatePicker examDatePicker = new DatePicker();
		gridPane.add(examDateLabel, 0, 5);
		gridPane.add(examDatePicker, 1, 5);
		
		// Document Upload
		Label uploadLabel = new Label("Upload Document:");
		Button selectButton = new Button("Select Documents");
		ListView<String> documentListView = new ListView<>();
		gridPane.add(uploadLabel, 0, 6);
		gridPane.add(selectButton, 1, 6);
		gridPane.add(documentListView, 1, 7);
		
		List<File> selectedFileList = new ArrayList<>();
		
		selectButton.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select Documents");
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("All Files", "*.*"),
					new FileChooser.ExtensionFilter("PDF", "*.pdf")
			);
			List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);
			if (selectedFiles != null) {
				for (File file : selectedFiles) {
					documentListView.getItems().add(file.getName());
					selectedFileList.add(file);
				}
			}
		});
		
		// Status ComboBox
		Label statusLabel = new Label("Status:");
		ComboBox<Claim.ClaimStatus> statusComboBox = new ComboBox<>();
		statusComboBox.getItems().addAll(Claim.ClaimStatus.values());
		gridPane.add(statusLabel, 0, 8);
		gridPane.add(statusComboBox, 1, 8);
		
		// Submit Button
		Button submitButton = new Button("Submit");
		gridPane.add(submitButton, 1, 9);
		
		submitButton.setOnAction(e -> {
			try {
				// Collect data from form fields
				String insuredPersonId = insuredPersonField.getText();
				String claimAmountStr = bankingInfoField.getText();
				LocalDate claimDate = claimDatePicker.getValue();
				LocalDate examDate = examDatePicker.getValue();
				Claim.ClaimStatus status = statusComboBox.getValue();
				String bankingInfo = bankingInfoField.getText();
				
				// Validate data
				if (insuredPersonId.isEmpty() || claimAmountStr.isEmpty() || claimDate == null || examDate == null || status == null) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Validation Error");
					alert.setContentText("Please fill all required fields.");
					alert.showAndWait();
					return;
				}
				long claimAmount = Long.parseLong(claimAmountStr);
				
				BeneficiaryController beneficiaryController = BeneficiaryController.getInstance();
				Optional<Beneficiary> insuredPerSon = beneficiaryController.read(insuredPersonId);
				
				if (insuredPerSon.isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Invalid Insured Person");
					alert.setContentText("The insured person with ID " + insuredPersonId + " does not exist.");
					alert.showAndWait();
					return;
				}
				Claim newClaim = new Claim();
				newClaim.setInsuredPerson(insuredPerSon.get());
				newClaim.setClaimAmount(claimAmount);
				newClaim.setClaimDate(claimDate);
				newClaim.setExamDate(examDate);
				newClaim.setStatus(status);
				
				InsuranceCardController insuranceCardController = InsuranceCardController.getInstance();
				InsuranceCard insuranceCard = insuranceCardController.findCardByCardHolder(insuredPerSon.get());
				
				newClaim.setInsuranceCard(insuranceCard);
				
				BankingInfoController bankingInfoController = BankingInfoController.getInstance();
				Optional<BankingInfo> bankingInfoOptional = bankingInfoController.read(bankingInfo);
				
				if (bankingInfoOptional.isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Invalid Banking Info");
					alert.setContentText("The banking info with account number " + bankingInfo + " does not exist.");
					alert.showAndWait();
					return;
				}
				
				newClaim.setReceiverBankingInfo(bankingInfoOptional.get());
				
				// Save the claim to the database
				ClaimController claimController = ClaimController.getInstance();
				claimController.create(newClaim);
				
				DocumentController documentController = DocumentController.getInstance();
				for (File file : selectedFileList) {
					documentController.uploadDocument(file, newClaim);
				}
				
				// Add the claim to the table
				table.getItems().add(newClaim);
				
				// Show success message
				Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
				successAlert.setTitle("Success");
				successAlert.setHeaderText(null);
				successAlert.setContentText("Claim added successfully.");
				successAlert.showAndWait();
				
			} catch (NumberFormatException ex) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Invalid Input");
				alert.setContentText("Please enter a valid number for claim amount.");
				alert.showAndWait();
			} catch (Exception ex) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error Adding Claim");
				alert.setContentText("An error occurred while adding the claim.");
				alert.showAndWait();
			}
		});
		
		return gridPane;
	}
	
	private GridPane getInsuranceTable(BorderPane root) {
		// Fetch the list of InsuranceCard objects from the database
		InsuranceCardController insuranceCardController = InsuranceCardController.getInstance();
		List<InsuranceCard> insuranceCards = new ArrayList<>();
		switch (userRole) {
			case "Dependent" -> {
				// Get the insurance card for the current user
				BeneficiaryController beneficiaryController = BeneficiaryController.getInstance();
				Optional<Beneficiary> insuredPerson = beneficiaryController.read(userId);
				if (insuredPerson.isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Insured Person Not Found");
					alert.setContentText("The insured person with ID " + userId + " does not exist.");
					alert.showAndWait();
					return new GridPane();
				}
				InsuranceCard insuranceCard = insuranceCardController.findCardByCardHolder(insuredPerson.get());
				return showInsuranceCardDetails(insuranceCard);
			}
			case "Policy holder" -> {
				// Get the insurance cards for the policy holder
				BeneficiaryController beneficiaryController = BeneficiaryController.getInstance();
				Optional<Beneficiary> policyHolderOptional = beneficiaryController.readPolicyHolder(userId);
				if (policyHolderOptional.isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Policy Holder Not Found");
					alert.setContentText("The policy holder with ID " + userId + " does not exist.");
					alert.showAndWait();
					return new GridPane();
				}
				
				Beneficiary policyHolder = policyHolderOptional.get();
				List<Beneficiary> dependents = policyHolder.getDependents();
				for (Beneficiary dependent : dependents) {
					InsuranceCard insuranceCard = insuranceCardController.findCardByCardHolder(dependent);
					if (insuranceCard != null) {
						insuranceCards.add(insuranceCard);
					}
				}
			}
			case "Policy owner" -> {
				// Get the insurance cards for the policy owner
				PolicyOwnerController policyOwnerController = PolicyOwnerController.getInstance();
				Optional<PolicyOwner> policyOwnerOptional = policyOwnerController.read(userId);
				if (policyOwnerOptional.isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Policy Owner Not Found");
					alert.setContentText("The policy owner with ID " + userId + " does not exist.");
					alert.showAndWait();
					return new GridPane();
				}
				
				PolicyOwner policyOwner = policyOwnerOptional.get();
				List<Beneficiary> beneficiaries = policyOwner.getBeneficiaries();
				for (Beneficiary beneficiary : beneficiaries) {
					InsuranceCard insuranceCard = insuranceCardController.findCardByCardHolder(beneficiary);
					if (insuranceCard != null) {
						insuranceCards.add(insuranceCard);
					}
				}
			}
			default -> insuranceCards = insuranceCardController.readAll();
		}
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.TOP_CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));
		
		ColumnConstraints column = new ColumnConstraints();
		column.setPercentWidth(100);
		gridPane.getColumnConstraints().addAll(column);
		
		TableView<InsuranceCard> table = new TableView<>();
		
		TableColumn<InsuranceCard, String> cardNumberColumn = new TableColumn<>("Card Number");
		cardNumberColumn.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
		cardNumberColumn.setPrefWidth(100);
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
		policyOwnerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPolicyOwner().getFullName()));
		
		TableColumn<InsuranceCard, String> cardHolderColumn = new TableColumn<>("Card Holder");
		cardHolderColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCardHolder().getFullName()));
		
		TableColumn<InsuranceCard, String> exprirationColumn = new TableColumn<>("Expiration Date");
		exprirationColumn.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
		
		table.getColumns().addAll(cardNumberColumn, policyOwnerColumn, cardHolderColumn, exprirationColumn);
		
		
		ObservableList<InsuranceCard> insuranceCardData = FXCollections.observableArrayList(insuranceCards);
		
		table.setItems(insuranceCardData);
		
		table.setRowFactory(tv -> {
			TableRow<InsuranceCard> row = new TableRow<>();
			row.setOnMouseClicked(e -> {
				if (!row.isEmpty()) {
					InsuranceCard clickedInsuranceCard = row.getItem();
					GridPane claimPane = showInsuranceCardDetails(clickedInsuranceCard);
					root.setCenter(claimPane);
				}
			});
			return row;
		});
		
		HBox header = new HBox();
		header.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");
		Label headerText = new Label("Insurance Card List");
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		Button addInsuranceBtn = new Button("+");
		addInsuranceBtn.setStyle("-fx-background-color: #62a0ff; -fx-text-fill: white; -fx-font-size: 14px;");
		addInsuranceBtn.setOnAction(e -> {
			root.setCenter(addNewInsuranceCardForm(root, table));
		});
		
		header.getChildren().addAll(headerText, spacer, addInsuranceBtn);
		
		gridPane.add(header, 0, 0);
		gridPane.add(table, 0, 1);
		
		return gridPane;
	}
	
	private static GridPane showInsuranceCardDetails(InsuranceCard insuranceCard) {
		InsuranceCardController insuranceCardController = InsuranceCardController.getInstance();
		Optional<InsuranceCard> insuranceCardOptional = insuranceCardController.read(insuranceCard.getCardNumber());
		if (insuranceCardOptional.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Insurance Card Not Found");
			alert.setContentText("The insurance card with card number " + insuranceCard.getCardNumber() + " does not exist.");
			return new GridPane();
		}
		
		insuranceCard = insuranceCardOptional.get();
		
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
		policyOwnerField.setText(insuranceCard.getPolicyOwner().getFullName());
		policyOwnerField.setEditable(false);
		gridPane.add(policyOwnerLabel, 0, 1);
		gridPane.add(policyOwnerField, 1, 1);
		
		Label cardHolderLabel = new Label("Card Holder:");
		TextField cardHolderField = new TextField();
		cardHolderField.setText(insuranceCard.getCardHolder().getFullName());
		cardHolderField.setEditable(false);
		gridPane.add(cardHolderLabel, 0, 2);
		gridPane.add(cardHolderField, 1, 2);
		
		Label expirationDateLabel = new Label("Expiration Date:");
		TextField expirationDateField = new TextField();
		expirationDateField.setText(String.valueOf(insuranceCard.getExpirationDate()));
		expirationDateField.setEditable(false);
		gridPane.add(expirationDateLabel, 0, 3);
		gridPane.add(expirationDateField, 1, 3);
		
		return gridPane;
	}
	
	private static GridPane addNewInsuranceCardForm(BorderPane root, TableView<InsuranceCard> table) {
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
		
		Label cardHolderLabel = new Label("Card Holder ID:");
		TextField cardHolderField = new TextField();
		gridPane.add(cardHolderLabel, 0, 0);
		gridPane.add(cardHolderField, 1, 0);
		
		Label policyOwnerLabel = new Label("Policy Owner ID:");
		TextField policyOwnerField = new TextField();
		gridPane.add(policyOwnerLabel, 0, 1);
		gridPane.add(policyOwnerField, 1, 1);
		
		Label expirationDateLabel = new Label("Expiration Date:");
		DatePicker expirationDateField = new DatePicker();
		gridPane.add(expirationDateLabel, 0, 2);
		gridPane.add(expirationDateField, 1, 2);
		
		Button submitButton = new Button("Submit");
		gridPane.add(submitButton, 1, 3);
		
		String cardHolderId = cardHolderField.getText();
		String policyOwnerId = policyOwnerField.getText();
		LocalDate expirationDate = expirationDateField.getValue();
		
		submitButton.setOnAction(e -> {
			InsuranceCardController insuranceCardController = InsuranceCardController.getInstance();
			BeneficiaryController beneficiaryController = BeneficiaryController.getInstance();
			PolicyOwnerController policyOwnerController = PolicyOwnerController.getInstance();
			
			Optional<Beneficiary> cardHolderOptional = beneficiaryController.read(cardHolderId);
			Optional<PolicyOwner> policyOwnerOptional = policyOwnerController.read(policyOwnerId);
			
			if (cardHolderOptional.isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Invalid Card Holder");
				alert.setContentText("The card holder with ID " + cardHolderId + " does not exist.");
				alert.showAndWait();
				return;
			}
			
			if (policyOwnerOptional.isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Invalid Policy Owner");
				alert.setContentText("The policy owner with ID " + policyOwnerId + " does not exist.");
				alert.showAndWait();
				return;
			}
			
			InsuranceCard newInsuranceCard = new InsuranceCard();
			newInsuranceCard.setCardHolder(cardHolderOptional.get());
			newInsuranceCard.setPolicyOwner(policyOwnerOptional.get());
			newInsuranceCard.setExpirationDate(expirationDate);
			
			insuranceCardController.create(newInsuranceCard);
			root.setCenter(table);
		});
		
		return gridPane;
	}
	
	
	private GridPane getBeneficiaryTable(BorderPane root) {
		BeneficiaryController beneficiaryController = BeneficiaryController.getInstance();
		PolicyOwnerController policyOwnerController = PolicyOwnerController.getInstance();
		List<Beneficiary> beneficiaries;
		
		if (userRole == "Policy owner") {
			Optional<PolicyOwner> policyOwner = policyOwnerController.read(userId);
			if (policyOwner.isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Policy Holder Not Found");
				alert.setContentText("The policy holder with ID " + userId + " does not exist.");
				alert.showAndWait();
				return new GridPane();
			}
			beneficiaries = policyOwner.get().getBeneficiaries();
		} else if (Objects.equals(userRole, "Insurance manager") ||
				Objects.equals(userRole, "Insurance surveyor") ||
				Objects.equals(userRole, "System admin")) {
			beneficiaries = beneficiaryController.readAll();
		} else {
			return new GridPane();
		}
		
		ObservableList<Beneficiary> beneficiaryData = FXCollections.observableArrayList();
		beneficiaryData.addAll(beneficiaries);
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.TOP_CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));
		
		ColumnConstraints column = new ColumnConstraints();
		column.setPercentWidth(100);
		gridPane.getColumnConstraints().addAll(column);
		TableView<Beneficiary> table = new TableView<>();
		
		TableColumn<Beneficiary, String> beneficiaryIdColumn = new TableColumn<>("ID");
		beneficiaryIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
		
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
		roleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isPolicyHolder() ? "Policy Holder" : "Dependent"));
		
		TableColumn<Beneficiary, String> beneficiaryFullNameColumn = new TableColumn<>("Full Name");
		beneficiaryFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
		
		TableColumn<Beneficiary, String> phoneNumberColumn = new TableColumn<>("Phone Number");
		phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		
		TableColumn<Beneficiary, String> insuranceCardNumberColumn = new TableColumn<>("Insurance Card Number");
		insuranceCardNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInsuranceCard().getCardNumber()));
		
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
		
		
		table.setItems(beneficiaryData);
		
		table.setRowFactory(tv -> {
			TableRow<Beneficiary> row = new TableRow<>();
			row.setOnMouseClicked(e -> {
				Beneficiary clickedBeneficiary = row.getItem();
				GridPane beneficiaryPane = showBeneficiaryDetails(clickedBeneficiary);
				root.setCenter(beneficiaryPane);
			});
			return row;
		});
		
		
		HBox header = new HBox();
		header.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");
		Label headerText = new Label("Insurance Card List");
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		Button addInsuranceBtn = new Button("+");
		addInsuranceBtn.setStyle("-fx-background-color: #62a0ff; -fx-text-fill: white; -fx-font-size: 14px;");
		addInsuranceBtn.setOnAction(e -> {
			root.setCenter(addNewBeneficiaryForm(root, table));
		});
		
		header.getChildren().addAll(headerText, spacer, addInsuranceBtn);
		
		gridPane.add(header, 0, 0);
		gridPane.add(table, 0, 1);
		
		return gridPane;
	}
	
	
	private GridPane showUserProfile() {
		switch (userRole) {
			case "Policy holder", "Dependent" -> {
				return showBeneficiaryProfile();
			}
			case "Policy owner" -> {
				return showPolicyOwnerProfile();
			}
			case "Insurance manager", "Insurance surveyor" -> {
				return showInsuranceProviderProfile();
			}
		}
		
		// Admin profile
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.TOP_CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));
		
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(15);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(85);
		gridPane.getColumnConstraints().addAll(column1, column2);
		
		Label userIdLabel = new Label("User:");
		TextField userIdField = new TextField();
		userIdField.setText("System Admin");
		gridPane.add(userIdLabel, 0, 0);
		gridPane.add(userIdField, 1, 0);
		
		Label userRoleLabel = new Label("Role");
		TextField userRoleField = new TextField();
		userRoleField.setText(this.userRole);
		gridPane.add(userRoleLabel, 0, 1);
		gridPane.add(userRoleField, 1, 1);
		
		return gridPane;
	}
	
	private GridPane showInsuranceProviderProfile() {
		InsuranceProviderController insuranceProviderController = InsuranceProviderController.getInstance();
		Optional<InsuranceProvider> insuranceProviderOptional = insuranceProviderController.read(this.userId);
		if (insuranceProviderOptional.isEmpty()) {
			return new GridPane();
		}
		InsuranceProvider insuranceProvider = insuranceProviderOptional.get();
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.TOP_CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));
		
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(15);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(85);
		gridPane.getColumnConstraints().addAll(column1, column2);
		
		Label userIdLabel = new Label("User ID:");
		TextField userIdField = new TextField();
		userIdField.setText(insuranceProvider.getInsuranceProviderId());
		gridPane.add(userIdLabel, 0, 0);
		gridPane.add(userIdField, 1, 0);
		
		Label userRoleLabel = new Label("Role");
		TextField userRoleField = new TextField();
		userRoleField.setText(this.userRole);
		gridPane.add(userRoleLabel, 0, 1);
		gridPane.add(userRoleField, 1, 1);
		
		Label fullNameLabel = new Label("Full Name:");
		TextField fullNameField = new TextField();
		fullNameField.setText(insuranceProvider.getFullName());
		fullNameField.setEditable(false);
		gridPane.add(fullNameLabel, 0, 2);
		gridPane.add(fullNameField, 1, 2);
		
		Label roleLabel = new Label("Phone Number:");
		TextField roleField = new TextField();
		roleField.setText(insuranceProvider.getPhoneNumber());
		roleField.setEditable(false);
		gridPane.add(roleLabel, 0, 3);
		gridPane.add(roleField, 1, 3);
		
		Label emailLabel = new Label("Email:");
		TextField emailField = new TextField();
		emailField.setText(insuranceProvider.getEmail());
		emailField.setEditable(false);
		gridPane.add(emailLabel, 0, 4);
		gridPane.add(emailField, 1, 4);
		
		return gridPane;
		
	}
	
	private GridPane showPolicyOwnerProfile() {
		PolicyOwnerController policyOwnerController = PolicyOwnerController.getInstance();
		Optional<PolicyOwner> policyOwnerOptionalOptional = policyOwnerController.read(this.userId);
		if (policyOwnerOptionalOptional.isEmpty()) {
			return new GridPane();
		}
		PolicyOwner policyOwner = policyOwnerOptionalOptional.get();
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.TOP_CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));
		
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(15);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(85);
		gridPane.getColumnConstraints().addAll(column1, column2);
		
		Label userIdLabel = new Label("User ID:");
		TextField userIdField = new TextField();
		userIdField.setText(policyOwner.getCustomerId());
		gridPane.add(userIdLabel, 0, 0);
		gridPane.add(userIdField, 1, 0);
		
		Label userRoleLabel = new Label("Role");
		TextField userRoleField = new TextField();
		userRoleField.setText(this.userRole);
		gridPane.add(userRoleLabel, 0, 1);
		gridPane.add(userRoleField, 1, 1);
		
		Label fullNameLabel = new Label("Full Name:");
		TextField fullNameField = new TextField();
		fullNameField.setText(policyOwner.getFullName());
		fullNameField.setEditable(false);
		gridPane.add(fullNameLabel, 0, 2);
		gridPane.add(fullNameField, 1, 2);
		
		Label roleLabel = new Label("Phone Number:");
		TextField roleField = new TextField();
		roleField.setText(policyOwner.getPhoneNumber());
		roleField.setEditable(false);
		gridPane.add(roleLabel, 0, 3);
		gridPane.add(roleField, 1, 3);
		
		Label addressLabel = new Label("Address:");
		TextField addressField = new TextField();
		addressField.setText(policyOwner.getAddress());
		addressField.setEditable(false);
		gridPane.add(addressLabel, 0, 4);
		gridPane.add(addressField, 1, 4);
		
		Label emailLabel = new Label("Email:");
		TextField emailField = new TextField();
		emailField.setText(policyOwner.getEmail());
		emailField.setEditable(false);
		gridPane.add(emailLabel, 0, 5);
		gridPane.add(emailField, 1, 5);
		
		return gridPane;
	}
	
	private GridPane showBeneficiaryProfile() {
		BeneficiaryController beneficiaryController = BeneficiaryController.getInstance();
		Optional<Beneficiary> beneficiaryOptional = beneficiaryController.read(this.userId);
		if (beneficiaryOptional.isEmpty()) {
			return new GridPane();
		}
		Beneficiary beneficiary = beneficiaryOptional.get();
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.TOP_CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));
		
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(15);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(85);
		gridPane.getColumnConstraints().addAll(column1, column2);
		
		Label userIdLabel = new Label("User ID:");
		TextField userIdField = new TextField();
		userIdField.setText(beneficiary.getCustomerId());
		gridPane.add(userIdLabel, 0, 0);
		gridPane.add(userIdField, 1, 0);
		
		Label userRoleLabel = new Label("Role");
		TextField userRoleField = new TextField();
		userRoleField.setText(this.userRole);
		gridPane.add(userRoleLabel, 0, 1);
		gridPane.add(userRoleField, 1, 1);
		
		Label fullNameLabel = new Label("Full Name:");
		TextField fullNameField = new TextField();
		fullNameField.setText(beneficiary.getFullName());
		fullNameField.setEditable(false);
		gridPane.add(fullNameLabel, 0, 2);
		gridPane.add(fullNameField, 1, 2);
		
		Label roleLabel = new Label("Phone Number:");
		TextField roleField = new TextField();
		roleField.setText(beneficiary.getPhoneNumber());
		roleField.setEditable(false);
		gridPane.add(roleLabel, 0, 3);
		gridPane.add(roleField, 1, 3);
		
		Label insuranceCardNumberLabel = new Label("Insurance Card Number:");
		TextField insuranceCardNumberField = new TextField();
		insuranceCardNumberField.setText(beneficiary.getInsuranceCard().getCardNumber());
		insuranceCardNumberField.setEditable(false);
		gridPane.add(insuranceCardNumberLabel, 0, 4);
		gridPane.add(insuranceCardNumberField, 1, 4);
		
		Label addressLabel = new Label("Address:");
		TextField addressField = new TextField();
		addressField.setText(beneficiary.getAddress());
		addressField.setEditable(false);
		gridPane.add(addressLabel, 0, 5);
		gridPane.add(addressField, 1, 5);
		
		Label emailLabel = new Label("Email:");
		TextField emailField = new TextField();
		emailField.setText(beneficiary.getEmail());
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
	
	private static GridPane addNewBeneficiaryForm(BorderPane root, TableView<Beneficiary> beneficiaryTable) {
		BeneficiaryController beneficiaryController = BeneficiaryController.getInstance();
		
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
		
		Label beneficiaryFullNameLabel = new Label("Full Name:");
		TextField beneficiaryFullNameField = new TextField();
		gridPane.add(beneficiaryFullNameLabel, 0, 0);
		gridPane.add(beneficiaryFullNameField, 1, 0);
		
		Label roleLabel = new Label("Role:");
		ComboBox<String> roleComboBox = new ComboBox<>();
		roleComboBox.getItems().addAll("Dependent", "Policy holder");
		gridPane.add(roleLabel, 0, 1);
		gridPane.add(roleComboBox, 1, 1);
		
		Label policyHolderLabel = new Label("Policy Holder ID (if new beneficiary is a dependent):");
		TextField policyHolderField = new TextField();
		gridPane.add(policyHolderLabel, 0, 2);
		gridPane.add(policyHolderField, 1, 2);
		
		Label phoneNumberLabel = new Label("Phone Number:");
		TextField phoneNumberField = new TextField();
		gridPane.add(phoneNumberLabel, 0, 3);
		gridPane.add(phoneNumberField, 1, 3);
		
		Label addressLabel = new Label("Address:");
		TextField addressField = new TextField();
		gridPane.add(addressLabel, 0, 4);
		gridPane.add(addressField, 1, 4);
		
		Label emailLabel = new Label("Email:");
		TextField emailField = new TextField();
		gridPane.add(emailLabel, 0, 5);
		gridPane.add(emailField, 1, 5);
		
		Button submitButton = new Button("Submit");
		gridPane.add(submitButton, 1, 6);
		
		String fullName = beneficiaryFullNameField.getText();
		String newRole = roleComboBox.getValue();
		String policyHolderId = policyHolderField.getText();
		String phoneNumber = phoneNumberField.getText();
		String address = addressField.getText();
		String email = emailField.getText();
		
		submitButton.setOnAction(e -> {
			Beneficiary newBeneficiary = new Beneficiary();
			newBeneficiary.setFullName(fullName);
			newBeneficiary.setPhoneNumber(phoneNumber);
			newBeneficiary.setAddress(address);
			newBeneficiary.setEmail(email);
			newBeneficiary.setIsPolicyHolder(!Objects.equals(newRole, "Dependent"));
			if (!newBeneficiary.isPolicyHolder()) {
				beneficiaryController.read(policyHolderId).ifPresent(newBeneficiary::setPolicyHolder);
			}
			
			beneficiaryController.create(newBeneficiary);
			root.setCenter(beneficiaryTable);
		});
		
		return gridPane;
	}
	
	private GridPane getPaymentContent() {
		PolicyOwnerController policyOwnerController = PolicyOwnerController.getInstance();
		Optional<PolicyOwner> policyOwnerOptional = policyOwnerController.read(userId);
		
		if (policyOwnerOptional.isEmpty()) {
			return new GridPane();
		}
		PolicyOwner policyOwner = policyOwnerOptional.get();
		long totalPayment = ClaimCalculator.calculateYearlyClaimPayment(policyOwner);
		
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
		
		Label paymentIdLabel = new Label("Total Yearly Payment:");
		TextField paymentIdField = new TextField();
		paymentIdField.setText(String.valueOf(totalPayment));
		gridPane.add(paymentIdLabel, 0, 0);
		gridPane.add(paymentIdField, 1, 0);
		
		return gridPane;
	}
	
	private static GridPane getDependentTable(BorderPane root) {
		if (userId == null) {
			return new GridPane();
		}
		
		if (!Objects.equals(userRole, "Policy holder")) {
			return new GridPane();
		}
		
		BeneficiaryController beneficiaryController = BeneficiaryController.getInstance();
		Optional<Beneficiary> policyHolderOptional = beneficiaryController.read(userId);
		if (policyHolderOptional.isEmpty()) {
			return new GridPane();
		}
		Beneficiary policyHolder = policyHolderOptional.get();
		
		List<Beneficiary> dependents = policyHolder.getDependents();
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.TOP_CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));
		
		ColumnConstraints column = new ColumnConstraints();
		column.setPercentWidth(100);
		gridPane.getColumnConstraints().addAll(column);
		
		TableView<Beneficiary> table = new TableView<>();
		
		TableColumn<Beneficiary, String> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
		
		TableColumn<Beneficiary, String> fullNameColumn = new TableColumn<>("Full Name");
		fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
		
		TableColumn<Beneficiary, String> phoneNumberColumn = new TableColumn<>("Phone Number");
		phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		
		TableColumn<Beneficiary, String> insuranceCardNumberColumn = new TableColumn<>("Insurance Card Number");
		insuranceCardNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInsuranceCard().getCardNumber()));
		
		TableColumn<Beneficiary, String> addressColumn = new TableColumn<>("Address");
		addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		
		TableColumn<Beneficiary, String> emailColumn = new TableColumn<>("Email");
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		
		table.getColumns().add(idColumn);
		table.getColumns().add(fullNameColumn);
		table.getColumns().add(phoneNumberColumn);
		table.getColumns().add(insuranceCardNumberColumn);
		table.getColumns().add(addressColumn);
		table.getColumns().add(emailColumn);
		
		ObservableList<Beneficiary> dependentsData = FXCollections.observableArrayList(dependents);
		table.setItems(dependentsData);
		
		gridPane.add(table, 0, 0);
		
		return gridPane;
	}
}