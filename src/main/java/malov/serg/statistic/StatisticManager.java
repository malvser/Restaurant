package malov.serg.statistic;

import malov.serg.statistic.event.CookedOrderEventDataRow;
import malov.serg.statistic.event.EventDataRow;
import malov.serg.statistic.event.EventType;
import malov.serg.statistic.event.VideoSelectedEventDataRow;

import java.util.*;


public class StatisticManager {

    private static volatile StatisticManager instance;
    private StatisticStorage statisticStorage = new StatisticStorage();

    private StatisticManager() {
    }

    public static StatisticManager getInstance() {
        if (instance == null) {
            synchronized (StatisticManager.class) {
                if (instance == null) {
                    instance = new StatisticManager();
                }
            }
        }
        return instance;
    }



    public void register(EventDataRow data) {
        statisticStorage.put(data);

    }



    public Map<Date, Double> getAdvProfit(){
        Map<Date, Double> resultMap = new TreeMap<>(Collections.reverseOrder());
        for (EventDataRow event : statisticStorage.getStorage(EventType.SELECTED_VIDEOS)) {
            Date date = dateWithoutTime(event.getDate());
            VideoSelectedEventDataRow eventData = (VideoSelectedEventDataRow) event;
            if (resultMap.containsKey(date)) {
                resultMap.put(date, resultMap.get(date) + (0.01d * (double) eventData.getAmount()));
            } else {
                resultMap.put(date, (0.01d * (double) eventData.getAmount()));
            }
        }
        return resultMap;
    }
    /**
     *
     * Получаем продолжитеьность роботы каждого повара отдельно
     */
    public Map<Date, TreeMap<String, Integer>> getCookWorkTime(){
        TreeMap<Date, TreeMap<String, Integer>> map = new TreeMap<>(Collections.reverseOrder());
        for (EventDataRow e : statisticStorage.getStorage(EventType.COOKED_ORDER)) {
            Date date = dateWithoutTime(e.getDate());
            CookedOrderEventDataRow cook = (CookedOrderEventDataRow) e;
            int time = cook.getTime();
            if (time == 0) continue;
            if (time % 60 == 0) time = time / 60;
            else time = time / 60 + 1;
            if (map.containsKey(date)) {
                TreeMap<String, Integer> value = map.get(date);
                if (value.containsKey(cook.getCookName())) {
                    value.put(cook.getCookName(), value.get(cook.getCookName())+time);
                }
                else value.put(cook.getCookName(), time);
            }
            else {
                TreeMap<String, Integer> value = new TreeMap<>();
                value.put(cook.getCookName(), time);
                map.put(date, value);
            }
        }
        return map;
    }

    private Date dateWithoutTime(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    private class StatisticStorage {
        private Map<EventType, List<EventDataRow>> storage = new HashMap<>();
        private StatisticStorage() {
            for (EventType type : EventType.values()) {
                storage.put(type, new ArrayList<EventDataRow>());
            }
        }
        private void put(EventDataRow data) {

            storage.get(data.getType()).add(data);
        }

        private List<EventDataRow> getStorage(EventType e) {
            return storage.get(e);
        }





    }
}
