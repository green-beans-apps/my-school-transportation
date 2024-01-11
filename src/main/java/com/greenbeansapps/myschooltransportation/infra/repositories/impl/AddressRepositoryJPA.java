package com.greenbeansapps.myschooltransportation.infra.repositories.impl;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.AddressRepository;
import com.greenbeansapps.myschooltransportation.infra.repositories.IAddressRepositoryJPA;
import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.AddressSchema;
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
    var newAddress = new AddressSchema();
    BeanUtils.copyProperties(address, newAddress);
    this.addressRepo.save(newAddress);
    return address;
  }

  @Override
  public Optional<Address> findById(UUID addressId) {
    Optional<AddressSchema> addressSchema = this.addressRepo.findById(addressId);
    if(addressSchema.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(new Address(addressSchema.get().getId(), addressSchema.get().getCity(), addressSchema.get().getDistrict(), addressSchema.get().getStreet(), addressSchema.get().getReferencePoint(), addressSchema.get().getHouseNumber()));
  }
}
