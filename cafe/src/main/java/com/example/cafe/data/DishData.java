package com.example.cafe.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishData {
    private int id_dish;

    private String dish_name;

    private int weight;

    private double price;

    private int categoryId;

    private String description;

}
