package malov.serg;

import lombok.NoArgsConstructor;
import malov.serg.ad.AdvertisementManager;
import malov.serg.ad.NoVideoAvailableException;
import malov.serg.kitchen.Order;
import malov.serg.kitchen.TestOrder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/*@Entity
@Table(name="Tablet")*/
public class Tablet {

   /* @Id
    @GeneratedValue*/
    private long id;
    private final int number;
    private Logger logger =  Logger.getLogger(Tablet.class.getName());
    private LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>();

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public Tablet(int number)
    {
        this.number = number;
        setQueue(new Application().getOrderQueue());
    }

    public void createOrder()
    {
        Order order = null;
        try
        {
            order = new Order(this);
            initOrder(order);
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
    }
    public void createTestOrder()
    {
        Order order = null;
        try
        {
            order = new TestOrder(this);
            initOrder(order);
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
    }
    private void initOrder(Order order)
    {
        if (!order.isEmpty()) {
            ConsoleHelper.writeMessage(order.toString());
            queue.add(order);
        }
        try
        {
            new AdvertisementManager(order.getTotalCookingTime() * 60).processVideos();
        }catch (NoVideoAvailableException e) {
            logger.log(Level.INFO, "No video is available for the order " + order);
        }//NoAvailableVideoEventDataRow
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


