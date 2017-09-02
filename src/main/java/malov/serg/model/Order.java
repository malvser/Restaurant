package malov.serg.Model;



import lombok.NoArgsConstructor;
import malov.serg.ConsoleHelper;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="orders")
@NoArgsConstructor
public class Order implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    @OneToMany(mappedBy="order", cascade=CascadeType.ALL)
    private List<Dish> dishes;
    @OneToOne
    @JoinColumn(name="tablet_id")
    private Tablet tablet;


    public List<Dish> getDishes()
    {
        return dishes;
    }
    public Order(Tablet tablet, List<Dish> dishes) throws IOException
    {
        this.tablet = tablet;
        this.dishes = dishes;
        //ConsoleHelper.writeMessage(Dish.allDishesToString());
        //initDishes();
    }


    public int getTotalCookingTime()
    {
        int sum = 0;
        for(Dish dish: dishes)
        {
            sum += dish.getDuration();
        }
        return sum;
    }
    protected void initDishes() throws IOException
    {
        dishes = ConsoleHelper.getAllDishesForOrder();
    }
    public boolean isEmpty()
    {
        return dishes.isEmpty();
    }

    public Tablet getTablet() {
        return tablet;
    }

    @Override
    public String toString()
    {
        return dishes.isEmpty() ? "" : String.format("Your order: %s of Tablet{number=%d}",dishes,tablet.getNumber());
    }

    public long getId() {
        return id;
    }
}

