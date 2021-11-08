package com.kati.core.domain.favorite.repository;

import com.kati.core.domain.favorite.domain.Favorite;
import com.kati.core.domain.food.domain.Food;
import com.kati.core.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long>{

    List<Favorite> findAllByUser(User user);

    void deleteByUserAndFood(User user, Food food);

    boolean existsByUserAndFood(User user, Food food);

    Long countAllByUser(User user);

}
