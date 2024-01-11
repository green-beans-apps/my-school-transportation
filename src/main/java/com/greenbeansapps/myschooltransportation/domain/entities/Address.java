package com.greenbeansapps.myschooltransportation.domain.entities;

import java.util.UUID;

public class Address {
    private UUID id;
    private String city;
    private String district;
    private String street;
    private String referencePoint;
    private Integer houseNumber;

    public Address() {
    }

    public Address(UUID id, String city, String district, String street, String referencePoint, Integer houseNumber) {
        this.id = id;
        this.city = city;
        this.district = district;
        this.street = street;
        this.referencePoint = referencePoint;
        this.houseNumber = houseNumber;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getReferencePoint() {
        return referencePoint;
    }

    public void setReferencePoint(String referencePoint) {
        this.referencePoint = referencePoint;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }
}
