package malov.serg.Model;

import malov.serg.Application;
import malov.serg.ad.AdvertisementManager;
import malov.serg.ad.NoVideoAvailableException;


import javax.persistence.*;
import java.util.concurrent.LinkedBlockingQueue;

@Entity
@Table(name="tablets")
public class Tablet {

    @Id
    @GeneratedValue
    private long id;
    private int number;


    //private Logger logger =  Logger.getLogger(Tablet.class.getName());
    private LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>();

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public Tablet(int number)
    {
        this.number = number;
        setQueue(new Application().getOrderQueue());
    }

    public Tablet(){

    }



    public LinkedBlockingQueue<Order> getQueue() {
        return queue;
    }

    public void createOrder()
    {
       /* Order order = null;
        try
        {
            order = new Order(this);
            initOrder(order);
        }
        catch (IOException e) {
          //  logger.log(Level.SEVERE, "Console is unavailable.");
        }*/
    }
    public void createTestOrder()
    {
      /* Order order = null;
        try
        {
            order = new Order(this);
            initOrder(order);
        }
        catch (IOException e) {
          //  logger.log(Level.SEVERE, "Console is unavailable.");
        }*/
    }
    private void initOrder(Order order)
    {
        if (!order.isEmpty()) {
           // ConsoleHelper.writeMessage(order.toString());
            queue.add(order);
        }
        try
        {
            new AdvertisementManager(order.getTotalCookingTime() * 60).processVideos();
        }catch (NoVideoAvailableException e) {
           // logger.log(Level.INFO, "No video is available for the order " + order);
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


