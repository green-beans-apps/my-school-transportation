package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.CreateAddressRequest;

import java.util.UUID;

public interface CreateAddressUseCase {
    public Address execute(CreateAddressRequest data);
}
