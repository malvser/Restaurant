package malov.serg.kitchen;


import malov.serg.Tablet;

import java.io.IOException;
import java.util.ArrayList;

public class TestOrder extends Order {

    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected void initDishes() throws IOException {
        dishes = new ArrayList<Dish>();
        int dishLength = Dish.values().length;
        int randomLength = (int) (Math.random() * dishLength) + 1;
        for (int i = 0; i < randomLength; i++) {
            double random = Math.random() * dishLength;
            dishes.add(Dish.values()[(int) random]);
        }


    }
}
