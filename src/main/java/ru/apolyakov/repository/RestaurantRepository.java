package ru.apolyakov.repository;

import org.springframework.data.repository.CrudRepository;
import ru.apolyakov.model.Restaurant;
import ru.apolyakov.model.User;

public interface RestaurantRepository  extends CrudRepository<Restaurant, Integer> {
}
