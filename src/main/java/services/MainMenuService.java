package services;

import alert.Alerts;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import services.CustomerService;
import services.EngineerService;
import services.HouseModelService;
import services.ProjectService;


public class MainMenuService {

    public static void openMainMenu (Stage primaryStage){
        Alerts alert = new Alerts();

        primaryStage.setTitle("Main menu");

        GridPane mainGridPane = new GridPane();
        mainGridPane.setHgap(10);
        mainGridPane.setVgap(10);
        mainGridPane.setAlignment(Pos.CENTER);

        Label label = new Label("Select:");
        Button projectButton = new Button("Projects");
        Button customerButton = new Button("Customers");
        Button engineerButton = new Button("Engineers");
        Button houseModelButton = new Button("House models");
        Button exitButton = new Button("Exit");

        mainGridPane.add(label, 0, 0);
        mainGridPane.add(projectButton, 0, 1);
        mainGridPane.add(customerButton, 0, 2);
        mainGridPane.add(engineerButton, 0, 3);
        mainGridPane.add(houseModelButton, 0, 4);
        mainGridPane.add(exitButton, 0, 5);

        HouseModelService houseModelService = new HouseModelService();
        CustomerService customerService = new CustomerService();
        EngineerService engineerService = new EngineerService();
        ProjectService projectService = new ProjectService();

        projectButton.setOnAction(event -> projectService.openProjectWindow(projectButton, primaryStage));
        customerButton.setOnAction(event -> customerService.openCustomerWindow(customerButton, primaryStage));
        engineerButton.setOnAction(event -> engineerService.openEngineerWindow(engineerButton, primaryStage));
        houseModelButton.setOnAction(event -> houseModelService.openHouseModelWindow(houseModelButton, primaryStage));
        exitButton.setOnAction(event -> alert.invokeExitAlert());

        Scene mainScene = new Scene(mainGridPane);
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }


}
