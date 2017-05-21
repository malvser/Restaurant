package malov.serg.ad;


public class Advertisement {

    private Object content; // — видео
    private String name;  // — имя/название
    private long initialAmount;  // — начальная сумма, стоимость рекламы в копейках. Используем long, чтобы избежать проблем с округлением
    private int hits; // — количество оплаченных показов
    private int duration; // — продолжительность в секундах
    private long amountPerOneDisplaying;


    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;
        this.amountPerOneDisplaying = initialAmount/hits;

    }

    public String getName() {
        return name;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public int getDuration() {
        return duration;
    }

    public int getHits() {
        return hits;
    }



    public void revalidate() throws UnsupportedOperationException {

        if (hits <= 0) throw new UnsupportedOperationException();

        if (hits == 1) amountPerOneDisplaying += initialAmount % amountPerOneDisplaying;
        hits--;
    }
}
