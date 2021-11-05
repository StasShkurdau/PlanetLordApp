package com.example.PlanetLords.services;

import com.example.PlanetLords.DTO.AppointRulerDTO;
import com.example.PlanetLords.DTO.PlanetDTO;
import com.example.PlanetLords.DTO.PlanetLordDTO;
import com.example.PlanetLords.models.PlanetLord;

import java.util.List;


public interface MainService {
    void addPlanet(PlanetDTO planetDTO);

    void addPlanetLord(PlanetLordDTO planetLordDTO);

    String appointRuler(AppointRulerDTO appointRulerDTO);

    String destroyPlanet(PlanetDTO planetDTO);

    List<PlanetLord> listLosers();

    List<PlanetLord> showYoungestLords();
}
