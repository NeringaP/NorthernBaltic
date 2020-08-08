package domain;

import javax.persistence.*;

public class Customer extends Person{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "id")
    private Project project;

    public Customer() {
    }

    public Customer(String name, String lastName, String phoneNumber) {
        super(name, lastName, phoneNumber);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Customer{" + super.toString() +
                project.toString() +
                '}';
    }
}
