package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class Fatura {
    private String chave;
    private List<Transacao> transacao;
    private LocalDate dataVencimento;
    private Cartao cartao;
    private double valor;
    private boolean isPago;
}