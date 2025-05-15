package org.example.repository;

import org.example.model.Cartao;

public interface CartaoRepository {
    Cartao cadastrar(Cartao cartao);

    Cartao buscarPorNumero(String numeroCartao);
}
