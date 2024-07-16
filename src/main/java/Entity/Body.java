package Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="body")
public class Body {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "type")
    String type;
    @OneToMany(mappedBy = "body", cascade = CascadeType.ALL)
    List<Car> cars = new ArrayList<>();

    public Body() {
    }

    public Body(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return "Body{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
