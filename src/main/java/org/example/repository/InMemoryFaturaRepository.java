package org.example.repository;

import org.example.model.Fatura;

import java.util.Collection;
import java.util.HashMap;

public class InMemoryFaturaRepository implements FaturaRepository {
    private final HashMap<String, Fatura> faturaRepository = new HashMap<>();

    @Override
    public Fatura cadastrar(Fatura fatura) {
        faturaRepository.put(fatura.getChave(), fatura);
        return fatura;
    }

    @Override
    public Collection<Fatura> buscarPorNumeroCartao(String numeroCartao) {
        return faturaRepository.values();
    }

    @Override
    public int buscarTamanho() {
        return faturaRepository.size();
    }
}
