package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateAddressUseCase;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateAddressUseCaseImpl implements CreateAddressUseCase{
    private final AddressRepository addressRepo;

    public CreateAddressUseCaseImpl(AddressRepository addressRepo) {
        this.addressRepo = addressRepo;
    }

    @Override
    public Address execute(UUID id, String city, String district, String street, String referencePoint, String houseNumber) {
        var newAddress = new Address(id, city, district, street, referencePoint, houseNumber);
        return this.addressRepo.create(newAddress);
    }
}
