package malov.serg.Model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="dish")
@NoArgsConstructor
public class Dish implements Serializable {

    //Fish(25), Steak(30), Soup(15), Juice(5), Water(3);

    private static final long serialVersionUID = 365985541L;
    @Id
    @GeneratedValue
    private long id;
    @Column(name = ("photo"), length = 16777215)
    private byte[] photo;

    @ManyToMany(mappedBy = "dishes", cascade = CascadeType.PERSIST)
    private List<Order> order = new ArrayList<>();

    private String name;
    private int cost;
    private int weight;
    private int discount;
    private int duration;


    public List<Order> getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Dish(String name, int cost, int weight, int discount) {
        this.name = name;
        this.cost = cost;
        this.weight = weight;
        this.discount = discount;

    }

    public Dish(String name, int cost, int weight, byte[] photo) {
        this.name = name;
        this.cost = cost;
        this.weight = weight;
        this.photo = photo;
    }

    public Dish(byte[] photo, String name, int cost, int weight, int discount, int duration) {
        this.photo = photo;
        this.name = name;
        this.cost = cost;
        this.weight = weight;
        this.discount = discount;
        this.duration = duration;
    }

    public Dish(String name, int cost, int weight, int duration, int discount) {
        this.name = name;
        this.cost = cost;
        this.weight = weight;
        this.discount = discount;
        this.duration = duration;
    }



    public Dish(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public int getWeight() {
        return weight;
    }

    public int getDiscount() {
        return discount;
    }

    public int getDuration() {
        return duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
