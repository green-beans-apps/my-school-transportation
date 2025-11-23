package com.greenbeansapps.myschooltransportation.infra.repositories.impl;

import com.greenbeansapps.myschooltransportation.domain.entities.MonthlyFee;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.MonthlyFeeRepository;
import com.greenbeansapps.myschooltransportation.infra.repositories.IMonthlyFeeRepositoryJPA;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MonthlyFreeRepositoryJPA implements MonthlyFeeRepository {

    private final IMonthlyFeeRepositoryJPA monthlyFeeRepo;

    public MonthlyFreeRepositoryJPA(IMonthlyFeeRepositoryJPA monthlyFeeRepo) {
        this.monthlyFeeRepo = monthlyFeeRepo;
    }

    @Override
    public MonthlyFee create(MonthlyFee monthlyFee) {
        this.monthlyFeeRepo.save(monthlyFee);
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

        monthlyFeeRepo.delete(monthlyFee.get());
        return true;
    }

    @Override
    public MonthlyFee updateMonthlyFee(MonthlyFee monthlyFee) {

        Optional<MonthlyFee> oldMonthlyFee = this.monthlyFeeRepo.findById(monthlyFee.getId());

        if (oldMonthlyFee.isEmpty()) {
            throw new StudentNotFoundException();
        }

        this.monthlyFeeRepo.save(monthlyFee);

        return monthlyFee;

    }

    @Override
    public List<MonthlyFee> findAllMonthlyFeesByReference(Months month, Integer year, UUID conductorId) {
        return monthlyFeeRepo.findAllMonthlyFeesByReference(month, year, conductorId);
    }
}
