package com.greenbeansapps.myschooltransportation.domain.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "parameters_conductor")
public class ParametersConductor implements Serializable {

    private static final Float PERCENT_CONTRACT_TERMINATION_DEFAULT = 0.20f;

    @Id
    @Column(name = "conductor_id")
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "conductor_id")
    private Conductor conductor;

    @Column(nullable = false)
    private Float percentContractTermination;

    public ParametersConductor(Conductor conductor, Float percentContractTermination) {
        this.conductor = conductor;
        this.percentContractTermination = Objects.isNull(percentContractTermination)
                        ? PERCENT_CONTRACT_TERMINATION_DEFAULT : percentContractTermination;

    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public Float getPercentContractTermination() {
        return percentContractTermination;
    }

    public void setPercentContractTermination(Float percentContractTermination) {
        this.percentContractTermination = percentContractTermination;
    }
}
