package ru.apolyakov.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "menu")
@JsonIgnoreProperties(value= {"menuItems"})
public class Menu extends AbstractBaseEntity {
    @Column(name = "created", columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDate created = LocalDate.now();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
    @OrderBy("id DESC")
    private List<MenuItem> menuItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    public Menu() {
    }


    public Menu(LocalDate created, List<MenuItem> menuItems) {
        this(null, created, menuItems);
    }

    public Menu(Integer id, LocalDate created, List<MenuItem> menuItems) {
        super(id);
        this.created = created;
        this.menuItems = menuItems;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
