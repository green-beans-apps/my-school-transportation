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

  @Override
  public Optional<Address> updateAddress(Address address) {
    Optional<AddressSchema> addressSchema = this.addressRepo.findById(address.getId());
    if(addressSchema.isEmpty()) {
      return Optional.empty();
    }

    addressSchema.get().setCity(address.getCity());
    addressSchema.get().setDistrict(address.getDistrict());
    addressSchema.get().setStreet(address.getStreet());
    addressSchema.get().setReferencePoint(address.getReferencePoint());
    addressSchema.get().setHouseNumber(address.getHouseNumber());

    this.addressRepo.save(addressSchema.get());
    return Optional.of(new Address(addressSchema.get().getId(), addressSchema.get().getCity(), addressSchema.get().getDistrict(), addressSchema.get().getStreet(), addressSchema.get().getReferencePoint(), addressSchema.get().getHouseNumber()));
  }

  @Override
  public Boolean deleteAddress(UUID addressId) {
    Optional<AddressSchema> addressSchema = this.addressRepo.findById(addressId);
    if (addressSchema.isEmpty()) {
      return false;
    }

    addressRepo.delete(addressSchema.get());
    return true;
  }
}
