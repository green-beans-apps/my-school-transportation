package com.greenbeansapps.myschooltransportation.implementation.protocols.repositories;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;

import java.util.Optional;
import java.util.UUID;

public interface AddressRepository {
    public Address create(Address address);
    public Optional<Address> findById(UUID addressId);
    public Optional<Address> updateAddress(Address address);
    public Boolean deleteAddress(UUID addressId);
}
