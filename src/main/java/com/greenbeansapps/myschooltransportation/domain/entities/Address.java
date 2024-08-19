package com.greenbeansapps.myschooltransportation.domain.entities;

import com.greenbeansapps.myschooltransportation.domain.utils.CapitalizeWords;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name= "address")
public class Address {
    @Id
    private UUID id;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String district;
    @Column(nullable = false)
    private String street;
    @Column()
    private String referencePoint;
    @Column(nullable = false)
    private String houseNumber;

    public Address() {
    }

    public Address(UUID id, String city, String district, String street, String referencePoint, String houseNumber) {
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
        this.city = CapitalizeWords.execute(city);
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        if (district == null || district.isEmpty()) {
            throw new IllegalArgumentException("District cannot be null or empty");
        }
        this.district = CapitalizeWords.execute(district);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        if (street == null || street.isEmpty()) {
            throw new IllegalArgumentException("Street cannot be null or empty");
        }
        this.street = CapitalizeWords.execute(street);
    }

    public String getReferencePoint() {
        return referencePoint;
    }

    public void setReferencePoint(String referencePoint) {
        if (referencePoint == null || referencePoint.isEmpty()) {
            this.referencePoint = "Sem Referência";
        } else {
            this.referencePoint = CapitalizeWords.execute(referencePoint);
        }
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        if (houseNumber.trim().isEmpty()) {
            this.houseNumber = "S/N";
        } else {
            this.houseNumber = houseNumber;
        }
    }

}
