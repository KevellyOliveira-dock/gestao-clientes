package org.example.service;

import org.example.model.Cliente;

public interface ClienteService {
    String cadastrarCliente(String nomeCompleto, String cpf, String endereco);

    Cliente verificarCPF(String cpf);
}
