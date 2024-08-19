package com.greenbeansapps.myschooltransportation.infra.repositories.impl;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ResponsibleRepository;
import com.greenbeansapps.myschooltransportation.infra.repositories.IResponsibleRepositoryJPA;
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
        this.responsibleRepo.save(responsible);
        return responsible;
    }

    @Override
    public Optional<Responsible> findById(UUID responsibleId) {
        Optional<Responsible> responsible = this.responsibleRepo.findById(responsibleId);
        if (responsible.isEmpty()) {
            return Optional.empty();
        }
        return responsible;
    }

    @Override
    public Optional<Responsible> findByEmail(String email) {
        Optional<Responsible> responsible = this.responsibleRepo.findByEmail(email);
        if (responsible.isEmpty()) {
            return Optional.empty();
        }
        return responsible;
    }

    @Override
    public Optional<Responsible> findByphone(String phone) {
        Optional<Responsible> responsible = this.responsibleRepo.findByphone(phone);
        if (responsible.isEmpty()) {
            return Optional.empty();
        }
        return responsible;
    }

    @Override
    public Responsible updateResponsible(Responsible responsible) {
        Optional<Responsible> oldResponsible = this.responsibleRepo.findById(responsible.getId());

        oldResponsible.get().setName(responsible.getName());
        oldResponsible.get().setEmail(responsible.getEmail());
        oldResponsible.get().setPhone(responsible.getPhone());

        this.responsibleRepo.save(oldResponsible.get());
        return responsible;
    }

    @Override
    public Boolean deleteResponsible(UUID responsibleId) {
        Optional<Responsible> responsible = this.responsibleRepo.findById(responsibleId);
        if (responsible.isEmpty()) {
            return false;
        }

        responsibleRepo.delete(responsible.get());
        return true;
    }
}
