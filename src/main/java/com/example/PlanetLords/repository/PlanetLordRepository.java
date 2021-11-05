package com.example.PlanetLords.repository;


import com.example.PlanetLords.models.PlanetLord;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PlanetLordRepository extends PagingAndSortingRepository<PlanetLord, Long> {
    PlanetLord findByName(String name);
    List<PlanetLord> findAll();
}
