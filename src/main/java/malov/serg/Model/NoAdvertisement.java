package malov.serg.Model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="noadvertisement")
public class NoAdvertisement implements Serializable {

    private static final long serialVersionUID = 615845646455L;
    @Id
    @GeneratedValue
    private long id;
    private Long totalDuration;   // — время приготовления заказа в секундах
    private String date;

    public NoAdvertisement() {
    }

    public NoAdvertisement(Long totalDuration) {
        this.totalDuration = totalDuration;
        this.date = DateFormat();
    }

    public String DateFormat(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return simpleDateFormat.format(new Date());
    }

    public Long getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Long totalDuration) {
        this.totalDuration = totalDuration;
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
}
