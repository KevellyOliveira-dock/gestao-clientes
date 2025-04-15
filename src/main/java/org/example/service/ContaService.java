package org.example.service;

import org.example.model.Conta;

public interface ContaService {

    Conta cadastrarConta(String cpf, String saldo) throws Exception;

    Conta buscarContaPorNumero(String numeroConta) throws Exception;
}
