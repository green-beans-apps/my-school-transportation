package com.greenbeansapps.myschooltransportation.infra.repositories.schemas;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;


@Entity
@Table(name= "address")
public class AddressSchema implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  private UUID id;
  @Column(nullable = false)
  private String city;
  @Column(nullable = false)
  private String district;
  @Column(nullable = false)
  private String street;
  @Column(nullable = false)
  private String referencePoint;
  @Column(nullable = false)
  private String houseNumber;

  public AddressSchema() {
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

  public String getHouseNumber() {
    return houseNumber;
  }

  public void setHouseNumber(String houseNumber) {
    this.houseNumber = houseNumber;
  }
}
