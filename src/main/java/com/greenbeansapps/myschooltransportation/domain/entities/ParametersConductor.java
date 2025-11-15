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

    public ParametersConductor() {
    }

    public ParametersConductor(Conductor conductor, Float percentContractTermination) {
        setConductor(conductor);
        setPercentContractTermination(percentContractTermination);
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

        if (Objects.isNull(percentContractTermination)) {
            this.percentContractTermination = PERCENT_CONTRACT_TERMINATION_DEFAULT;
            return;
        }

        if (percentContractTermination < 0 || percentContractTermination > 1) {
            throw new IllegalArgumentException("percentContractTermination deve estar entre 0 e 1");
        }

        this.percentContractTermination = percentContractTermination;
    }
}
