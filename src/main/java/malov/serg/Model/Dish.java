package malov.serg.Model;



import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="dish")
public class Dish implements Serializable {

    //Fish(25), Steak(30), Soup(15), Juice(5), Water(3);

    private static final long serialVersionUID = 365985541L;
    @Id
    @GeneratedValue
    private long id;
    @Column(name = ("photo"), length = 16777215)
    private byte[] photo;

    @ManyToMany(mappedBy = "dishes", cascade = CascadeType.ALL)
    private List<Order> order = new ArrayList<>();

    @ManyToMany(mappedBy = "cookingDishes", cascade = CascadeType.ALL)
    private List<CookedOrder> cookedOrders = new ArrayList<>();
    private String type;
    private Integer bonus;
    private String name;
    private int cost;
    private int weight;
    private int discount;
    private int duration;

    public List<CookedOrder> getCookedOrders() {
        return cookedOrders;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public List<Order> getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Dish() {
    }

    public Dish(String name, int cost, int weight, int discount) {
        this.name = name;
        this.cost = cost;
        this.weight = weight;
        this.discount = discount;

    }


    public Dish(byte[] photo, String name, int cost, int weight, int discount, int duration, String type) {
        this.photo = photo;
        this.name = name;
        this.cost = cost;
        this.weight = weight;
        this.discount = discount;
        this.duration = duration;
        this.type = type;
    }

    public Dish(String name, int cost, int weight, int duration, int discount) {
        this.name = name;
        this.cost = cost;
        this.weight = weight;
        this.discount = discount;
        this.duration = duration;
    }
    public Dish(byte[] photo, String name, int cost, int weight, int discount, int duration, String type, Integer bonus) {
        this.photo = photo;
        this.name = name;
        this.cost = cost;
        this.weight = weight;
        this.discount = discount;
        this.duration = duration;
        this.type = type;
        this.bonus = bonus;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
