package com.greenbeansapps.myschooltransportation.infra.repositories.impl;

import com.greenbeansapps.myschooltransportation.domain.entities.MonthlyFee;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.MonthlyFeeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MonthlyFreeRepositoryJPA implements MonthlyFeeRepository {

    private final MonthlyFeeRepository monthlyFeeRepo;

    public MonthlyFreeRepositoryJPA(MonthlyFeeRepository monthlyFeeRepo) {
        this.monthlyFeeRepo = monthlyFeeRepo;
    }

    @Override
    public MonthlyFee create(MonthlyFee monthlyFee) {
        this.monthlyFeeRepo.create(monthlyFee);
        return monthlyFee;
    }

    @Override
    public Optional<MonthlyFee> findById(UUID monthlyFeeId) {
        Optional<MonthlyFee> monthlyFee = this.monthlyFeeRepo.findById(monthlyFeeId);
        if (monthlyFee.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(monthlyFee.get());
    }

    @Override
    public Boolean deleteMonthlyFee(UUID monthlyFeeId) {
        Optional<MonthlyFee> monthlyFee = this.monthlyFeeRepo.findById(monthlyFeeId);
        if (monthlyFee.isEmpty()) {
            return false;
        }

        monthlyFeeRepo.deleteMonthlyFee(monthlyFee.get().getId());
        return true;
    }

    @Override
    public MonthlyFee updateMonthlyFee(MonthlyFee monthlyFee) {

        Optional<MonthlyFee> oldMonthlyFee = this.monthlyFeeRepo.findById(monthlyFee.getId());

        if (oldMonthlyFee.isEmpty()) {
            throw new StudentNotFoundException();
        }

        this.monthlyFeeRepo.create(monthlyFee);

        return monthlyFee;

    }

    @Override
    public List<MonthlyFee> findAllMonthlyFeesByReference(Months month, Integer year, UUID conductorId) {
        return monthlyFeeRepo.findAllMonthlyFeesByReference(month, year, conductorId);
    }


}
