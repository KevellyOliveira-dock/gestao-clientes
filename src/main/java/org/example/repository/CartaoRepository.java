package org.example.repository;

import org.example.model.Cartao;

import java.util.List;

public interface CartaoRepository {
    Cartao cadastrar(Cartao cartao);

    Cartao buscarPorNumero(String numeroCartao);

    List<Cartao> buscarPorCPF(String cpf);
}
