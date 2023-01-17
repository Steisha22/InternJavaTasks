package com.example.cafe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishQueryDto {

    private double price;

    private String category_name;

    private int from;

    private int size;
}
