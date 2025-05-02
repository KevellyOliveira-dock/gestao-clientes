package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class Cartao {
    private String numeroCartao;
    private String CVV;
    private String dtVencimento;
    private Cliente cliente;
    private Conta conta;

    public String toString() {
        return "O cliente " + cliente.getNomeCompleto() + ", de conta número " + conta.getNumeroConta()
                + ", acionou um novo cartão: "
                + "\nData de vencimento: " + dtVencimento + "."
                + "\nNúmero do cartão: " + numeroCartao + "."
                + "\nCVV: " + CVV + ".\n";
    }
}
