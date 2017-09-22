package malov.serg.Model;




import malov.serg.statistic.event.EventDataRow;
import malov.serg.statistic.event.EventType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cookedorder")
public class CookedOrder implements EventDataRow {

    @Id
    @GeneratedValue
    private long id;


    private Integer tabletNumber;  // — имя планшета
    private String cookName;  // — имя повара
    private  int cookingTimeSeconds;  // — время приготовления заказа в секундах
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="CookedOrderDish",
            joinColumns={@JoinColumn(name="cookedOrder_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="dish_id", referencedColumnName="id")})
    private List<Dish> cookingDishes;   // — список блюд для приготовления
    private Date date;
    @ManyToOne
    @JoinColumn(name="cookCookedOrder_id")
    private Cook cookCookedOrder;


    public CookedOrder(Integer tabletName, Cook cookCookedOrder, List<Dish> cookedDish, int cookingTimeSeconds, Date date) {
        this.tabletNumber = tabletName;
        this.cookCookedOrder = cookCookedOrder;
        this.cookingTimeSeconds = cookingTimeSeconds;
        this.cookingDishes = cookedDish;
        this.cookName = cookCookedOrder.getName();
        this.date = date;

    }

    public CookedOrder() {
    }

    public Cook getCookCookedOrder() {
        return cookCookedOrder;
    }

    public void setCookCookedOrder(Cook cookCookedOrder) {
        this.cookCookedOrder = cookCookedOrder;
    }

    public long getId() {
        return id;
    }

    public Integer getTabletNumber() {
        return tabletNumber;
    }

    public int getCookingTimeSeconds() {
        return cookingTimeSeconds;
    }

    public List<Dish> getCookingDishes() {
        return cookingDishes;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public EventType getType() {
        return EventType.COOKED_ORDER;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public int getTime() {
        return cookingTimeSeconds;
    }

    public String getCookName() {
        return cookName;
    }
}
