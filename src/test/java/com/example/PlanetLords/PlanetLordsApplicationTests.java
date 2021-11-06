package com.example.PlanetLords;

import com.example.PlanetLords.DTO.AppointRulerDTO;
import com.example.PlanetLords.DTO.PlanetDTO;
import com.example.PlanetLords.DTO.PlanetLordDTO;
import com.example.PlanetLords.models.PlanetLord;
import com.example.PlanetLords.repository.PlanetRepository;
import com.example.PlanetLords.services.MainService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Метод destroyPlanet(planetDTO) был протестирован в addPlanetTest() и appointRulerActionTest()

@SpringBootTest
class PlanetLordsApplicationTests {

	@Autowired
	MainService mainService;

	@Autowired	SessionFactory sessionFactory;

	@Test
	void addPlanetTest() {
		PlanetDTO planetDTO = new PlanetDTO();
		String testPlanetName = "Test_planet";
		planetDTO.setName(testPlanetName);
		mainService.addPlanet(planetDTO);
		assertEquals(testPlanetName, mainService.getPlanetByName(testPlanetName).getName());
		mainService.destroyPlanet(planetDTO);
		assertEquals(null, mainService.getPlanetByName(testPlanetName));
	}

	@Test
	void addPlanetLordTest() {
		PlanetLordDTO planetLordDTO = new PlanetLordDTO();
		String testPlanetLordName = "Test_Lord";
		planetLordDTO.setName(testPlanetLordName);
		planetLordDTO.setAge(18);
		mainService.addPlanetLord(planetLordDTO);
		assertEquals(testPlanetLordName, mainService.getPlanetLordByName(testPlanetLordName).getName());
		mainService.deletePlanetLord(planetLordDTO);
	}

	@Test
	void appointRulerActionTest() {
		PlanetDTO planetDTO = new PlanetDTO();
		String testPlanetName = "Test_planet";
		planetDTO.setName(testPlanetName);
		mainService.addPlanet(planetDTO);

		PlanetLordDTO planetLordDTO = new PlanetLordDTO();
		String testPlanetLordName = "Test_Lord";
		planetLordDTO.setName(testPlanetLordName);
		planetLordDTO.setAge(18);
		mainService.addPlanetLord(planetLordDTO);

		AppointRulerDTO appointRulerDTO = new AppointRulerDTO();
		appointRulerDTO.setPlanetLordName(testPlanetLordName);
		appointRulerDTO.setPlanetName(testPlanetName);

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		mainService.appointRuler(appointRulerDTO);

		PlanetLord returnedPlanetLord = mainService.getPlanetLordByName(testPlanetLordName);

		assertEquals(testPlanetName, returnedPlanetLord.getPlanets().get(0).getName());
		mainService.destroyPlanet(planetDTO);
		mainService.deletePlanetLord(planetLordDTO);
	}

}
