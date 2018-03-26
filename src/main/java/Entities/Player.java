package Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Player {

    private String firstName;
    private String lastName;
    private String photo;
    private int eligible_for_offense_and_defense;
    private String position;
    private int age;
    private String elias_id;
    private String pro_status;
    private String bats;
    private String fullName;
    private String id;
    private String throw_s;
    private String pro_team;
    private String sport;


    @JsonProperty("firstname")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstname")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("lastname")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastname")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("photo")
    public String getPhoto() {
        return photo;
    }

    @JsonProperty("photo")
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @JsonProperty("eligible_for_offense_and_defense")
    public int getEligible_for_offense_and_defense() {
        return eligible_for_offense_and_defense;
    }

    @JsonProperty("eligible_for_offense_and_defense")
    public void setEligible_for_offense_and_defense(int eligible_for_offense_and_defense) {
        this.eligible_for_offense_and_defense = eligible_for_offense_and_defense;
    }

    @JsonProperty("position")
    public String getPosition() {
        return position;
    }

    @JsonProperty("position")
    public void setPosition(String position) {
        this.position = position;
    }

    @JsonProperty("age")
    public int getAge() {
        return age;
    }

    @JsonProperty("age")
    public void setAge(int age) {
        this.age = age;
    }

    @JsonProperty("elias_id")
    public String getElias_id() {
        return elias_id;
    }

    @JsonProperty("elias_id")
    public void setElias_id(String elias_id) {
        this.elias_id = elias_id;
    }

    @JsonProperty("pro_status")
    public String getPro_status() {
        return pro_status;
    }

    @JsonProperty("pro_status")
    public void setPro_status(String pro_status) {
        this.pro_status = pro_status;
    }

    @JsonProperty("bats")
    public String getBats() {
        return bats;
    }

    @JsonProperty("bats")
    public void setBats(String bats) {
        this.bats = bats;
    }

    @JsonProperty("fullname")
    public String getFullName() {
        return fullName;
    }

    @JsonProperty("fullname")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("throws")
    public String getThrow_s() {
        return throw_s;
    }

    @JsonProperty("throws")
    public void setThrow_s(String throw_s) {
        this.throw_s = throw_s;
    }

    @JsonProperty("pro_team")
    public String getPro_team() {
        return pro_team;
    }

    @JsonProperty("pro_team")
    public void setPro_team(String pro_team) {
        this.pro_team = pro_team;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }


}
