package malov.serg.ad;



import java.util.Map;
import java.util.TreeMap;

public class StatisticAdvertisementManager {

    private static volatile StatisticAdvertisementManager instance;
    private AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();


    private StatisticAdvertisementManager(){

    }

    public static StatisticAdvertisementManager getInstance(){
        if(instance == null){
            synchronized (StatisticAdvertisementManager.class){
                if(instance == null){
                    instance = new StatisticAdvertisementManager();
                }
            }
        }
        return instance;
    }


    public  Map<String, Integer> getAllVideo(){

        Map<String, Integer> activeAdvertisements = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (Advertisement adv : advertisementStorage.list()) {

                activeAdvertisements.put(adv.getName(),adv.getHits());

        }
        return activeAdvertisements;
    }


}
