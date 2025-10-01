package com.greenbeansapps.myschooltransportation.domain.enums;

import lombok.Getter;

@Getter
public enum ChargeType {
    MENSALIDADE("Mensalidade"),
    DIARIA("Diária"),
    SEMANAL("Semanal"),
    QUINZENAL("Quinzenal"),
    AVULSO("Avulso"),
    EXTRA("Serviço Extra"),
    MULTA("Multa por Atraso"),
    TAXA_MATRICULA("Taxa de Matrícula");

    private final String descricao;

    ChargeType(String descricao) {
        this.descricao = descricao;
    }
}
