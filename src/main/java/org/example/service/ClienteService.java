package org.example.service;

import org.example.model.Cliente;

public interface ClienteService {
    void cadastrarCliente(String nomeCompleto, String cpf, String endereco);

    Cliente verificarCPF(String cpf);

    void atualizarCliente(String nomeAtualizado, String cpf, String enderecoAtualizado);
}
