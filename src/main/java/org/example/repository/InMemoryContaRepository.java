package org.example.repository;

import org.example.model.Conta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class InMemoryContaRepository implements ContaRepository {
    private final HashMap<String, Conta> contaRepository = new HashMap<>();

    @Override
    public Conta cadastrar(Conta conta) {
        contaRepository.put(conta.getNumeroConta(), conta);
        return conta;
    }

    @Override
    public List<Conta> buscarValores(String cpf) {
        List<Conta> contas = new ArrayList<>();

        for (Conta conta : contaRepository.values()) {
            if (conta.getTitular().getCpf().equals(cpf) && conta.isAtivo()) {
                contas.add(conta);
            }
        }
        return contas;
    }

    @Override
    public Conta buscarPorNumero(String numeroConta) {
        return contaRepository.get(numeroConta);
    }

    @Override
    public int buscarTamanho() {
        return contaRepository.size();
    }
}
