package services;

import alert.Alerts;
import dao.CustomerDao;
import dao.EngineerDao;
import dao.HouseModelDao;
import dao.ProjectDao;
import domain.Customer;
import domain.Engineer;
import domain.HouseModel;
import domain.Project;
import javafx.beans.property.SimpleObjectProperty;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ProjectService {

    Stage projectWindow, editInfoWindow;
    TableView tableView;
    TextField idInput, addressInput, dueDateInput, idTextField, addressTextField, dueDateTextField;
    ComboBox<String> houseModelInput, customerInput, engineerInput, houseModelComboBox, customerComboBox,
            engineerComboBox;

    Alerts alert = new Alerts();
    ProjectDao projectDao = new ProjectDao();
    HouseModelDao houseModelDao = new HouseModelDao();
    CustomerDao customerDao = new CustomerDao();
    EngineerDao engineerDao = new EngineerDao();

    ObservableList<String> houseModelsNames = FXCollections.observableArrayList();
    ObservableList<String> customersLastNames = FXCollections.observableArrayList();
    ObservableList<String> engineersLastNames = FXCollections.observableArrayList();

    public void openProjectWindow(Button button, Stage primaryStage) {

        houseModelsNames.addAll(houseModelDao.getAllModelsNames());
        customersLastNames.addAll(customerDao.getAllCustomersLastName());
        engineersLastNames.addAll(engineerDao.getAllEngineersLastName());

        projectWindow = new Stage();
        projectWindow.setMinWidth(900);
        projectWindow.setHeight(500);
        projectWindow.setTitle("Projects table");

        projectWindow.initModality(Modality.WINDOW_MODAL);
        projectWindow.initOwner(primaryStage);
        projectWindow.setX(primaryStage.getX() - 200);
        projectWindow.setY(primaryStage.getY() + 100);

        button.setOnMouseClicked(mouseEvent -> {

            tableView = getTableView();

            HBox serviceBox = getServiceBox();

            VBox vBox = new VBox();
            vBox.getChildren().addAll(tableView, serviceBox);

            Scene tableScene = new Scene(vBox);
            projectWindow.setScene(tableScene);

            fillTableWithDataFromDB();

            projectWindow.show();
        });
    }

    private void fillTableWithDataFromDB() {
        for (Project project : projectDao.getAllProjects()) {
            tableView.getItems().addAll(project);
        }
    }

    private TableView getTableView() {

        tableView = new TableView();

        TableColumn idColumn = new TableColumn("ID number");
        idColumn.setMinWidth(90);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn addressColumn = new TableColumn("Address");
        addressColumn.setMinWidth(300);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn dueDateColumn = new TableColumn("Due date");
        dueDateColumn.setMinWidth(90);
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        TableColumn<Project, String> modelNameColumn = new TableColumn<>("Model");
        modelNameColumn.setMinWidth(110);
        modelNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, String>,
                ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Project, String> param) {
                return new SimpleObjectProperty<>(param.getValue().getHouseModel().getName());
            }
        });

        TableColumn<Project, String> customerLastNameColumn = new TableColumn<>("Customer last name");
        customerLastNameColumn.setMinWidth(200);
        customerLastNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, String>,
                ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Project, String> param) {
                return new SimpleObjectProperty<>(param.getValue().getCustomer().getLastName());
            }
        });

        TableColumn<Project, String> engineerLastNameColumn = new TableColumn<>("Engineer last name");
        engineerLastNameColumn.setMinWidth(200);
        engineerLastNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, String>,
                ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Project, String> param) {
                return new SimpleObjectProperty<>(param.getValue().getEngineer().getLastName());
            }
        });

        tableView.getColumns().addAll(idColumn, addressColumn, dueDateColumn, modelNameColumn, customerLastNameColumn,
                engineerLastNameColumn);
        return tableView;
    }

    private HBox getServiceBox() {
        idInput = new TextField();
        idInput.setPromptText("ID number");

        addressInput = new TextField();
        addressInput.setPromptText("Address");

        dueDateInput = new TextField();
        dueDateInput.setPromptText("Due date yyyy-mm-dd");

        houseModelInput = new ComboBox(houseModelsNames);
        houseModelInput.setPromptText("House model");

        customerInput = new ComboBox(customersLastNames);
        customerInput.setPromptText("Customer");

        engineerInput = new ComboBox(engineersLastNames);
        engineerInput.setPromptText("Engineer");

        Button addButton = new Button("Add");
        addButton.setOnMouseClicked(mouseEvent -> addInfo());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnMouseClicked(event -> invokeDeleteAlert());

        Button updateButton = new Button("Edit");
        updateButton.setOnAction(event -> openEditInfoWindow());

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(idInput, addressInput, dueDateInput, houseModelInput, customerInput,
        engineerInput, addButton, updateButton, deleteButton);
        return hBox;
    }

    private void addInfo() {
        if(!idInput.getText().isEmpty() && !addressInput.getText().isEmpty() && !dueDateInput.getText().isEmpty() &&
        !houseModelInput.getValue().isEmpty() && !customerInput.getValue().isEmpty() && !engineerInput.getValue().isEmpty()) {
            Integer id = Integer.parseInt(idInput.getText());
            String address = addressInput.getText();
            LocalDate dueDate = LocalDate.parse(dueDateInput.getText());
            String houseModelName = houseModelInput.getValue();
            String customerLastName = customerInput.getValue();
            String engineerLastName = engineerInput.getValue();

            Project newProject = new Project(id, address, dueDate);
            newProject.setHouseModel(getHouseModelByName(houseModelName));
            newProject.setCustomer(getCustomerByLastName(customerLastName));
            newProject.setEngineer(getEngineerByLastName(engineerLastName));
            projectDao.addProject(newProject);
            tableView.getItems().addAll(newProject);

            idInput.clear();
            addressInput.clear();
            dueDateInput.clear();
            houseModelInput.setValue(null);
            customerInput.setValue(null);
            engineerInput.setValue(null);
        } else {
            alert.invokeEmptyFieldsAlert();
        }
    }

    private HouseModel getHouseModelByName(String houseModelName) {
        HouseModel houseModel = null;
        for (HouseModel model : houseModelDao.getAllModels()) {
            if (model.getName().equalsIgnoreCase(houseModelName)) {
                houseModel = model;
            }
        }
        return houseModel;
    }

    private Customer getCustomerByLastName(String customerLastName) {
        Customer customer = null;
        for (Customer singleCustomer : customerDao.getAllCustomers()) {
            if(singleCustomer.getLastName().equalsIgnoreCase(customerLastName)) {
                customer = singleCustomer;
            }
        }
        return customer;
    }

    private Engineer getEngineerByLastName(String engineerLastName) {
        Engineer engineer = null;
        for (Engineer singleEngineer : engineerDao.getAllEngineers()) {
            if(singleEngineer.getLastName().equalsIgnoreCase(engineerLastName)) {
                engineer = singleEngineer;
            }
        }
        return engineer;
    }

    private void invokeDeleteAlert() {
        Project selectedProjectToDelete = (Project) tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete project " +
                selectedProjectToDelete.getId() + "?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Delete project");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {
            alert.close();
            projectDao.deleteProject(selectedProjectToDelete);
            tableView.getItems().remove(selectedProjectToDelete);
        } else if (result.get() == ButtonType.NO) {
            alert.close();
        }
    }

    private void openEditInfoWindow() {

        editInfoWindow = new Stage();
        editInfoWindow.setWidth(500);
        editInfoWindow.setHeight(500);
        editInfoWindow.setTitle("Edit project");

        editInfoWindow.initModality(Modality.WINDOW_MODAL);
        editInfoWindow.initOwner(projectWindow);
        editInfoWindow.setX(projectWindow.getX() + 200);
        editInfoWindow.setY(projectWindow.getY() + 100);

        Project selectedProjectToEdit = (Project) tableView.getSelectionModel().getSelectedItem();
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.TOP_CENTER);

        Label idLabel = new Label("ID number:");
        Label addressLabel = new Label("Address:");
        Label dueDateLabel = new Label("Due date:");
        Label houseModelLabel = new Label("House model:");
        Label customerLabel = new Label("Customer:");
        Label engineerLabel = new Label("Engineer:");

        idTextField = new TextField();
        idTextField.setMaxWidth(130);
        idTextField.setText(Integer.toString(selectedProjectToEdit.getId()));

        addressTextField = new TextField();
        addressTextField.setMaxWidth(300);
        addressTextField.setText(selectedProjectToEdit.getAddress());

        dueDateTextField = new TextField();
        dueDateTextField.setMaxWidth(130);
        dueDateTextField.setText(String.valueOf(selectedProjectToEdit.getDueDate()));

        houseModelComboBox = new ComboBox<>(houseModelsNames);
        houseModelComboBox.setMaxWidth(200);
        houseModelComboBox.setValue(selectedProjectToEdit.getHouseModel().getName());

        customerComboBox = new ComboBox<>(customersLastNames);
        customerComboBox.setMaxWidth(200);
        customerComboBox.setValue(selectedProjectToEdit.getCustomer().getLastName());

        engineerComboBox = new ComboBox<>(engineersLastNames);
        engineerComboBox.setMaxWidth(200);
        engineerComboBox.setValue(selectedProjectToEdit.getEngineer().getLastName());

        Button updateButton = new Button("Update");

        gridPane.add(idLabel, 0, 0);
        gridPane.add(idTextField, 1, 0);
        gridPane.add(addressLabel, 0, 1);
        gridPane.add(addressTextField, 1, 1);
        gridPane.add(dueDateLabel, 0, 2);
        gridPane.add(dueDateTextField, 1, 2);
        gridPane.add(houseModelLabel, 0, 3);
        gridPane.add(houseModelComboBox, 1, 3);
        gridPane.add(customerLabel, 0, 4);
        gridPane.add(customerComboBox, 1, 4);
        gridPane.add(engineerLabel, 0, 5);
        gridPane.add(engineerComboBox, 1, 5);
        gridPane.add(updateButton, 1, 6);

        updateButton.setOnMouseClicked(event -> updateInfo(selectedProjectToEdit));

        Scene scene = new Scene(gridPane);
        editInfoWindow.setScene(scene);
        editInfoWindow.show();
    }

    private void updateInfo(Project selectedProjectToEdit) {
        Integer id = Integer.parseInt(idTextField.getText());
        String address = addressTextField.getText();
        LocalDate dueDate = LocalDate.parse(dueDateTextField.getText());
        String houseModelName = houseModelComboBox.getValue();
        String customerLastName = customerComboBox.getValue();
        String engineerLastName = engineerComboBox.getValue();

        selectedProjectToEdit.setId(id);
        selectedProjectToEdit.setAddress(address);
        selectedProjectToEdit.setDueDate(dueDate);
        selectedProjectToEdit.setHouseModel(getHouseModelByName(houseModelName));
        selectedProjectToEdit.setCustomer(getCustomerByLastName(customerLastName));
        selectedProjectToEdit.setEngineer(getEngineerByLastName(engineerLastName));

        projectDao.updateProject(selectedProjectToEdit);
        tableView.refresh();
        editInfoWindow.close();
    }

}
