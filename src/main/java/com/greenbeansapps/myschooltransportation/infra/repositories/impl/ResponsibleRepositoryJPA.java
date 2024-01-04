package com.greenbeansapps.myschooltransportation.infra.repositories.impl;

import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ResponsibleRepository;
import com.greenbeansapps.myschooltransportation.infra.repositories.IResponsibleRepositoryJPA;
import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.ResponsibleSchema;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ResponsibleRepositoryJPA implements ResponsibleRepository {

    private final IResponsibleRepositoryJPA responsibleRepo;

    public ResponsibleRepositoryJPA(IResponsibleRepositoryJPA responsibleRepo) {
        this.responsibleRepo = responsibleRepo;
    }

    @Override
    public Responsible create(Responsible responsible) {
        var newResponsible = new ResponsibleSchema();
        BeanUtils.copyProperties(responsible, newResponsible);
        this.responsibleRepo.save(newResponsible);
        return responsible;
    }

    @Override
    public Optional<Responsible> findById(UUID responsibleId) {
        Optional<ResponsibleSchema> responsibleSchema = this.responsibleRepo.findById(responsibleId);
        if (responsibleSchema.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new Responsible(responsibleSchema.get().getId(), responsibleSchema.get().getName(), responsibleSchema.get().getEmail(), responsibleSchema.get().getPhoneNumber()));
    }

    @Override
    public Optional<Responsible> findByEmail(String email) {
        Optional<ResponsibleSchema> responsibleSchema = this.responsibleRepo.findByEmail(email);
        if (responsibleSchema.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new Responsible(responsibleSchema.get().getId(), responsibleSchema.get().getName(), responsibleSchema.get().getEmail(), responsibleSchema.get().getPhoneNumber()));
    }

    @Override
    public Optional<Responsible> findByPhoneNumber(String phoneNumber) {
        Optional<ResponsibleSchema> responsibleSchema = this.responsibleRepo.findByPhoneNumber(phoneNumber);
        if (responsibleSchema.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new Responsible(responsibleSchema.get().getId(), responsibleSchema.get().getName(), responsibleSchema.get().getEmail(), responsibleSchema.get().getPhoneNumber()));
    }
}
