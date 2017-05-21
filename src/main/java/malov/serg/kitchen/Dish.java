package malov.serg.kitchen;

import java.util.Arrays;


public enum Dish {


    Fish(25), Steak(30), Soup(15), Juice(5), Water(3);
    private int duration;

    Dish(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public static String allDishesToString(){
        StringBuilder sb = new StringBuilder(Arrays.toString(Dish.values()));
        sb.delete(0, 1);
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

   /* public int getDuration() {
        return duration;
    }*/
}
