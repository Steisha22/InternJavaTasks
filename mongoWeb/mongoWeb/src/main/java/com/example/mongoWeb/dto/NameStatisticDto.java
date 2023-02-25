package com.example.mongoWeb.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Builder
@Jacksonized
public class NameStatisticDto {
    private String firstName;
    private Integer count;
}
