package ru.apolyakov.repository;

import org.springframework.data.repository.CrudRepository;
import ru.apolyakov.model.Menu;
import ru.apolyakov.model.User;

public interface MenuRepository  extends CrudRepository<Menu, Integer> {
}
