package com.example.mongoWeb.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class PepInfoDto {
    private String id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private boolean isPep;
}
