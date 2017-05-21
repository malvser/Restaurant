package malov.serg;




import malov.serg.ad.StatisticAdvertisementManager;
import malov.serg.statistic.StatisticManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class DirectorTablet {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

    public void printAdvertisementProfit(){
        double total = 0;
        for (Map.Entry<Date, Double> entry : StatisticManager.getInstance().getAdvProfit().entrySet())
        {
            total += entry.getValue();
            ConsoleHelper.writeMessage(String.format("%s - %.2f", simpleDateFormat.format(entry.getKey()), entry.getValue()));
        }
        ConsoleHelper.writeMessage(String.format("Total - %.2f",total));
    }
    public void printCookWorkloading(){
        for (Map.Entry<Date, TreeMap<String, Integer>> pair : StatisticManager.getInstance().getCookWorkTime().entrySet()) {

            ConsoleHelper.writeMessage(simpleDateFormat.format(pair.getKey()));
            for (Map.Entry<String, Integer> pair2 : pair.getValue().entrySet()) {
                if (pair2.getValue() > 0) { // Если повар не работал в какой-то из дней, то с пустыми данными его НЕ выводим
                    ConsoleHelper.writeMessage(String.format("%s - %d min", pair2.getKey(), pair2.getValue()));
                }
            }
        }
    }

    public void printActiveVideoSet(){
        if (! StatisticAdvertisementManager.getInstance().getAllVideo().isEmpty()) {
            for (Map.Entry<String, Integer> active : StatisticAdvertisementManager.getInstance().getAllVideo().entrySet()) {
                if(active.getValue() > 0) {
                    ConsoleHelper.writeMessage(String.format(Locale.ENGLISH, "%s - %d", active.getKey(), active.getValue()));
                }

            }
        }

    }

    public void printArchivedVideoSet(){
        if (! StatisticAdvertisementManager.getInstance().getAllVideo().isEmpty()) {
            for (Map.Entry<String, Integer> archive : StatisticAdvertisementManager.getInstance().getAllVideo().entrySet()) {
                if(archive.getValue() == 0) {
                    ConsoleHelper.writeMessage(String.format(Locale.ENGLISH, archive.getKey()));
                }
            }
        }
    }
}
