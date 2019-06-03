package ru.apolyakov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.apolyakov.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface VotingRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id =?1 and v.user.id =?2 and v.created =?3")
    Optional<Vote> findByRestaurantIdAndUserIdAndDate(int restaurantId, int userId, LocalDate date);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id =?1 and v.user.id =?2 and v.created BETWEEN ?3 AND ?4")
    Optional<Vote> getByRestaurantIdAndUserIdAndDate(int restaurantId, int userId, LocalDate start, LocalDateTime end);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.user.id =?1 and v.created = ?2 ORDER BY v.created DESC")
    List<Vote> getVotesByUserIdByDate(int userId, LocalDate date);

    @Override
    @Transactional
    <S extends Vote> S save(S vote);
}
