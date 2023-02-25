package com.example.mongoWeb.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Builder
@Document("pepData")
@FieldNameConstants
public class PepData {
    @Id
    private String id;
    @JsonProperty("last_job_title")
    private String lastJobTitle;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("wiki_uk")
    private String wikiUk;
    @JsonProperty("photo")
    private String photo;
    @JsonProperty("reputation_convictions_en")
    private String reputationConvictionsEn;
    @JsonProperty("first_name_en")
    private String firstNameEn;
    @JsonProperty("last_workplace_en")
    private String lastWorkplaceEn;
    @JsonProperty("names")
    private String names;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("patronymic")
    private String patronymic;
    @JsonProperty("also_known_as_en")
    private String alsoKnownAsEn;
    @JsonProperty("reputation_manhunt_uk")
    private String reputationManhuntUk;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("declarations")
    private List<Declaration> declarations;
    @JsonProperty("related_countries")
    private List<RelatedCountries> related_countries;
    @JsonProperty("reputation_sanctions_uk")
    private String reputationSanctionsUk;
    @JsonProperty("related_companies")
    private List<RelatedCompany> relatedCompanies;
    @JsonProperty("date_of_birth")
    private String dateOfBirth;
    @JsonProperty("patronymic_en")
    private String patronymicEn;
    @JsonProperty("reason_of_termination_en")
    private String reasonOfTerminationEn;
    @JsonProperty("reputation_assets_en")
    private String reputationAssetsEn;
    @JsonProperty("related_persons")
    private List<RelatedPersons> relatedPersons;
    @JsonProperty("reputation_convictions_uk")
    private String reputationConvictionsUk;
    @JsonProperty("reputation_crimes_en")
    private String reputationCrimesEn;
    @JsonProperty("reason_of_termination")
    private String reasonOfTermination;
    @JsonProperty("full_name_en")
    private String fullNameEn;
    @JsonProperty("city_of_birth")
    private String cityOfBirth;
    @JsonProperty("city_of_birth_uk")
    private String cityOfBirthUk;
    @JsonProperty("type_of_official")
    private String typeOfOfficial;
    @JsonProperty("died")
    private boolean died;
    @JsonProperty("last_name_en")
    private String lastNameEn;
    @JsonProperty("last_job_title_en")
    private String lastJobTitleEn;
    @JsonProperty("is_pep")
    private boolean isPep;
    @JsonProperty("reputation_manhunt_en")
    private String reputationManhuntEn;
    @JsonProperty("also_known_as_uk")
    private String alsoKnownAsUk;
    @JsonProperty("url")
    private String url;
    @JsonProperty("termination_date_human")
    private String terminationDateHuman;
    @JsonProperty("last_workplace")
    private String lastWorkplace;
    @JsonProperty("city_of_birth_en")
    private String cityOfBirthEn;
    @JsonProperty("reputation_sanctions_en")
    private String reputationSanctionsEn;
    @JsonProperty("reputation_crimes_uk")
    private String reputationCrimesUk;
    @JsonProperty("wiki_en")
    private String wikiEn;
    @JsonProperty("reputation_assets_uk")
    private String reputationAssetsUk;
    @JsonProperty("type_of_official_en")
    private String typeOfOfficialEn;

    public PepData() {
    }

    public PepData(String id, String lastJobTitle, String lastName, String wikiUk, String photo, String reputationConvictionsEn, String firstNameEn, String lastWorkplaceEn, String names, String fullName, String patronymic, String alsoKnownAsEn, String reputationManhuntUk, String firstName, List<Declaration> declarations, List<RelatedCountries> related_countries, String reputationSanctionsUk, List<RelatedCompany> relatedCompanies, String dateOfBirth, String patronymicEn, String reasonOfTerminationEn, String reputationAssetsEn, List<RelatedPersons> relatedPersons, String reputationConvictionsUk, String reputationCrimesEn, String reasonOfTermination, String fullNameEn, String cityOfBirth, String cityOfBirthUk, String typeOfOfficial, boolean died, String lastNameEn, String lastJobTitleEn, boolean isPep, String reputationManhuntEn, String alsoKnownAsUk, String url, String terminationDateHuman, String lastWorkplace, String cityOfBirthEn, String reputationSanctionsEn, String reputationCrimesUk, String wikiEn, String reputationAssetsUk, String typeOfOfficialEn) {
        this.id = id;
        this.lastJobTitle = lastJobTitle;
        this.lastName = lastName;
        this.wikiUk = wikiUk;
        this.photo = photo;
        this.reputationConvictionsEn = reputationConvictionsEn;
        this.firstNameEn = firstNameEn;
        this.lastWorkplaceEn = lastWorkplaceEn;
        this.names = names;
        this.fullName = fullName;
        this.patronymic = patronymic;
        this.alsoKnownAsEn = alsoKnownAsEn;
        this.reputationManhuntUk = reputationManhuntUk;
        this.firstName = firstName;
        this.declarations = declarations;
        this.related_countries = related_countries;
        this.reputationSanctionsUk = reputationSanctionsUk;
        this.relatedCompanies = relatedCompanies;
        this.dateOfBirth = dateOfBirth;
        this.patronymicEn = patronymicEn;
        this.reasonOfTerminationEn = reasonOfTerminationEn;
        this.reputationAssetsEn = reputationAssetsEn;
        this.relatedPersons = relatedPersons;
        this.reputationConvictionsUk = reputationConvictionsUk;
        this.reputationCrimesEn = reputationCrimesEn;
        this.reasonOfTermination = reasonOfTermination;
        this.fullNameEn = fullNameEn;
        this.cityOfBirth = cityOfBirth;
        this.cityOfBirthUk = cityOfBirthUk;
        this.typeOfOfficial = typeOfOfficial;
        this.died = died;
        this.lastNameEn = lastNameEn;
        this.lastJobTitleEn = lastJobTitleEn;
        this.isPep = isPep;
        this.reputationManhuntEn = reputationManhuntEn;
        this.alsoKnownAsUk = alsoKnownAsUk;
        this.url = url;
        this.terminationDateHuman = terminationDateHuman;
        this.lastWorkplace = lastWorkplace;
        this.cityOfBirthEn = cityOfBirthEn;
        this.reputationSanctionsEn = reputationSanctionsEn;
        this.reputationCrimesUk = reputationCrimesUk;
        this.wikiEn = wikiEn;
        this.reputationAssetsUk = reputationAssetsUk;
        this.typeOfOfficialEn = typeOfOfficialEn;
    }

    @Override
    public String toString() {
        return "PepData{" +
                "lastJobTitle='" + lastJobTitle + '\'' +
                ", lastName='" + lastName + '\'' +
                ", wikiUk='" + wikiUk + '\'' +
                ", photo='" + photo + '\'' +
                ", reputationConvictionsEn='" + reputationConvictionsEn + '\'' +
                ", firstNameEn='" + firstNameEn + '\'' +
                ", lastWorkplaceEn='" + lastWorkplaceEn + '\'' +
                ", names='" + names + '\'' +
                ", fullName='" + fullName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", alsoKnownAsEn='" + alsoKnownAsEn + '\'' +
                ", reputationManhuntUk='" + reputationManhuntUk + '\'' +
                ", firstName='" + firstName + '\'' +
                ", declarations=" + declarations +
                ", related_countries=" + related_countries +
                ", reputationSanctionsUk='" + reputationSanctionsUk + '\'' +
                ", relatedCompanies=" + relatedCompanies +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", patronymicEn='" + patronymicEn + '\'' +
                ", reasonOfTerminationEn='" + reasonOfTerminationEn + '\'' +
                ", reputationAssetsEn='" + reputationAssetsEn + '\'' +
                ", relatedPersons=" + relatedPersons +
                ", reputationConvictionsUk='" + reputationConvictionsUk + '\'' +
                ", reputationCrimesEn='" + reputationCrimesEn + '\'' +
                ", reasonOfTermination='" + reasonOfTermination + '\'' +
                ", fullNameEn='" + fullNameEn + '\'' +
                ", cityOfBirth='" + cityOfBirth + '\'' +
                ", cityOfBirthUk='" + cityOfBirthUk + '\'' +
                ", typeOfOfficial='" + typeOfOfficial + '\'' +
                ", died=" + died +
                ", lastNameEn='" + lastNameEn + '\'' +
                ", lastJobTitleEn='" + lastJobTitleEn + '\'' +
                ", isPep=" + isPep +
                ", reputationManhuntEn='" + reputationManhuntEn + '\'' +
                ", alsoKnownAsUk='" + alsoKnownAsUk + '\'' +
                ", url='" + url + '\'' +
                ", terminationDateHuman='" + terminationDateHuman + '\'' +
                ", lastWorkplace='" + lastWorkplace + '\'' +
                ", cityOfBirthEn='" + cityOfBirthEn + '\'' +
                ", reputationSanctionsEn='" + reputationSanctionsEn + '\'' +
                ", reputationCrimesUk='" + reputationCrimesUk + '\'' +
                ", wikiEn='" + wikiEn + '\'' +
                ", reputationAssetsUk='" + reputationAssetsUk + '\'' +
                ", typeOfOfficialEn='" + typeOfOfficialEn + '\'' +
                '}';
    }
}
