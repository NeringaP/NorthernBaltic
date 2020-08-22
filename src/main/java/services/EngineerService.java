package services;

import alert.Alerts;
import dao.EngineerDao;
import dao.ProjectDao;
import domain.Engineer;
import domain.Project;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.util.Callback;

import java.util.Optional;

public class EngineerService {

    Stage engineerWindow, editInfoWindow;
    TableView tableView;
    TextField nameInput, lastNameInput,  phoneNumberInput, nameTextField, lastNameTextField, phoneNumberTextField;
    ComboBox<String> positionInput, positionComboBox;

    EngineerDao engineerDao = new EngineerDao();
    Alerts alert = new Alerts();



    public void openEngineerWindow(Button button, Stage primaryStage) {

        engineerWindow = new Stage();
        engineerWindow.setMinWidth(900);
        engineerWindow.setHeight(500);
        engineerWindow.setTitle("Engineers table");

        engineerWindow.initModality(Modality.WINDOW_MODAL);
        engineerWindow.initOwner(primaryStage);
        engineerWindow.setX(primaryStage.getX() + 200);
        engineerWindow.setY(primaryStage.getY() + 100);

        button.setOnMouseClicked(mouseEvent -> {

            tableView = getTableView();

            HBox serviceBox = getServiceBox();

            VBox vBox = new VBox();
            vBox.getChildren().addAll(tableView, serviceBox);

            Scene tableScene = new Scene(vBox);
            engineerWindow.setScene(tableScene);

            fillTableWithDataFromDB();

            engineerWindow.show();
        });
    }

    private void fillTableWithDataFromDB() {
        for (Engineer engineer : engineerDao.getAllEngineers()) {
            tableView.getItems().addAll(engineer);
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

        TableColumn positionColumn = new TableColumn("Position");
        positionColumn.setMinWidth(200);
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));

        tableView.getColumns().addAll(idColumn, nameColumn, lastNameColumn, phoneNumberColumn, positionColumn);
        return tableView;
    }

    private HBox getServiceBox() {
        nameInput = new TextField();
        nameInput.setPromptText("Name");

        lastNameInput = new TextField();
        lastNameInput.setPromptText("Last name");

        phoneNumberInput = new TextField();
        phoneNumberInput.setPromptText("+370 xxxx xxxx");

        positionInput = new ComboBox();
        positionInput.setPromptText("Position");
        positionInput.getItems().addAll("Lead engineer", "Engineer", "Draftsman");

        Button addButton = new Button("Add");
        addButton.setOnMouseClicked(mouseEvent -> addInfo());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnMouseClicked(event -> invokeDeleteAlert());

        Button updateButton = new Button("Edit");
        updateButton.setOnAction(event -> openEditInfoWindow());

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(nameInput, lastNameInput, phoneNumberInput, positionInput, addButton,
                updateButton, deleteButton);
        return hBox;
    }

    private void addInfo() {
        if (!nameInput.getText().isEmpty() && !lastNameInput.getText().isEmpty() && !phoneNumberInput.getText().isEmpty()
                && !positionInput.getValue().isEmpty()) {
            String engineerName = nameInput.getText();
            String engineerLastName = lastNameInput.getText();
            String phoneNumber = phoneNumberInput.getText();
            String position = positionInput.getValue();

            Engineer newEngineer = new Engineer(engineerName, engineerLastName, phoneNumber, position);
            engineerDao.addEngineer(newEngineer);
            tableView.getItems().addAll(newEngineer);

            nameInput.clear();
            lastNameInput.clear();
            phoneNumberInput.clear();
            positionInput.setValue(null);
        } else {
            alert.invokeEmptyFieldsAlert();
        }
    }

    private void invokeDeleteAlert() {
        Engineer selectedEngineerToDelete = (Engineer) tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete engineer " +
                selectedEngineerToDelete.getName() + " " + selectedEngineerToDelete.getLastName() + "?", ButtonType.YES,
                ButtonType.NO);
        alert.setTitle("Delete engineer");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {
            alert.close();
            engineerDao.deleteEngineer(selectedEngineerToDelete);
            tableView.getItems().remove(selectedEngineerToDelete);
        } else if (result.get() == ButtonType.NO) {
            alert.close();
        }
    }

    private void openEditInfoWindow() {

        editInfoWindow = new Stage();
        editInfoWindow.setWidth(500);
        editInfoWindow.setHeight(300);
        editInfoWindow.setTitle("Edit engineer");

        editInfoWindow.initModality(Modality.WINDOW_MODAL);
        editInfoWindow.initOwner(engineerWindow);
        editInfoWindow.setX(engineerWindow.getX() + 200);
        editInfoWindow.setY(engineerWindow.getY() + 100);

        Engineer selectedEngineerToEdit = (Engineer) tableView.getSelectionModel().getSelectedItem();
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.TOP_CENTER);

        Label nameLabel = new Label("Name:");
        Label lastNameLabel = new Label("Last name:");
        Label phoneNumberLabel = new Label("Phone number:");
        Label positionLabel = new Label("Position:");

        nameTextField = new TextField();
        nameTextField.setMaxWidth(130);
        nameTextField.setText(selectedEngineerToEdit.getName());

        lastNameTextField = new TextField();
        lastNameTextField.setMaxWidth(130);
        lastNameTextField.setText(selectedEngineerToEdit.getLastName());

        phoneNumberTextField = new TextField();
        phoneNumberTextField.setMaxWidth(130);
        phoneNumberTextField.setText(selectedEngineerToEdit.getPhoneNumber());

        positionComboBox = new ComboBox<>();
        positionComboBox.setValue(selectedEngineerToEdit.getPosition());
        positionComboBox.getItems().addAll("Lead engineer", "Engineer", "Draftsman");

        Button updateButton = new Button("Update");

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameTextField, 1, 0);
        gridPane.add(lastNameLabel, 0, 1);
        gridPane.add(lastNameTextField, 1, 1);
        gridPane.add(phoneNumberLabel, 0, 2);
        gridPane.add(phoneNumberTextField, 1, 2);
        gridPane.add(positionLabel, 0, 3);
        gridPane.add(positionComboBox, 1, 3);
        gridPane.add(updateButton, 1, 4);

        updateButton.setOnMouseClicked(event -> updateInfo(selectedEngineerToEdit));

        Scene scene = new Scene(gridPane);
        editInfoWindow.setScene(scene);
        editInfoWindow.show();
    }

    private void updateInfo(Engineer selectedEngineerToEdit) {
        String engineerName = nameTextField.getText();
        String engineerLastName = lastNameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String position = positionComboBox.getValue();

        selectedEngineerToEdit.setName(engineerName);
        selectedEngineerToEdit.setLastName(engineerLastName);
        selectedEngineerToEdit.setPhoneNumber(phoneNumber);
        selectedEngineerToEdit.setPosition(position);

        engineerDao.updateEngineer(selectedEngineerToEdit);
        tableView.refresh();
        editInfoWindow.close();
    }
}
