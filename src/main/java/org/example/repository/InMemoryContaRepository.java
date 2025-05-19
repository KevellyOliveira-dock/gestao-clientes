package org.example.repository;

import org.example.model.Conta;

import java.util.Collection;
import java.util.HashMap;

public class InMemoryContaRepository implements ContaRepository {
    private final HashMap<String, Conta> contaRepository = new HashMap<>();

    @Override
    public Conta cadastrar(Conta conta) {
        contaRepository.put(conta.getNumeroConta(), conta);
        return conta;
    }

    @Override
    public Collection<Conta> buscarValores(String cpf) {
        return contaRepository.values();
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
