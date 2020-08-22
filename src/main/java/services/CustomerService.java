package services;

import alert.Alerts;
import dao.CustomerDao;
import domain.Customer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class CustomerService {

    Stage customerWindow, editInfoWindow;
    TableView tableView;
    TextField nameInput, lastNameInput, phoneNumberInput, nameTextField, lastNameTextField, phoneNumberTextField;

    CustomerDao customerDao = new CustomerDao();
    Alerts alert = new Alerts();

    public void openCustomerWindow(Button button, Stage primaryStage) {

        customerWindow = new Stage();
        customerWindow.setMinWidth(900);
        customerWindow.setHeight(500);
        customerWindow.setTitle("Customers table");

        customerWindow.initModality(Modality.WINDOW_MODAL);
        customerWindow.initOwner(primaryStage);
        customerWindow.setX(primaryStage.getX() + 200);
        customerWindow.setY(primaryStage.getY() + 100);

        button.setOnMouseClicked(mouseEvent -> {

            tableView = getTableView();

            HBox serviceBox = getServiceBox();

            VBox vBox = new VBox();
            vBox.getChildren().addAll(tableView, serviceBox);

            Scene tableScene = new Scene(vBox);
            customerWindow.setScene(tableScene);

            fillTableWithDataFromDB();

            customerWindow.show();
        });
    }

    private void fillTableWithDataFromDB() {
        for (Customer customer : customerDao.getAllCustomers()) {
            tableView.getItems().addAll(customer);
        }
    }

    private TableView getTableView() {

        tableView = new TableView();

        TableColumn idColumn = new TableColumn("Id");
        idColumn.setMinWidth(90);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn lastNameColumn = new TableColumn("Last name");
        lastNameColumn.setMinWidth(200);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn phoneNumberColumn = new TableColumn("Phone number");
        phoneNumberColumn.setMinWidth(200);
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        tableView.getColumns().addAll(idColumn, nameColumn, lastNameColumn, phoneNumberColumn);
        return tableView;
    }

    private HBox getServiceBox() {
        nameInput = new TextField();
        nameInput.setPromptText("Name");

        lastNameInput = new TextField();
        lastNameInput.setPromptText("Last name");

        phoneNumberInput = new TextField();
        phoneNumberInput.setPromptText("+370 xxxx xxxx");

        Button addButton = new Button("Add");
        addButton.setOnMouseClicked(mouseEvent -> addInfo());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnMouseClicked(event -> invokeDeleteAlert());

        Button updateButton = new Button("Edit");
        updateButton.setOnAction(event -> openEditInfoWindow());

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(nameInput, lastNameInput, phoneNumberInput, addButton,
                updateButton, deleteButton);
        return hBox;
    }

    private void addInfo() {
        if(!nameInput.getText().isEmpty() && !lastNameInput.getText().isEmpty() && !phoneNumberInput.getText().isEmpty()) {
            String customerName = nameInput.getText();
            String customerLastName = lastNameInput.getText();
            String phoneNumber = phoneNumberInput.getText();

            Customer newCustomer = new Customer(customerName, customerLastName, phoneNumber);
            customerDao.addCustomer(newCustomer);
            tableView.getItems().addAll(newCustomer);

            nameInput.clear();
            lastNameInput.clear();
            phoneNumberInput.clear();
        } else {
            alert.invokeEmptyFieldsAlert();
        }
    }

    private void invokeDeleteAlert() {
        Customer selectedCustomerToDelete = (Customer) tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete customer " +
                selectedCustomerToDelete.getName() + " " + selectedCustomerToDelete.getLastName() + "?", ButtonType.YES,
                ButtonType.NO);
        alert.setTitle("Delete customer");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {
            alert.close();
            customerDao.deleteCustomer(selectedCustomerToDelete);
            tableView.getItems().remove(selectedCustomerToDelete);
        } else if (result.get() == ButtonType.NO) {
            alert.close();
        }
    }

    private void openEditInfoWindow() {

        editInfoWindow = new Stage();
        editInfoWindow.setWidth(500);
        editInfoWindow.setHeight(300);
        editInfoWindow.setTitle("Edit customer");

        editInfoWindow.initModality(Modality.WINDOW_MODAL);
        editInfoWindow.initOwner(customerWindow);
        editInfoWindow.setX(customerWindow.getX() + 200);
        editInfoWindow.setY(customerWindow.getY() + 100);

        Customer selectedCustomerToEdit = (Customer) tableView.getSelectionModel().getSelectedItem();
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.TOP_CENTER);

        Label nameLabel = new Label("Name:");
        Label lastNameLabel = new Label("Last name:");
        Label phoneNumberLabel = new Label("Phone number:");

        nameTextField = new TextField();
        nameTextField.setMaxWidth(100);
        nameTextField.setText(selectedCustomerToEdit.getName());

        lastNameTextField = new TextField();
        lastNameTextField.setMaxWidth(100);
        lastNameTextField.setText(selectedCustomerToEdit.getLastName());

        phoneNumberTextField = new TextField();
        phoneNumberTextField.setMaxWidth(100);
        phoneNumberTextField.setText(selectedCustomerToEdit.getPhoneNumber());

        Button updateButton = new Button("Update");

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameTextField, 1, 0);
        gridPane.add(lastNameLabel, 0, 1);
        gridPane.add(lastNameTextField, 1, 1);
        gridPane.add(phoneNumberLabel, 0, 2);
        gridPane.add(phoneNumberTextField, 1, 2);
        gridPane.add(updateButton, 1, 3);

        updateButton.setOnMouseClicked(event -> updateInfo(selectedCustomerToEdit));

        Scene scene = new Scene(gridPane);
        editInfoWindow.setScene(scene);
        editInfoWindow.show();
    }

    private void updateInfo(Customer selectedCustomerToEdit) {
        String customerName = nameTextField.getText();
        String customerLastName = lastNameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();

        selectedCustomerToEdit.setName(customerName);
        selectedCustomerToEdit.setLastName(customerLastName);
        selectedCustomerToEdit.setPhoneNumber(phoneNumber);

        customerDao.updateCustomer(selectedCustomerToEdit);
        tableView.refresh();
        editInfoWindow.close();
    }
}
