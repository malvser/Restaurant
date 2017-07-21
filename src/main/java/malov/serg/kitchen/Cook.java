package malov.serg.kitchen;





import lombok.NoArgsConstructor;
import malov.serg.Application;
import malov.serg.ConsoleHelper;
import malov.serg.statistic.StatisticManager;
import malov.serg.statistic.event.CookedOrderEventDataRow;

import javax.persistence.*;
import java.util.concurrent.LinkedBlockingQueue;


@Entity
@Table(name="cook")
@NoArgsConstructor

public class Cook implements Runnable {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private boolean busy;
    //@OneToMany(mappedBy="cook", cascade=CascadeType.ALL)
    private LinkedBlockingQueue<Order> queueOrders = new LinkedBlockingQueue<>();

    private LinkedBlockingQueue<Order> cookedOrders = new LinkedBlockingQueue<>();

    public void setCookedOrders(LinkedBlockingQueue<Order> cookedOrders) {
        this.cookedOrders = cookedOrders;
    }

    public void setQueueOrders(LinkedBlockingQueue<Order> queueOrders) {
        this.queueOrders = queueOrders;
    }

    public Cook(String name) {

        this.name = name;
        setCookedOrders(new Application().getCookedOrders());

    }

    public boolean isBusy() {
        return busy;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LinkedBlockingQueue<Order> getQueueOrders() {
        return queueOrders;
    }

    public LinkedBlockingQueue<Order> getCookedOrders() {
        return cookedOrders;
    }

    public void startCookingOrder(Order order){
        busy = true;

        StatisticManager.getInstance().register(new CookedOrderEventDataRow(order.getTablet().toString(),name,order.getTotalCookingTime() * 60,
                order.getDishes()));
        ConsoleHelper.writeMessage("Start cooking - " +  order + ", cooking time " + order.getTotalCookingTime() + "min" );

        cookedOrders.add(order);

        try {
            Thread.sleep(10 * order.getTotalCookingTime());
        } catch (InterruptedException e) {

        }
        busy = false;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void run() {
        while(true){
            try{
                if(!queueOrders.isEmpty()){

                        if(!this.isBusy()){
                            Order order = queueOrders.poll();
                            if(order != null)
                            {
                                startCookingOrder(order);
                            } else {
                                break;
                            }
                        }

                }
                Thread.sleep(10);
            } catch (InterruptedException ignore){
                break;
            }
        }
    }
}
