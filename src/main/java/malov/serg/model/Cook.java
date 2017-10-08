package malov.serg.Model;


import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "cook")
@NoArgsConstructor

public class Cook {

    @Id
    @GeneratedValue
    private long id;

    private String name;


    @OneToMany(mappedBy = "cookCookedOrder", cascade = CascadeType.ALL)
    private List<CookedOrder> CookedOrders = new ArrayList<>();

    public Cook(String name) {

        this.name = name;


    }


    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    @Override
    public String toString() {
        return name;
    }



}
