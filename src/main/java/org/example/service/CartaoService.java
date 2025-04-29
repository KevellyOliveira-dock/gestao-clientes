package org.example.service;

import org.example.model.Conta;

public interface CartaoService {
    Conta cadastrarConta(String cpf, String numeroConta) throws Exception;
}
