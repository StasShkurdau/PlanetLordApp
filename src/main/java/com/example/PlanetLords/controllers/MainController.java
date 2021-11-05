package com.example.PlanetLords.controllers;

import com.example.PlanetLords.DTO.AppointRulerDTO;
import com.example.PlanetLords.DTO.PlanetDTO;
import com.example.PlanetLords.DTO.PlanetLordDTO;
import com.example.PlanetLords.services.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
@RequestMapping("/main")
public class MainController {

    private MainService mainService;

    public MainController(MainService service) {
        this.mainService = service;
    }

    @GetMapping(path = "")
    public String showMainPage(){
        return "main_page";
    }

    @PostMapping(path = "/addPlanetLordAction")
    public String addPlanetLord(@ModelAttribute("planetLord") @Valid PlanetLordDTO planetLordDTO,
                                BindingResult bindingResult,
                                Model model,
                                HttpSession httpSession){
        if (bindingResult.hasErrors()) {
            //   model.addAttribute("sections", sectionRepository.findAll())
            return "add_planet_lord";
        } else {
            mainService.addPlanetLord(planetLordDTO);
            model.addAttribute("message", String.format("Add planetLord %s to DB", planetLordDTO.getName()));
            return "main_page";
        }
    }

    @PostMapping(path = "/addPlanetAction")
    public String addPlanet(@ModelAttribute("planet") @Valid PlanetDTO planetDTO,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            //   model.addAttribute("sections", sectionRepository.findAll())
            return "add_planet";
        } else {
            mainService.addPlanet(planetDTO);
            model.addAttribute("message", String.format("Add planet %s to DB", planetDTO.getName()));
            return "main_page";
        }
    }

    @GetMapping(path = "/addPlanet")
    public String addPlanet(Model model){
        model.addAttribute("planet", new PlanetDTO());
        return "add_planet";
    }

    @GetMapping(path = "/addPlanetLord")
    public String addPlanetLord(Model model){
        model.addAttribute("planetLord", new PlanetLordDTO());
        return "add_planet_lord";
    }

    @GetMapping(path = "/appointRuler")
    public String appointRuler(Model model){
        model.addAttribute("appointRuler", new AppointRulerDTO());
        return "planet_lord_binding";
    }

    @GetMapping(path = "/destroyPlanet")
    public String destroyPlanet(Model model){
        model.addAttribute("planet", new PlanetDTO());
        return "destroy_planet";
    }

    @GetMapping(path = "/showLosers")
    public String showLosers(Model model){
        model.addAttribute("LordList", mainService.listLosers());
        return "show_lord_losers";
    }

    @GetMapping(path = "/showYoungestLords")
    public String showYoungestLords(Model model){
        model.addAttribute("LordList", mainService.showYoungestLords());
        return "show_youngest_lord";
    }

    @PostMapping(path = "/destroyPlanetAction")
    public String destroyPlanetAction(@ModelAttribute("appointRuler")PlanetDTO planetDTO, Model model){
        String responseMessage = mainService.destroyPlanet(planetDTO);

        if(responseMessage != "ok"){
            model.addAttribute("planet", new PlanetDTO());
            model.addAttribute("message_error", responseMessage);
            return "destroy_planet";
        }
        model.addAttribute("message", String.format("Planet %s destroyed", planetDTO.getName()));
        return "main_page";
    }

    @PostMapping(path = "/appointRulerAction")
    public String appointRulerAction(@ModelAttribute("appointRuler") AppointRulerDTO appointRulerDTO,
                               Model model){
       String responseMessage = mainService.appointRuler(appointRulerDTO);

       if(responseMessage != "ok"){
           model.addAttribute("message_error", responseMessage);
           model.addAttribute("appointRuler", appointRulerDTO);
           return "planet_lord_binding";
       }else{
           model.addAttribute("message", String.format("The %s is appointed to rule the %s planet", appointRulerDTO.getPlanetLordName(), appointRulerDTO.getPlanetName()));
           return "main_page";
       }

    }
}
