package org.example.repository;

import org.example.model.Fatura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryFaturaRepository implements FaturaRepository {
    private final HashMap<String, Fatura> faturaRepository = new HashMap<>();

    @Override
    public Fatura cadastrar(Fatura fatura) {
        faturaRepository.put(fatura.getChave(), fatura);
        return fatura;
    }

    @Override
    public List<Fatura> buscarPorNumeroCartao(String numeroCartao) {
        List<Fatura> faturas = new ArrayList<>();

        for (Fatura fatura : faturaRepository.values()) {
            if (fatura.getCartao().getNumeroCartao().equals(numeroCartao)) {
                faturas.add(fatura);
            }
        }

        return faturas;
    }

    @Override
    public int buscarTamanho() {
        return faturaRepository.size();
    }
}
