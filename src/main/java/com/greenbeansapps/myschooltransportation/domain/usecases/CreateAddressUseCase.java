package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;

public interface CreateAddressUseCase {
    public Address execute(String city, String district, String street, Integer houseNumber);
}
