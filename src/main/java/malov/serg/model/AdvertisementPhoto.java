package malov.serg.Model;


import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="advertisement")
@NoArgsConstructor
public class AdvertisementPhoto {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = ("photo"), length = 16777215)
    private byte[] photo;
    private String name;
    private int initialAmount;  // — начальная сумма, стоимость рекламы в копейках.
    private int hits; // — количество оплаченных показов
   // private int amountPerOneDisplaying;


    public AdvertisementPhoto(byte[] photo, String name, int initialAmount, int hits) {
        this.photo = photo;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        //this.amountPerOneDisplaying = amountPerOneDisplaying;
    }


    public byte[] getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public long getInitialAmount() {
        return initialAmount;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public long getId() {
        return id;
    }
}

