package main;

import dao.CustomerDao;
import dao.EngineerDao;
import dao.HouseModelDao;
import dao.ProjectDao;
import domain.Customer;
import domain.Engineer;
import domain.HouseModel;
import domain.Project;
import javafx.application.Application;
import javafx.stage.Stage;
import repo.Repository;
import services.MainMenuService;

import java.time.LocalDate;

public class Main extends Application {
    public static void main(String[] args) {
        Repository.getRepoData();
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainMenuService.openMainMenu(primaryStage);
    }
}
