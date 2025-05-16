package org.example.repository;

import org.example.model.Cliente;

import java.util.Collection;

public interface ClienteRepository {
    Cliente cadastrar(Cliente cliente);

    Cliente buscarPorCPF(String cpf);

    Collection<Cliente> buscarValores(String nome);
}
