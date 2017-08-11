package malov.serg.kitchen;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name="dish")
@NoArgsConstructor
public class Dish {

    //Fish(25), Steak(30), Soup(15), Juice(5), Water(3);
    @Id
    @GeneratedValue
    private long id;
    @Column(name = ("photo"), length = 16777215)
    private byte[] photo;
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
    private String name;
    private int cost;
    private int weight;

    private int discount;
    private int duration;


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
