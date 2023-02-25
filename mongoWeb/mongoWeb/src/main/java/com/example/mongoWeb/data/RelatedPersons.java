package com.example.mongoWeb.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RelatedPersons {
    @JsonProperty("relationship_type_en")
    private String relationshipTypeEn;

    @JsonProperty("date_established")
    private String dateEstablished;

    @JsonProperty("person_en")
    private String personEn;

    @JsonProperty("date_confirmed")
    private String dateConfirmed;

    @JsonProperty("is_pep")
    private String isPep;

    @JsonProperty("date_finished")
    private String dateFinished;

    @JsonProperty("person_uk")
    private String personUk;

    @JsonProperty("relationship_type")
    private String relationshipType;

    @Override
    public String toString() {
        return "RelatedPersons{" +
                "relationshipTypeEn='" + relationshipTypeEn + '\'' +
                ", dateEstablished='" + dateEstablished + '\'' +
                ", personEn='" + personEn + '\'' +
                ", dateConfirmed='" + dateConfirmed + '\'' +
                ", isPep='" + isPep + '\'' +
                ", dateFinished='" + dateFinished + '\'' +
                ", personUk='" + personUk + '\'' +
                ", relationshipType='" + relationshipType + '\'' +
                '}';
    }
}
