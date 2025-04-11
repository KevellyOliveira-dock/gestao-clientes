package org.example.service;

import org.example.model.Conta;

public interface ContasService {

    Conta cadastrarConta(String cpf, double saldo) throws Exception;

    Conta buscarContaPorNumero(String numeroConta) throws Exception;
}
