package com.greenbeansapps.myschooltransportation.infra.repositories;

import com.greenbeansapps.myschooltransportation.domain.entities.MonthlyFee;
import com.greenbeansapps.myschooltransportation.domain.entities.Payment;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IMonthlyFeeRepositoryJPA extends JpaRepository<MonthlyFee, UUID> {

    @Query("SELECT mf FROM MonthlyFee mf WHERE mf.referenceMonth = :month AND mf.referenceYear = :year")
    public List<MonthlyFee> findAllMonthlyFeesByReference(Months month, Integer year);
}
