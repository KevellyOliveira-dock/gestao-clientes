package org.example.service;

import org.example.model.Cliente;

public interface ContaService {

    String cadastrarConta(String numeroConta, String cpf, double saldo);
}
