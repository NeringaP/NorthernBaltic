package main;

import javafx.application.Application;
import javafx.stage.Stage;
import services.MainMenuService;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainMenuService mainMenuService = new MainMenuService();
        mainMenuService.openMainMenu(primaryStage);
    }
}
