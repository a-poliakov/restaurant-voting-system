package ru.apolyakov.service;

import ru.apolyakov.model.Vote;

public interface VoteService {
    Vote voteFor(Integer restaurantId, Double rate);
}
