package org.example.service;

import org.example.model.Conta;

import java.util.List;

public interface ContaService {
    Conta cadastrarConta(String cpf, String saldo) throws Exception;

    Conta buscarContaPorNumero(String numeroConta) throws Exception;

    List<Conta> buscarContasPorTitular(String nomeCompleto) throws Exception;

    List<Conta> buscarContasPorCPF(String cpf);

    Conta desativarConta(String numeroConta) throws Exception;
}
