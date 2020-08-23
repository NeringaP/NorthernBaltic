package repo;

import dao.CustomerDao;
import dao.EngineerDao;
import dao.HouseModelDao;
import dao.ProjectDao;
import domain.Customer;
import domain.Engineer;
import domain.HouseModel;
import domain.Project;

import java.time.LocalDate;

public class Repository {

    public static void getRepoData() {
        HouseModel noomi = new HouseModel("Noomi", 420000, 125, false);
        HouseModel zara = new HouseModel("Zara", 485000, 145, false);
        HouseModel aurora = new HouseModel("Aurora", 564000, 215, true);
        HouseModel vindy = new HouseModel("Vindy", 512000, 150, false);
        HouseModel arwen = new HouseModel("Arwen", 525000, 185, true);
        HouseModelDao houseModelDao = new HouseModelDao();
        houseModelDao.addHouseModel(noomi);
        houseModelDao.addHouseModel(zara);
        houseModelDao.addHouseModel(aurora);
        houseModelDao.addHouseModel(vindy);
        houseModelDao.addHouseModel(arwen);
        Engineer engineer1 = new Engineer("Marius", "Mikeliūnas", "+37045852236", "Lead engineer");
        Engineer engineer2 = new Engineer("Neringa", "Petrauskaitė", "+37063558071", "Engineer");
        Engineer engineer3 = new Engineer("Mindaugas", "Čepulis", "+37045226988", "Draftsman");
        Engineer engineer4 = new Engineer("Rasa", "Jokšaitė", "+37089692235", "Lead engineer");
        EngineerDao engineerDao = new EngineerDao();
        engineerDao.addEngineer(engineer1);
        engineerDao.addEngineer(engineer2);
        engineerDao.addEngineer(engineer3);
        engineerDao.addEngineer(engineer4);
        Customer customer1 = new Customer("Viktorija", "Lapė", "+37045882231");
        Customer customer2 = new Customer("Lukas", "Sakalauskas", "+37045546988");
        Customer customer3 = new Customer("Jonas", "Tolenis", "+37045856671");
        Customer customer4 = new Customer("Vaida", "Strumskienė", "+37088985562");
        CustomerDao customerDao = new CustomerDao();
        customerDao.addCustomer(customer1);
        customerDao.addCustomer(customer2);
        customerDao.addCustomer(customer3);
        customerDao.addCustomer(customer4);
        Project project1 = new Project(1020022, "Miško g. 25, Kaunas", LocalDate.of(2021, 05, 24));
        project1.setHouseModel(noomi);
        project1.setCustomer(customer2);
        project1.setEngineer(engineer1);
        ProjectDao projectDao = new ProjectDao();
        projectDao.addProject(project1);
    }
}
