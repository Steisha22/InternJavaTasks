package com.example.cafe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@Jacksonized
public class CategorySaveDto {
    @NotNull(message = "id_dish is required")
    private int id_category;

    @NotBlank(message = "Category name is required")
    private String category_name;
}
