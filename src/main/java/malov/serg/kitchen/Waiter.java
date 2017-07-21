package malov.serg.kitchen;




import malov.serg.ConsoleHelper;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;


public class Waiter implements Runnable  {

    private String nameCook;
    private boolean busy;
    private LinkedBlockingQueue<Order> cookedOrders = new LinkedBlockingQueue<>();

    public void setCookedOrders(LinkedBlockingQueue<Order> cookedOrders) {
        this.cookedOrders = cookedOrders;
    }

    public void setNameCook(String nameCook) {
        this.nameCook = nameCook;
    }

    public boolean isBusy() {
        return busy;
    }


    @Override
    public void run() {
        while(true){
            try{
                if(!cookedOrders.isEmpty()){

                    if(!this.isBusy()){
                        Order order = cookedOrders.poll();
                        if(order != null)
                        {
                            //ConsoleHelper.writeMessage();
                            carryOrder(order);
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

    public void carryOrder(Order order){
        busy = true;

        try {
            Thread.sleep(10 * Dish.values().length);
        } catch (InterruptedException e) {

        }
        ConsoleHelper.writeMessage(order.toString() +  " was cooked by "); //cook.getName());
        busy = false;
    }
}
