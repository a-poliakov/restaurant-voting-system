package ru.apolyakov.repository;

import org.springframework.data.repository.CrudRepository;
import ru.apolyakov.model.MenuItem;
import ru.apolyakov.model.User;

public interface MenuItemRepository  extends CrudRepository<MenuItem, Integer> {
}
