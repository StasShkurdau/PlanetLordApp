package com.example.PlanetLords.repository;

import com.example.PlanetLords.models.Planet;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends PagingAndSortingRepository<Planet, Long> {

    Planet findByName(String name);
}
