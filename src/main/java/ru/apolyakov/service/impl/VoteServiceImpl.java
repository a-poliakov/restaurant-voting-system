package ru.apolyakov.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.apolyakov.config.exception.NotAcceptableException;
import ru.apolyakov.model.Restaurant;
import ru.apolyakov.model.User;
import ru.apolyakov.model.Vote;
import ru.apolyakov.repository.RestaurantRepository;
import ru.apolyakov.repository.UserRepository;
import ru.apolyakov.repository.VotingRepository;
import ru.apolyakov.security.AuthorizedUser;
import ru.apolyakov.security.SecurityUtil;
import ru.apolyakov.service.VoteService;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class VoteServiceImpl implements VoteService {
    public static final LocalTime DEADLINE_VOTE_HOUR = LocalTime.of(11, 0);

    private final VotingRepository votingRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Autowired
    public VoteServiceImpl(VotingRepository votingRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.votingRepository = votingRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Vote voteFor(Integer restaurantId, Double rate) {
        LocalDate today = LocalDate.now();
        AuthorizedUser authorizedUser = SecurityUtil.get();
        LocalTime now = LocalTime.now();
        Vote vote = votingRepository.findByRestaurantIdAndUserIdAndDate(restaurantId, authorizedUser.getId(), today).orElse(null);
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if (restaurant == null)
        {
            throw new NotAcceptableException();
        }
        User user = userRepository.findById(authorizedUser.getId()).orElse(null);
        if (user == null)
        {
            throw new NotAcceptableException();
        }

        if (vote == null)
        {
            vote = new Vote(today, rate);
            vote.setRestaurant(restaurant);
            vote.setUser(user);
            return votingRepository.save(vote);
        }
        else if (now.isBefore(DEADLINE_VOTE_HOUR))
        {
            vote.setRate(rate);
            return votingRepository.save(vote);
        }
        throw new NotAcceptableException();
    }
}
