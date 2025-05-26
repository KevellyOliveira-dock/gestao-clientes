package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class Cartao {
    private String numeroCartao;
    private String cvv;
    private LocalDate dataVencimento;
    private Cliente cliente;
    private Conta conta;
    private boolean isBloqueado;

    public String toString() {
        return "O cliente " + cliente.getNomeCompleto() + ", de conta número " + conta.getNumeroConta()
                + ", acionou um novo cartão: "
                + "\nData de vencimento: " + dataVencimento + "."
                + "\nNúmero do cartão: " + numeroCartao + "."
                + "\nCVV: " + cvv + ".\n";
    }
}
