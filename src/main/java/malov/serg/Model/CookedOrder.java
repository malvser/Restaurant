package malov.serg.Model;




import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cookedorders")
public class CookedOrder {

    @Id
    @GeneratedValue
    private long id;


    private Integer tabletNumber;  // — имя планшета
    private  int cookingTimeSeconds;  // — время приготовления заказа в секундах
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="CookedOrderDish",
            joinColumns={@JoinColumn(name="cookedOrder_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="dish_id", referencedColumnName="id")})
    private List<Dish> cookingDishes;   // — список блюд для приготовления
    private String date;
    @ManyToOne
    @JoinColumn(name="cookCookedOrderId")
    private CustomUser cookCookedOrder;


    public CookedOrder(Integer tabletName, CustomUser cook, List<Dish> cookedDish, int cookingTimeSeconds) {
        this.tabletNumber = tabletName;
        this.cookCookedOrder = cook;
        this.cookingTimeSeconds = cookingTimeSeconds;
        this.cookingDishes = cookedDish;
        this.date = DateFormat();

    }

    public CookedOrder() {
    }

    public String DateFormat(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return simpleDateFormat.format(new Date());
    }

    public CustomUser getCook() {
        return cookCookedOrder;
    }

    public void setCook(CustomUser cook) {
        this.cookCookedOrder = cook;
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

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }


}
