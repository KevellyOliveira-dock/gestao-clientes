package org.example.repository;

import org.example.model.Cartao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public List<Cartao> buscarPorCPF(String cpf) {
        List<Cartao> cartoes = new ArrayList<>();

        for (Cartao cartao : cartaoRepository.values()) {
            if (cartao.getConta().getTitular().getCpf().equals(cpf)) {
                cartoes.add(cartao);
            }
        }

        return cartoes;
    }
}
