package org.example.service;

import org.example.model.Cartao;

import java.util.List;

public interface CartaoService {
    Cartao cadastrarCartao(String cpf, String numeroConta) throws Exception;

    Cartao buscarCartaoPorNumero(String numeroCartao) throws Exception;

    List<Cartao> buscarCartoesPorCPF(String cpf);

    Cartao bloquearCartao(String numeroCartao) throws Exception;
}