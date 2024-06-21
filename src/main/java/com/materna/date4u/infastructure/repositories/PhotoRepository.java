package com.materna.date4u.infastructure.repositories;

import com.materna.date4u.core.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long>, QuerydslPredicateExecutor<Photo> {
    @Query("SELECT p FROM Photo p WHERE p.profile = :profileId")
    List<Photo> findPhotosByProfileId(long profileId);
}
