package repositories;

import domain.HouseModel;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtils;


public class HouseModelsRepo extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.TOP_CENTER);

        Label nameLabel = new Label("Model name:");
        Label priceLabel = new Label("Price:");
        Label areaLabel = new Label("Area:");
        Label garageLabel = new Label("Has Garage:");


        TextField nameTextField = new TextField();
        nameTextField.setMaxWidth(100);

        TextField priceTextField = new TextField();
        priceTextField.setMaxWidth(100);

        TextField areaTextField = new TextField();
        areaTextField.setMaxWidth(100);

        ComboBox<String> garageComboBox = new ComboBox<>();
        garageComboBox.getItems().addAll("Garage", "No garage");


        Button addModelButton = new Button("Add house model");
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameTextField, 1, 0);
        gridPane.add(priceLabel, 0, 1);
        gridPane.add(priceTextField, 1, 1);
        gridPane.add(areaLabel, 0, 2);
        gridPane.add(areaTextField, 1, 2);
        gridPane.add(garageLabel, 0, 3);
        gridPane.add(garageComboBox, 1, 3);
        gridPane.add(addModelButton, 1, 4);
        //setOnMouseClicked method defines what happens when the button is clicked.
        addModelButton.setOnMouseClicked(event -> {
            String modelName = nameTextField.getText();
            Integer price = Integer.parseInt(priceTextField.getText());
            Integer area = Integer.parseInt(areaTextField.getText());
            String garage = garageComboBox.getValue();

            Label label = new Label("Good job!");
            gridPane.add(label, 0, 4);

            boolean hasGarage = isHasGarage(garage);
            HouseModel houseModel = new HouseModel(modelName, price, area, hasGarage);

            Session session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(houseModel);
                transaction.commit();
                session.close();
            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                ex.printStackTrace();
            }


        });

        Scene scene = new Scene(gridPane);
        primaryStage.setWidth(900);
        primaryStage.setHeight(500);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private boolean isHasGarage(String garage) {
        if (garage.equals("Garage")) {
            return true;
        } else {
            return false;
        }
    }
}


