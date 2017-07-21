package malov.serg.kitchen;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name="dish")
@NoArgsConstructor
public enum Dish {

    @Column(name="name")
    Fish(25), Steak(30), Soup(15), Juice(5), Water(3);

    //private String name;
    @Id
    @GeneratedValue
    private long id;
    private int duration;

    Dish(int duration) {
        this.duration = duration;
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
