package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Transacao {
    private LocalDate dataTransacao;
    private String descricao;
    private double valor;

    public String toString() {
        return "\n" + descricao + ", valor de " + valor + ", no dia " + dataTransacao + ".\n";
    }
}