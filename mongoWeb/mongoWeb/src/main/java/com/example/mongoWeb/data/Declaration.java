package com.example.mongoWeb.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Declaration {
    @JsonProperty("position_en")
    private String positionEn;

    @JsonProperty("url")
    private String url;

    @JsonProperty("income")
    private String income;

    @JsonProperty("region_uk")
    private String regionUk;

    @JsonProperty("office_en")
    private String officeEn;

    @JsonProperty("position_uk")
    private String positionUk;

    @JsonProperty("year")
    private String year;

    @JsonProperty("office_uk")
    private String officeUk;

    @JsonProperty("region_en")
    private String regionEn;

    @JsonProperty("family_income")
    private String familyIncome;

    @Override
    public String toString() {
        return "Declaration{" +
                "positionEn='" + positionEn + '\'' +
                ", url='" + url + '\'' +
                ", income='" + income + '\'' +
                ", regionUk='" + regionUk + '\'' +
                ", officeEn='" + officeEn + '\'' +
                ", positionUk='" + positionUk + '\'' +
                ", year='" + year + '\'' +
                ", officeUk='" + officeUk + '\'' +
                ", regionEn='" + regionEn + '\'' +
                ", familyIncome='" + familyIncome + '\'' +
                '}';
    }
}
