package com.example.PlanetLords.services.impl;

import com.example.PlanetLords.DTO.AppointRulerDTO;
import com.example.PlanetLords.DTO.PlanetDTO;
import com.example.PlanetLords.DTO.PlanetLordDTO;
import com.example.PlanetLords.models.Planet;
import com.example.PlanetLords.models.PlanetLord;
import com.example.PlanetLords.repository.PlanetLordRepository;
import com.example.PlanetLords.repository.PlanetRepository;
import com.example.PlanetLords.services.MainService;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private final PlanetLordRepository planetLordRepository;
    @Autowired
    private final PlanetRepository planetRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public MainServiceImpl(PlanetLordRepository planetLordRepository, PlanetRepository planetRepository) {
        this.planetLordRepository = planetLordRepository;
        this.planetRepository = planetRepository;
    }

    @Override
    public void addPlanet(PlanetDTO planetDTO) {
        Planet planet = new Planet();
        planet.setName(planetDTO.getName());
        planetRepository.save(planet);
        LOGGER.info(String.format("Add planet %s to DB", planet.getName()));
    }

    @Override
    public void addPlanetLord(PlanetLordDTO planetLordDTO) {
        PlanetLord planetLord = new PlanetLord();
        planetLord.setAge(planetLordDTO.getAge());
        planetLord.setName(planetLordDTO.getName());
        planetLordRepository.save(planetLord);
        LOGGER.info(String.format("Add planetLord %s to DB", planetLord.getName()));
    }

    @Override
    public String appointRuler(AppointRulerDTO appointRulerDTO) {
        Planet planet = planetRepository.findByName(appointRulerDTO.getPlanetName());
        if(isNull(planet)){
            String warnMessage = String.format("The planet with name %s is not exist", appointRulerDTO.getPlanetName());
            LOGGER.warn(warnMessage);
            return warnMessage;
        }
        PlanetLord planetLord = planetLordRepository.findByName(appointRulerDTO.getPlanetLordName());
        if(isNull(planetLord)){
            String warnMessage = String.format("The Planet Lord with name %s is not exist", appointRulerDTO.getPlanetLordName());
            LOGGER.warn(warnMessage);
            return warnMessage;
        }
     //   planet.setPlanetLord(planetLord);
     //   planetRepository.save(planet);
        planetLord.getPlanets().add(planet);
        planetLordRepository.save(planetLord);
        LOGGER.info("User called the method appointRuler()");
        return "ok";
    }

    @Override
    public String destroyPlanet(PlanetDTO planetDTO) {
        Planet planet = planetRepository.findByName(planetDTO.getName());
        if(isNull(planet)){
            String warnMessage = String.format("The planet with name %s is not exist", planetDTO.getName());
            LOGGER.warn(warnMessage);
            return warnMessage;
        }else{
            planetRepository.delete(planet);
        }
        LOGGER.info("User called the method destroyPlanet()");
        return "ok";
    }

    @Override
    public List<PlanetLord> listLosers() {
        List<PlanetLord> lordList = planetLordRepository.findAll();
        List<PlanetLord> listToReturn = new ArrayList<>();
        for(int i = 0; i < lordList.size(); i++){
            if(lordList.get(i).getPlanets().size() == 0){
                listToReturn.add(lordList.get(i));
            }
        }
        LOGGER.info("User called the method listLosers()");
        return listToReturn;
    }

    @Override
    public List<PlanetLord> showYoungestLords() {

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(PlanetLord.class)
                .addOrder(Order.asc("age"))
                .setMaxResults(10);
        LOGGER.info("User called the method showYoungestLords()");
                return criteria.list();

    }

}
