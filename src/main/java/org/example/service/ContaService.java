package org.example.service;

import org.example.model.Cliente;
import org.example.model.Conta;

public interface ContaService {

    Conta cadastrarConta(String cpf, double saldo) throws Exception;

    Conta buscarContaPorNumeroConta(String numeroConta);
}
