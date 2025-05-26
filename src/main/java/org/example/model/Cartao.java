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
}
