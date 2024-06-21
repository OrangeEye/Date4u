package com.materna.date4u.infastructure.repositories;

import com.materna.date4u.core.entities.Unicorn;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnicornRepository extends CrudRepository<Unicorn, Long> {

    default Optional<Unicorn> findByEmail(String email) {
        //TODO Suche verbessern
        for (Unicorn unicorn : this.findAll()) {
            if (unicorn.getEmail().equals(email)) return Optional.of(unicorn);
        }
        return Optional.empty();
    }

    // Optional<Unicorn> findUnicornByEmail(String email)
}

