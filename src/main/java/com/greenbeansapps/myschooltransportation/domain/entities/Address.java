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
        setId(id);
        setCity(city);
        setDistrict(district);
        setStreet(street);
        setReferencePoint(referencePoint);
        setHouseNumber(houseNumber);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        if (id == null) {
            this.id = UUID.randomUUID();
        } else {
            this.id = id;
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (city == null || city.isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        if (district == null || district.isEmpty()) {
            throw new IllegalArgumentException("District cannot be null or empty");
        }
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        if (street == null || street.isEmpty()) {
            throw new IllegalArgumentException("Street cannot be null or empty");
        }
        this.street = street;
    }

    public String getReferencePoint() {
        return referencePoint;
    }

    public void setReferencePoint(String referencePoint) {
        if (referencePoint == null || referencePoint.isEmpty()) {
            throw new IllegalArgumentException("Reference point cannot be null or empty");
        }
        this.referencePoint = referencePoint;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        if (houseNumber == null) {
            throw new IllegalArgumentException("House number cannot be null");
        }
        this.houseNumber = houseNumber;
    }
}
