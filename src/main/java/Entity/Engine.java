package Entity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="engine")
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    int id;
    @Column(name="volume")
    float volume;
    @Column(name="horsepower")
    int horsePower;
    @Column(name="typefuel")
    String typeFuel;
    @Column(name="title")
    String title;
    @OneToMany(mappedBy = "eng",cascade = CascadeType.ALL)
    public List<Car> cars=new ArrayList<>();

    public Engine() {
    }

    public Engine(float volume, int horsePower, String typeFuel, String title) {
        this.volume = volume;
        this.horsePower = horsePower;
        this.typeFuel = typeFuel;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public String getTypeFuel() {
        return typeFuel;
    }

    public void setTypeFuel(String typeFuel) {
        this.typeFuel = typeFuel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "id=" + id +
                ", volume=" + volume +
                ", horsePower=" + horsePower +
                ", typeFuel='" + typeFuel + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
