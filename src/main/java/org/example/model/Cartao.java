package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class Cartao {
    private String numeroCartao;
    private  String CVV;
    private LocalDateTime dtVencimento;
    private Cliente cliente;
    private Conta conta;
}
