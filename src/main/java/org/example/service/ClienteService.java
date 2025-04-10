package org.example.service;

import org.example.model.Cliente;

import java.util.List;

public interface ClienteService {
    Cliente cadastrarCliente(String nomeCompleto, String cpf, String endereco) throws Exception;

    Cliente buscarClientePorCPF(String cpf);

    Cliente atualizarCliente(String nomeCompleto, String cpf, String endereco) throws Exception;

    List<Cliente> pesquisarClientePorNome(String nome);
}
