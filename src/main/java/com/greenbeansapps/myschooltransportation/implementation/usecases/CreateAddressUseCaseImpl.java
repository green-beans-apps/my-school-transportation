package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateAddressUseCase;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.AddressRepository;

import java.util.UUID;

public class CreateAddressUseCaseImpl implements CreateAddressUseCase{
    private final AddressRepository addressRepo;

    public CreateAddressUseCaseImpl(AddressRepository addressRepo) {
        this.addressRepo = addressRepo;
    }


    @Override
    public Address execute(String city, String district, String street, Integer houseNumber) {
        var newAddress = new Address(UUID.randomUUID(), city, district, street, houseNumber);
        this.addressRepo.create(newAddress);
        return  newAddress;
    }
}
