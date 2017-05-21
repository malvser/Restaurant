package malov.serg.ad;



import malov.serg.statistic.StatisticManager;
import malov.serg.statistic.event.NoAvailableVideoEventDataRow;
import malov.serg.statistic.event.VideoSelectedEventDataRow;

import java.util.*;

public class AdvertisementManager {

    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();

    private int timeSeconds;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos(){
        List<Advertisement> list = storage.list();

        //убираем видео с hits = 0 и > W
        Iterator<Advertisement> iter = list.iterator();
        while (iter.hasNext()) {
            Advertisement ad = iter.next();
            if (ad.getHits() < 1 && ad.getDuration() > timeSeconds) {
                iter.remove();
            }
        }

        List<Advertisement> ads = getMaxProfitVideos(list);

        if (ads.isEmpty()) {
            StatisticManager.getInstance().register(new NoAvailableVideoEventDataRow(timeSeconds));
            throw new NoVideoAvailableException();
        }

        //сортируем сначала по убыванию стоимости показа одного видео(если разная стоимость показа),
        //затем (если стоимости равны) сортируем в порядке увеличения стоимости одной секунды видео
        Collections.sort(ads, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2)
            {
                if (o2.getAmountPerOneDisplaying() != o1.getAmountPerOneDisplaying())
                    return Long.compare(o2.getAmountPerOneDisplaying(), o1.getAmountPerOneDisplaying());
                else
                {
                    long oneSecondCost1 = (o1.getAmountPerOneDisplaying() * 1000) / o1.getDuration();
                    long oneSecondCost2 = (o2.getAmountPerOneDisplaying() * 1000) / o2.getDuration();
                    return Long.compare(oneSecondCost1, oneSecondCost2);
                }
            }
        });




        StatisticManager.getInstance().register(new VideoSelectedEventDataRow(ads, calcTotalAmount(ads), calcTotalTime(ads)));


        for (Advertisement ad: ads)
        {
            ad.revalidate();
            System.out.println((String.format("%s is displaying... %d, %d",
                    ad.getName(),
                    ad.getAmountPerOneDisplaying(),
                    ad.getAmountPerOneDisplaying() * 1000 / ad.getDuration())));

        }

    }

    private List<List<Advertisement>> powerSet(List<Advertisement> originalList)
    {
        List<List<Advertisement>> lists = new ArrayList<>();
        if (originalList.isEmpty()) {
            lists.add(new ArrayList<Advertisement>());
            return lists;
        }
        List<Advertisement> recurseList = new ArrayList<>(originalList);
        Advertisement head = recurseList.get(0);
        List<Advertisement> rest = new ArrayList<>(recurseList.subList(1, recurseList.size()));
        for (List<Advertisement> list : powerSet(rest)) {
            List<Advertisement> newList = new ArrayList<>();
            newList.add(head);
            newList.addAll(list);
            lists.add(newList);
            lists.add(list);
        }
        return lists;
    }

    private List<Advertisement> getMaxProfitVideos(List<Advertisement> ads)
    {
        List<List<Advertisement>> adsPowerSet = powerSet(ads);

        Iterator<List<Advertisement>> iterator = adsPowerSet.iterator();
        while (iterator.hasNext())
            if (calcTotalTime(iterator.next()) > timeSeconds)
                iterator.remove();

        Collections.sort(adsPowerSet, new Comparator<List<Advertisement>>()
        {
            @Override
            public int compare(List<Advertisement> o1, List<Advertisement> o2)
            {
                if (calcTotalAmount(o1) != calcTotalAmount(o2))
                {
                    return calcTotalAmount(o1) > calcTotalAmount(o2) ? 1: -1;
                }
                else if (calcTotalTime(o1) != calcTotalTime(o2))
                {
                    return calcTotalTime(o1) > calcTotalTime(o2) ? 1 : -1;
                }
                else
                    return o2.size() > o1.size() ? 1 :-1;
            }
        });
        return adsPowerSet.get(adsPowerSet.size()-1);
    }

    //вычисляет общий вес набора предметов ( общее время показа видео )
    private int calcTotalTime(List<Advertisement> ads)
    {
        int sumTime = 0;
        if (ads == null) return sumTime;

        for(Advertisement ad : ads)
            sumTime += ad.getDuration();

        return sumTime;
    }

    //вычисляет общую стоимость набора предметов ( общая цена показа видео )
    private long calcTotalAmount(List<Advertisement> ads)
    {
        long sumAmount = 0;
        if (ads == null) return sumAmount;

        for(Advertisement ad : ads)
            sumAmount += ad.getAmountPerOneDisplaying();

        return sumAmount;
    }
}
