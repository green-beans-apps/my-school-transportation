package com.greenbeansapps.myschooltransportation.infra.repositories.impl;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.AddressRepository;
import com.greenbeansapps.myschooltransportation.infra.repositories.IAddressRepositoryJPA;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class AddressRepositoryJPA implements AddressRepository {

  private final IAddressRepositoryJPA addressRepo;

  public AddressRepositoryJPA(IAddressRepositoryJPA addressRepo) {
    this.addressRepo = addressRepo;
  }

  @Override
  public Address create(Address address) {
    this.addressRepo.save(address);
    return address;
  }

  @Override
  public Optional<Address> findById(UUID addressId) {
    Optional<Address> address = this.addressRepo.findById(addressId);
    if(address.isEmpty()) {
      return Optional.empty();
    }
    return address;
  }

  @Override
  public Address updateAddress(Address address) {
    this.addressRepo.save(address);
    return address;
  }

  @Override
  public Boolean deleteAddress(UUID addressId) {
    Optional<Address> address = this.addressRepo.findById(addressId);
    if (address.isEmpty()) {
      return false;
    }

    addressRepo.delete(address.get());
    return true;
  }
}
