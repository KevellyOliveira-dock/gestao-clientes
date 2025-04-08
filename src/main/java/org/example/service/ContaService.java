package org.example.service;

import org.example.model.Conta;

public interface ContaService {

    Conta cadastrarConta(String numeroConta, String cpf, double saldo) throws Exception;
}
