package ru.apolyakov.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurant")
@JsonIgnoreProperties(value= {"menus","votes"})
public class Restaurant extends AbstractNamedEntity {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 200)
    @OrderBy("created DESC")
    protected List<Vote> votes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 200)
    @OrderBy("created DESC")
    protected List<Menu> menus;

    public Restaurant() {

    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
