package com.greenbeansapps.myschooltransportation.infra.repositories;

import com.greenbeansapps.myschooltransportation.domain.entities.BillingSummary;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Month;
import java.util.List;
import java.util.UUID;

public interface IBillingSummaryRepositoryJPA extends JpaRepository<BillingSummary, UUID> {

    @Query("SELECT bs from BillingSummary bs JOIN bs.student s JOIN s.conductor c WHERE bs.referenceMonth = :month AND bs.referenceYear = :year AND c.id = :conductorId")
    List<BillingSummary> findAllBillingSummaryByReference(String month, Integer year, UUID conductorId);
}
