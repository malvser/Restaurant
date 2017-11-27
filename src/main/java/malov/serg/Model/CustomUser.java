package malov.serg.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="customuser")
public class CustomUser {
    @Id
    @GeneratedValue
    private long id;

    private String login;
    private String password;

    @OneToMany(mappedBy = "cookCookedOrder", cascade = CascadeType.ALL)
    private List<CookedOrder> cookedOrders = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private UserRole role;
    private Integer bonus;
    private String email;
    private String phone;
    private String full_name;
    public CustomUser(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public CustomUser(String login, String password, UserRole role, String email, String phone) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phone = phone;
    }

    public CustomUser(String login, String password, UserRole role, String email, String phone, Integer bonus) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.bonus = bonus;
    }

    public CustomUser(String login, String password, UserRole role, String email, String phone, Integer bonus, String full_name) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.bonus = bonus;
        this.full_name = full_name;
    }

    public CustomUser() {}

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public List<CookedOrder> getCookedOrders() {
        return cookedOrders;
    }

    public void setCookedOrders(List<CookedOrder> cookedOrders) {
        this.cookedOrders = cookedOrders;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
