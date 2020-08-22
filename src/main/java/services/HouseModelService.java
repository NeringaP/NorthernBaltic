package services;

import alert.Alerts;
import dao.HouseModelDao;
import domain.HouseModel;
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

public class HouseModelService {
    Stage houseModelWindow, editInfoWindow;
    TableView tableView;
    TextField nameInput, priceInput, areaInput, nameTextField, priceTextField, areaTextField;
    CheckBox garageInput, garageCheckBox;

    HouseModelDao houseModelDao = new HouseModelDao();
    Alerts alert = new Alerts();

    public void openHouseModelWindow(Button button, Stage stage) {

        houseModelWindow = new Stage();
        houseModelWindow.setMinWidth(900);
        houseModelWindow.setHeight(500);
        houseModelWindow.setTitle("House models table");

        houseModelWindow.initModality(Modality.WINDOW_MODAL);
        houseModelWindow.initOwner(stage);
        houseModelWindow.setX(stage.getX() + 200);
        houseModelWindow.setY(stage.getY() + 100);

        button.setOnMouseClicked(mouseEvent -> {

            tableView = getTableView();

            HBox serviceBox = getServiceBox();

            VBox vBox = new VBox();
            vBox.getChildren().addAll(tableView, serviceBox);

            Scene houseModelScene = new Scene(vBox);
            houseModelWindow.setScene(houseModelScene);

            fillTableWithDataFromDB();

            houseModelWindow.show();
        });
    }

    private void fillTableWithDataFromDB() {
        for (HouseModel models : houseModelDao.getAllModels()) {
            tableView.getItems().addAll(models);
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

        TableColumn priceColumn = new TableColumn("Price($)");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn areaColumn = new TableColumn("Area(m2)");
        areaColumn.setMinWidth(100);
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));

        TableColumn garageColumn = new TableColumn("Has garage");
        garageColumn.setMinWidth(100);
        garageColumn.setCellValueFactory(new PropertyValueFactory<>("hasGarage"));

        tableView.getColumns().addAll(idColumn, nameColumn, priceColumn, areaColumn, garageColumn);
        return tableView;
    }

    private HBox getServiceBox() {

        nameInput = new TextField();
        nameInput.setPromptText("Name");

        priceInput = new TextField();
        priceInput.setPromptText("Price");

        areaInput = new TextField();
        areaInput.setPromptText("Area");

        Label garageLabel = new Label("Has garage");
        garageInput = new CheckBox();

        Button addButton = new Button("Add");
        addButton.setOnMouseClicked(mouseEvent -> addInfo());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnMouseClicked(event -> invokeDeleteAlert());

        Button updateButton = new Button("Edit");
        updateButton.setOnAction(event -> openEditInfoWindow());

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(nameInput, priceInput, areaInput, garageLabel, garageInput, addButton,
                updateButton, deleteButton);
        return hBox;
    }

    private void addInfo() {
        if (!nameInput.getText().isEmpty() && !priceInput.getText().isEmpty() && !areaInput.getText().isEmpty()) {
            String modelName = nameInput.getText();
            Integer price = Integer.parseInt(priceInput.getText());
            Integer area = Integer.parseInt(areaInput.getText());
            boolean garage = garageInput.isSelected();

            HouseModel newHouseModel = new HouseModel(modelName, price, area, garage);
            houseModelDao.addHouseModel(newHouseModel);
            tableView.getItems().addAll(newHouseModel);

            nameInput.clear();
            priceInput.clear();
            areaInput.clear();
            garageInput.setSelected(false);
        } else {
            alert.invokeEmptyFieldsAlert();
        }
    }

    private void invokeDeleteAlert() {
        HouseModel selectedHouseModelToDelete = (HouseModel) tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " +
                selectedHouseModelToDelete.getName() + " model?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Delete house model");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {
            alert.close();
            houseModelDao.deleteHouseModel(selectedHouseModelToDelete);
            tableView.getItems().remove(selectedHouseModelToDelete);
        } else if (result.get() == ButtonType.NO) {
            alert.close();
        }
    }

    private void openEditInfoWindow() {

        editInfoWindow = new Stage();
        editInfoWindow.setWidth(500);
        editInfoWindow.setHeight(300);
        editInfoWindow.setTitle("Edit house models");

        editInfoWindow.initModality(Modality.WINDOW_MODAL);
        editInfoWindow.initOwner(houseModelWindow);
        editInfoWindow.setX(houseModelWindow.getX() + 200);
        editInfoWindow.setY(houseModelWindow.getY() + 100);

        HouseModel selectedHouseModelToEdit = (HouseModel) tableView.getSelectionModel().getSelectedItem();
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.TOP_CENTER);

        Label nameLabel = new Label("Name:");
        Label priceLabel = new Label("Price:");
        Label moneyLabel = new Label("$");
        Label areaLabel = new Label("Area:");
        Label squareMLabel = new Label("m\u00B2");
        Label garageLabel = new Label("Has garage");

        nameTextField = new TextField();
        nameTextField.setMaxWidth(100);
        nameTextField.setText(selectedHouseModelToEdit.getName());

        priceTextField = new TextField();
        priceTextField.setMaxWidth(100);
        priceTextField.setText(String.valueOf(selectedHouseModelToEdit.getPrice()));

        areaTextField = new TextField();
        areaTextField.setMaxWidth(100);
        areaTextField.setText(String.valueOf(selectedHouseModelToEdit.getArea()));

        garageCheckBox = new CheckBox();
        garageCheckBox.setSelected(selectedHouseModelToEdit.getHasGarage());

        Button updateButton = new Button("Update");

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameTextField, 1, 0);
        gridPane.add(priceLabel, 0, 1);
        gridPane.add(priceTextField, 1, 1);
        gridPane.add(moneyLabel, 2, 1);
        gridPane.add(areaLabel, 0, 2);
        gridPane.add(areaTextField, 1, 2);
        gridPane.add(squareMLabel, 2, 2);
        gridPane.add(garageLabel, 0, 3);
        gridPane.add(garageCheckBox, 1, 3);
        gridPane.add(updateButton, 1, 4);

        updateButton.setOnMouseClicked(event -> updateInfo(selectedHouseModelToEdit));

        Scene scene = new Scene(gridPane);
        editInfoWindow.setScene(scene);
        editInfoWindow.show();
    }

    private void updateInfo(HouseModel selectedHouseModelToEdit) {
        String modelName = nameTextField.getText();
        Integer price = Integer.parseInt(priceTextField.getText());
        Integer area = Integer.parseInt(areaTextField.getText());
        boolean garage = garageCheckBox.isSelected();

        selectedHouseModelToEdit.setName(modelName);
        selectedHouseModelToEdit.setPrice(price);
        selectedHouseModelToEdit.setArea(area);
        selectedHouseModelToEdit.setHasGarage(garage);

        houseModelDao.updateHouseModel(selectedHouseModelToEdit);
        tableView.refresh();
        editInfoWindow.close();
    }
}
