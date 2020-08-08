package domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    private Integer id;
    private String address;
    private LocalDate dueDate;
    @ManyToOne
    @JoinColumn(name = "house_model_id")
    private HouseModel houseModel;
    @ManyToOne
    @JoinColumn(name = "engineer_id")
    private Engineer engineer;
    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    private Customer customer;

    public Project() {

    }

    public Project(Integer id, String address, LocalDate dueDate) {
        this.id = id;
        this.address = address;
        this.dueDate = dueDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public HouseModel getHouseModel() {
        return houseModel;
    }

    public void setHouseModel(HouseModel houseModel) {
        this.houseModel = houseModel;
    }

    public Engineer getEngineer() {
        return engineer;
    }

    public void setEngineer(Engineer engineer) {
        this.engineer = engineer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", houseModel=" + houseModel +
                ", address='" + address + '\'' +
                ", dueDate=" + dueDate +
                ", " + engineer.toString() +
                customer.toString() +
                '}';
    }
}
