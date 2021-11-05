package com.example.PlanetLords.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PlanetDTO {

    @Size(min = 2, message = "Имя не может быть меньше двух")
    String name;
}
