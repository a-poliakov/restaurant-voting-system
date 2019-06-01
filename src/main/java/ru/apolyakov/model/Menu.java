package ru.apolyakov.model;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

public class Menu extends AbstractBaseEntity {
    @Column(name = "created", columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDate created = LocalDate.now();

    private Set<MenuItem> menuItems;
}
