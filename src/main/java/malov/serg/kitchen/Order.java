package malov.serg.kitchen;



import malov.serg.ConsoleHelper;
import malov.serg.Tablet;

import java.io.IOException;
import java.util.List;


public class Order {

    protected List<Dish> dishes;
    private Tablet tablet;
    public List<Dish> getDishes()
    {
        return dishes;
    }
    public Order(Tablet tablet) throws IOException
    {
        this.tablet = tablet;
        ConsoleHelper.writeMessage(Dish.allDishesToString());
        initDishes();
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
        return dishes.isEmpty() ? "" : String.format("Your order: %s of        Tablet{number=%d}",dishes,tablet.getNumber());
    }
}

