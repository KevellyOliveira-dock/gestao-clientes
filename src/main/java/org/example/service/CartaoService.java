package org.example.service;

import org.example.model.Cartao;

public interface CartaoService {
    Cartao cadastrarCartao(String cpf, String numeroConta) throws Exception;

    Cartao buscarCartaoPorNumero(String numeroCartao) throws Exception;
}
