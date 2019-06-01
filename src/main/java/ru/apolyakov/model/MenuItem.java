package ru.apolyakov.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MenuItem")
public class MenuItem extends AbstractNamedEntity {
    @NotNull
    @Column(name = "price", nullable = false)
    private int price;

    public MenuItem(int price) {
        this.price = price;
    }

    public MenuItem(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
