package malov.serg.Model;




import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="advertisement")


public class AdvertisementPhoto {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = ("photo"), length = 16777215)
    private byte[] photo;
    private String name;
    private long cost;  // —  стоимость рекламы за показ.
    private long amount; // — количество оплаченных показов
    private long total_amount; // общее количество
    @OneToMany(mappedBy = "viewedAdvertisement", cascade = CascadeType.ALL)
    private List<ViewedAdvertisement> advertisementPhotos = new ArrayList<>();

    public AdvertisementPhoto() {
    }

    public AdvertisementPhoto(byte[] photo, String name, Long cost, Long amount, Long total_amount) {
        this.photo = photo;
        this.name = name;
        this.cost = cost;
        this.amount = amount;
        this.total_amount = total_amount;
        //this.amountPerOneDisplaying = amountPerOneDisplaying;
    }

    public void setTotal_amount(long total_amount) {
        this.total_amount = total_amount;
    }

    public long getTotal_amount() {
        return total_amount;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public long getCost() {
        return cost;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public void setAdvertisementPhotos(List<ViewedAdvertisement> advertisementPhotos) {
        this.advertisementPhotos = advertisementPhotos;
    }
}

