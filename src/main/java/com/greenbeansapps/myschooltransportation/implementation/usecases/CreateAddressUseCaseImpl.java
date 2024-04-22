package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateAddressUseCase;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.CreateAddressRequest;
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
    public Address execute(CreateAddressRequest data) {
        var newAddress = new Address(data.id(), data.city(), data.district(), data.street(), data.referencePoint(), data.houseNumber());
        return this.addressRepo.create(newAddress);
    }
}
