package com.materna.date4u.infastructure.repositories;

import com.materna.date4u.core.entities.Profile;
import com.materna.date4u.infastructure.repositories.filter.SearchFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long>, QuerydslPredicateExecutor<Profile> {

    @Query("SELECT p FROM Profile p WHERE p.nickname = :nickname")
    Optional<Profile> findProfileByNickname(String nickname);

    @Query("SELECT p FROM Profile p WHERE p.hornlength BETWEEN :min AND :max")
    List<Profile> findProfilesByHornlengthBetween(short min, short max);

    @Query("""
            SELECT p
            FROM   Profile p
            WHERE  (p.attractedToGender= :#{#filter.myGender} OR p.attractedToGender IS NULL)
              AND (p.gender = :#{#filter.attractedToGender} OR :#{#filter.attractedToGender} IS NULL)
              AND (p.hornlength BETWEEN :#{#filter.minHornlength} AND :#{#filter.maxHornlength})
              AND (p.birthdate  BETWEEN :#{#filter.minBirthdate}  AND :#{#filter.maxBirthdate})""")
    List<Profile> search(SearchFilter filter);

}
