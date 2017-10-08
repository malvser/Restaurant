package malov.serg.Model;


import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Table(name="viewed_advertisement")
public class ViewedAdvertisement implements Serializable {

    private static final long serialVersionUID = 365985541556L;

    @Id
    @GeneratedValue
    private long id;
    private Long profit;
    private String date;

    @ManyToOne
    @JoinColumn(name="viewed_advertisement_id")
    private AdvertisementPhoto viewedAdvertisement;

    public ViewedAdvertisement() {
    }

    public ViewedAdvertisement(AdvertisementPhoto viewedAdvertisement, Long profit) {
        this.viewedAdvertisement = viewedAdvertisement;
        this.profit = profit;
        this.date = DateFormat();
    }

    public String DateFormat(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return simpleDateFormat.format(new Date());
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getProfit() {
        return profit;
    }

    public void setProfit(Long profit) {
        this.profit = profit;
    }

    public AdvertisementPhoto getViewedAdvertisement() {
        return viewedAdvertisement;
    }

    public void setViewedAdvertisement(AdvertisementPhoto viewedAdvertisement) {
        this.viewedAdvertisement = viewedAdvertisement;
    }
}
