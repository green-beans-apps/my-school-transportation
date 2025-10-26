package com.greenbeansapps.myschooltransportation.infra.repositories;

import com.greenbeansapps.myschooltransportation.domain.entities.BillingSummary;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IBillingSummaryRepositoryJPA extends JpaRepository<BillingSummary, UUID> {

}
