package com.greenbeansapps.myschooltransportation.implementation.protocols.repositories;

import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;

import java.util.Optional;

public interface ResponsibleRepository {
    public Responsible create(Responsible responsible);
    public Optional<Responsible> findByEmail(String email);
    public Optional<Responsible> findByPhoneNumber(String phoneNumber);
}
