package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Conta {
    private String numeroConta;
    private Cliente titular;
    private double saldo;
}
