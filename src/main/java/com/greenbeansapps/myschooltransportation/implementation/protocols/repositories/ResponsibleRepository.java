package com.greenbeansapps.myschooltransportation.implementation.protocols.repositories;

import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;

import java.util.Optional;
import java.util.UUID;

public interface ResponsibleRepository {
    public Responsible create(Responsible responsible);
    public Optional<Responsible> findById(UUID responsibleId);
    public Optional<Responsible> findByEmail(String email);
    public Optional<Responsible> findByPhoneNumber(String phoneNumber);
    public Optional<Responsible> updateResponsible(Responsible responsible);
}
