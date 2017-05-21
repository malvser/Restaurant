package malov.serg.kitchen;




import malov.serg.ConsoleHelper;
import malov.serg.statistic.StatisticManager;
import malov.serg.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;


public class Cook extends Observable implements Runnable {

    private String name;
    private boolean busy;
    private LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>();

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public Cook(String name) {

        this.name = name;

    }

    public boolean isBusy() {
        return busy;
    }

    public void startCookingOrder(Order order){
        busy = true;

        StatisticManager.getInstance().register(new CookedOrderEventDataRow(order.getTablet().toString(),name,order.getTotalCookingTime() * 60,
                order.getDishes()));
        ConsoleHelper.writeMessage("Start cooking - " +  order + ", cooking time " + order.getTotalCookingTime() + "min" );

        setChanged();
        notifyObservers(order);
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
                if(!queue.isEmpty()){

                        if(!this.isBusy()){
                            Order order = queue.poll();
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
