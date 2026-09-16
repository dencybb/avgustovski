package org.randomplace.repository;

import org.randomplace.entity.RandomPlace;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RandomPlaceRepository implements PanacheRepository<RandomPlace> {
}