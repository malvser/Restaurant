package malov.serg;

import malov.serg.kitchen.Cook;
import malov.serg.kitchen.Order;
import malov.serg.kitchen.Waiter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.LinkedBlockingQueue;

@SpringBootApplication
public class Application {

    private static final int ORDER_CREATING_INTERVAL = 100;
    private static final LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();
    private static final LinkedBlockingQueue<Order> cookedOrders = new LinkedBlockingQueue<>();

    public LinkedBlockingQueue<Order> getCookedOrders() {
        return cookedOrders;
    }

    public LinkedBlockingQueue<Order> getOrderQueue() {
        return orderQueue;
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(/*final ContactService contactService*/) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {








                Locale.setDefault(Locale.ENGLISH);
                Cook cook1 = new Cook("Vova");
                cook1.setQueueOrders(orderQueue);
                Cook cook2 = new Cook("Petr");
                cook2.setQueueOrders(orderQueue);
                Thread thread = new Thread(cook1);
                thread.setDaemon(true);
                thread.start();
                Thread thread2 = new Thread(cook2);
                thread2.setDaemon(true);
                thread2.start();
                Waiter waiter = new Waiter();
                waiter.setCookedOrders(cookedOrders);
                Thread thread1 = new Thread(waiter);
                thread1.setDaemon(true);
                thread1.start();

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
        };
    }
}