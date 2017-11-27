package malov.serg.Model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tablet")
public class Tablet implements Serializable {


    private static final long serialVersionUID = 6529685098L;
    @Id
    @GeneratedValue
    private long id;
    private int number;
    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    //private Logger logger =  Logger.getLogger(Tablet.class.getName());

    public Tablet(int number)
    {
        this.number = number;

    }

    public Tablet(){

    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }




    public int getNumber()
    {
        return number;
    }
    @Override
    public String toString() {
        return "Tablet{number=" + number + "}";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}


