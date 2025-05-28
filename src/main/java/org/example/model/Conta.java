package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Conta {
    private String numeroConta;
    private Cliente titular;
    private Double saldo;
    private List<Transacao> transacao;
    private boolean isAtivo;

    public String toString() {
        return "Conta de número: " + numeroConta +
                "\nSaldo: " + saldo + "." +
                "\nPertence ao titular: " + titular.getNomeCompleto() + "." +
                "\nCPF: " + getTitular().getCpf() + "." +
                "\nEndereço: " + titular.getEndereco() + ".\n";
    }
}
