package domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "house_models")
public class HouseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer price;
    private Integer area;
    private boolean hasGarage;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "houseModel")
    private Set<Project> modelProjects = new HashSet<>();

    public HouseModel() {

    }

    public HouseModel(String name, Integer price, Integer area, boolean hasGarage) {
        this.name = name;
        this.price = price;
        this.area = area;
        this.hasGarage = hasGarage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public boolean getHasGarage() {
        return hasGarage;
    }

    public void setHasGarage(boolean hasGarage) {
        this.hasGarage = hasGarage;
    }

    public Set<Project> getModelProjects() {
        return modelProjects;
    }

    public void setModelProjects(Set<Project> modelProjects) {
        this.modelProjects = modelProjects;
    }

    @Override
    public String toString() {
        return "HouseModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", area=" + area +
                ", hasGarage=" + hasGarage +
                '}';
    }
}
