package domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "engineers")
public class Engineer extends Person{

    private String position;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "engineer")
    private Set<Project> engineerProjects = new HashSet<>();

    public Engineer() {

    }

    public Engineer(String name, String lastName, String phoneNumber, String position) {
        super(name, lastName, phoneNumber);
        this.position = position;
    }

    public Set<Project> getEngineerProjects() {
        return engineerProjects;
    }

    public void setEngineerProjects(Set<Project> engineerProjects) {
        this.engineerProjects = engineerProjects;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Engineer{" +
                super.toString() +
                ", position='" + position + '\'' +
                '}';
    }
}
