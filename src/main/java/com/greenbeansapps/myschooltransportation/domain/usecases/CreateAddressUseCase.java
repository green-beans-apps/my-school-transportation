package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;

import java.util.UUID;

public interface CreateAddressUseCase {
    public Address execute(UUID id, String city, String district, String street, String referencePoint, String houseNumber);
}
