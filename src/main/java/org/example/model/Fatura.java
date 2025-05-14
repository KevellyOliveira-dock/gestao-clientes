package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Fatura {
    private List<String> transacao;
    private String dtVencimento;
    private Cartao cartao;
    private double valor;
}
