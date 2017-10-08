package malov.serg.Model;



import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="orders")
@NoArgsConstructor
public class Order implements Serializable {


    private static final long serialVersionUID = 6158646455L;
    @Id
    @GeneratedValue
    private long id;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="OrderDish",
            joinColumns={@JoinColumn(name="order_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="dish_id", referencedColumnName="id")})
    private List<Dish> dishes = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name="tablet_id")
    private Tablet table;

    private Boolean cooking;

    public Boolean getCooking() {
        return cooking;
    }

    public void setCooking(Boolean cooking) {
        this.cooking = cooking;
    }

    public List<Dish> getDishes()
    {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public Order(Tablet tablet, List<Dish> dishes, Boolean cooking) throws IOException
    {
        this.table = tablet;
        this.dishes = dishes;
        this.cooking = cooking;
        //ConsoleHelper.writeMessage(Dish.allDishesToString());
        //initDishes();
    }


    public int getTotalCookingTime() {
        int sum = 0;
        if (!isEmpty())
            for (Dish dish : dishes) {
                sum += dish.getDuration();
            }
        return sum;
    }

    public boolean isEmpty()
    {
        return dishes.isEmpty();
    }

    public Tablet getTablet() {
        return table;
    }

    @Override
    public String toString()
    {
        return dishes.isEmpty() ? "" : String.format("Your order: %s of Tablet{number=%d}",dishes, table.getNumber());
    }

    public long getId() {
        return id;
    }
}

