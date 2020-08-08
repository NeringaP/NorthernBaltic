package sample;

import domain.Customer;
import domain.Person;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class test2 extends Application {

    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.TOP_CENTER);

        Label nameLabel = new Label("First name:");
        Label lastNameLabel = new Label("Last name:");
        Label phoneNumberLabel = new Label("Phone number:");

        TextField nameTextField = new TextField();
        nameTextField.setMaxWidth(100);

        TextField lastNameTextField = new TextField();
        lastNameTextField.setMaxWidth(100);

        TextField phoneNumberField = new TextField();
        lastNameTextField.setMaxWidth(100);



        Button addUserButton = new Button("Add user");
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameTextField, 1, 0);
        gridPane.add(lastNameLabel, 0, 1);
        gridPane.add(lastNameTextField, 1, 1);
        gridPane.add(phoneNumberLabel, 0, 2);
        gridPane.add(phoneNumberField, 1, 2);
        gridPane.add(addUserButton, 1, 3);
        //setOnMouseClicked method defines what happens when the button is clicked.
        addUserButton.setOnMouseClicked(event -> {
            System.out.println("Add users button WAS CLICKED!");

            String name = nameTextField.getText();
            System.out.println("Entered first name was: " + name);

            String lastName = lastNameTextField.getText();
            System.out.println("Entered last name was: " + lastName);

            String phoneNumber = phoneNumberField.getText();
            System.out.println("Entered phone number was: " + phoneNumber);

            Person customer = new Person(name, lastName, phoneNumber);
            System.out.println(customer);

            Label label = new Label("Nice!");
            gridPane.add(label, 0, 3);
        });

        Scene scene = new Scene(gridPane);
        primaryStage.setWidth(900);
        primaryStage.setHeight(500);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}

