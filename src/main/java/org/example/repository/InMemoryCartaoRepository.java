package org.example.repository;

import org.example.model.Cartao;

import java.util.Map;

public class InMemoryCartaoRepository implements CartaoRepository {
    private final Map<String, Cartao> cartaoRepository;

    public InMemoryCartaoRepository(Map<String, Cartao> cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

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
        var numero = cartaoRepository.get(cartao.getNumeroCartao());

        // Sugestão para simplificar o if
        numero.setBloqueado(!numero.isBloqueado());

        return numero;
    }
}
