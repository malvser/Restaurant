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
    private int amount; // — количество оплаченных показов
    private int total_amount; // общее количество
   // private int amountPerOneDisplaying;


    public AdvertisementPhoto(byte[] photo, String name, int initialAmount, int amount, int total_amount) {
        this.photo = photo;
        this.name = name;
        this.initialAmount = initialAmount;
        this.amount = amount;
        this.total_amount = total_amount;
        //this.amountPerOneDisplaying = amountPerOneDisplaying;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public int getTotal_amount() {
        return total_amount;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }
}

