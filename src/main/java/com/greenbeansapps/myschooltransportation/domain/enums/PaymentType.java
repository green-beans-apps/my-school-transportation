package com.greenbeansapps.myschooltransportation.domain.enums;

import lombok.Getter;

@Getter
public enum PaymentType {
    DINHEIRO("Dinheiro"),
    PIX("PIX"),
    TRANSFERENCIA_BANCARIA("Transferência Bancária"),
    DEBITO_AUTOMATICO("Débito Automático"),
    CARTAO_CREDITO("Cartão de Crédito"),
    CARTAO_DEBITO("Cartão de Débito"),
    BOLETO("Boleto Bancário"),
    CHEQUE("Cheque");

    private final String descricao;

    PaymentType(String descricao) {
        this.descricao = descricao;
    }
}
