package com.example.cafe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class CategoryInfoDto {
    private int id;
    private String category_name;
}
