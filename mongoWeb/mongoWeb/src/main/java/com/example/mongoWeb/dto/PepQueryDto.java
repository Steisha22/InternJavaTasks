package com.example.mongoWeb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PepQueryDto extends QueryDto{
    private String firstName;

    private String lastName;

    private String patronymic;
}
