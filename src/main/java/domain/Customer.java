package domain;

import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer extends Person{

    @OneToOne(mappedBy = "customer")
    private Project project;

    public Customer() {
    }

    public Customer(String name, String lastName, String phoneNumber) {
        super(name, lastName, phoneNumber);
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
