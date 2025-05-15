package org.example.repository;

import org.example.model.Cartao;

import java.util.HashMap;
import java.util.Map;

public class InMemoryCartaoRepository implements CartaoRepository {
    private final Map<String, Cartao> cartaoRepository = new HashMap<>();

    @Override
    public Cartao cadastrar(Cartao cartao) {
        cartaoRepository.put(cartao.getNumeroCartao(), cartao);
        return cartao;
    }

    @Override
    public Cartao buscarPorNumero(String numeroCartao) {
        return cartaoRepository.get(numeroCartao);
    }

    @Override
    public Cartao editar(Cartao cartao) {
        cartaoRepository.put(cartao.getNumeroCartao(), cartao);

        return cartao;
    }
}
