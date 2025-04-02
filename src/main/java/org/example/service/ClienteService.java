package org.example.service;

import org.example.model.Cliente;

import java.util.List;

public interface ClienteService {
    String cadastrarCliente(String nomeCompleto, String cpf, String endereco);

    Cliente buscarClientePorCPF(String cpf);

    String atualizarCliente(String nomeCompleto, String cpf, String endereco);

    List<Cliente> pesquisarClientePorNome(String nome);
}
