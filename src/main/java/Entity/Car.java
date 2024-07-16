package Entity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    int id;
    @Column(name="marka")
    String marka;
    @Column(name="model")
    String model;
    @ManyToOne
    @JoinColumn(name="id_eng",nullable = false)
    public Engine eng;
    @ManyToOne
    @JoinColumn(name="id_body",nullable = false)
    public Body body;
    @Column(name="price")
    BigDecimal price;
    @Column(name="date")
    String date;

    public Car() {
    }

    public Car(String marka, String model, Engine eng, Body body, BigDecimal price, String date) {
        this.marka = marka;
        this.model = model;
        this.eng = eng;
        this.body = body;
        this.price = price;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Engine getEng() {
        return eng;
    }

    public void setEng(Engine eng) {
        this.eng = eng;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", marka='" + marka + '\'' +
                ", model='" + model + '\'' +
                ", eng=" + eng +
                ", body=" + body +
                ", price=" + price +
                ", date=" + date +
                '}';
    }
}
