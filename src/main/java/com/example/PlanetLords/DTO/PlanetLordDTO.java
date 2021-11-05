package com.example.PlanetLords.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PlanetLordDTO {

    @Size(min = 2, message = "Имя не может быть меньше двух")
    String name;

    @Min(value = 2, message = "Возраст не может быть отрицательным")
    @Max(value = 300, message = "Возраст не может быть больше 300 лет")
    int age;
}
