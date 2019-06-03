package ru.apolyakov.model;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Persistable;
import org.springframework.hateoas.Identifiable;
import org.springframework.util.Assert;
import ru.apolyakov.HasId;

import javax.persistence.*;

@MappedSuperclass
@Access(AccessType.FIELD)
public class AbstractBaseEntity  implements Persistable<Integer>, Identifiable<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    protected AbstractBaseEntity() {
    }

    protected AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int id() {
        Assert.notNull(id, "Entity must has id");
        return id;
    }

    @Override
    public String toString() {
        return String.format("Entity %s (%s)", getClass().getName(), id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        AbstractBaseEntity that = (AbstractBaseEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }

    @Override
    public Integer getId() {
        return id();
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}
