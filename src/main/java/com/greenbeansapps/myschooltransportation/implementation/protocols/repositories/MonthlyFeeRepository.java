package com.greenbeansapps.myschooltransportation.implementation.protocols.repositories;

import com.greenbeansapps.myschooltransportation.domain.entities.MonthlyFee;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MonthlyFeeRepository {
    public MonthlyFee create(MonthlyFee monthlyFee);
    public Optional<MonthlyFee> findById(UUID monthlyFeeId);
    public Boolean deleteMonthlyFee(UUID monthlyFeeId);
    public MonthlyFee updateMonthlyFee(MonthlyFee monthlyFee);
    public List<MonthlyFee> findAllMonthlyFeesByReference(Months month, Integer year, UUID conductorId);
    public MonthlyFee findMonthlyFeeByReferenceAndStudent(Months month, Integer year, UUID studentId);
}
