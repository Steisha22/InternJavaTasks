package com.example.cafe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class DishInfoDto {

    private String dish_name;

    private int weight;

    private double price;

    private String dish_category;

    private String description;

}
