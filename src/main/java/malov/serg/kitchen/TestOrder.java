package malov.serg.kitchen;


import malov.serg.Tablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class TestOrder extends Order {

    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected void initDishes() throws IOException {
        dishes = new ArrayList<Dish>();
        int dishLength = Dish.values().length;
        int randomLength = (ThreadLocalRandom.current().nextInt(dishLength)) + 1;
        for (int i = 0; i < randomLength; i++) {
            double random = ThreadLocalRandom.current().nextInt(dishLength);
            dishes.add(Dish.values()[(int) random]);
        }


    }


}
