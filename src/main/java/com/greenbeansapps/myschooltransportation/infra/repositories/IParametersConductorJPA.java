package com.greenbeansapps.myschooltransportation.infra.repositories;

import com.greenbeansapps.myschooltransportation.domain.entities.ParametersConductor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IParametersConductorJPA extends JpaRepository<ParametersConductor, UUID> {
}
