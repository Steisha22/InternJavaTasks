package com.example.cafe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@Jacksonized
public class DishSaveDto {

    @NotBlank(message = "dish_name is required")
    private String dish_name;

    @NotNull(message = "weight is required")
    private int weight;

    @NotNull(message = "price is required")
    private double price;

    private String description;

    @NotNull(message = "categoryId is required")
    private int categoryId;
}
