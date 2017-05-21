package malov.serg;

import malov.serg.kitchen.Cook;
import malov.serg.kitchen.Order;
import malov.serg.kitchen.Waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.LinkedBlockingQueue;


public class Restaurant {

    private static final int ORDER_CREATING_INTERVAL = 100;
    private static final LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

    public LinkedBlockingQueue<Order> getOrderQueue() {
        return orderQueue;
    }

    public static void main(String[] args)
    {
        Locale.setDefault(Locale.ENGLISH);
        Cook cook1 = new Cook("Vova");
        cook1.setQueue(orderQueue);
        Cook cook2 = new Cook("Petr");
        cook2.setQueue(orderQueue);
        Thread thread = new Thread(cook1);
        thread.setDaemon(true);
        thread.start();
        Thread thread2 = new Thread(cook2);
        thread2.setDaemon(true);
        thread2.start();
        Waiter waiter = new Waiter();
        cook1.addObserver(waiter);
        cook2.addObserver(waiter);
        List<Tablet> tabletList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Tablet tablet = new Tablet(i);

            tabletList.add(tablet);
        }
        RandomOrderGeneratorTask generatorTask = new RandomOrderGeneratorTask(tabletList, ORDER_CREATING_INTERVAL);
        Thread thread3 = new Thread(generatorTask);
        thread3.start();
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
        }
        thread.interrupt();
        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();
    }
}
