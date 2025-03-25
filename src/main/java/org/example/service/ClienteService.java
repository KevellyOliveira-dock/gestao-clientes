package org.example.service;

import org.example.model.Cliente;

public interface ClienteService {
    void cadastrarCliente(String nomeCompleto, String cpf, String endereco);
}
