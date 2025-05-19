package org.example.repository;

import org.example.model.Cliente;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryClienteRepository implements ClienteRepository {
    private final Map<String, Cliente> clienteRepository = new HashMap<>();

    @Override
    public Cliente cadastrar(Cliente cliente) {
        return clienteRepository.put(cliente.getCpf(), cliente);
    }

    @Override
    public Cliente buscarPorCPF(String cpf) {
        return clienteRepository.get(cpf);
    }

    @Override
    public Collection<Cliente> buscarValores(String nome) {
        return clienteRepository.values();
    }
}
