package org.example.service;

import org.example.model.Cliente;
import org.example.model.Conta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClienteServiceImpl implements ClienteService {
    Map<String, Cliente> listaClientes = new HashMap<>();

    @Override
    public Cliente cadastrarCliente(String nomeCompleto, String cpf, String endereco) throws Exception {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new Exception("CPF não pode ser nulo ou vazio");
        }

        if (listaClientes.containsKey(cpf)) {
            throw new Exception("CPF já cadastrado");
        }

        var clienteCadastrar = new Cliente(nomeCompleto, cpf, endereco);
        listaClientes.put(cpf, clienteCadastrar);

        return clienteCadastrar;
    }

    @Override
    public Cliente buscarClientePorCPF(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return null;
        }

        return listaClientes.get(cpf);
    }

    @Override
    public Cliente atualizarCliente(String nomeCompleto, String cpf, String endereco) throws Exception {
        Cliente clienteExistente = buscarClientePorCPF(cpf);

        if (clienteExistente == null) {
            throw new Exception("CPF não cadastrado");
        }

        if (nomeCompleto.isEmpty()) {
            nomeCompleto = clienteExistente.getNomeCompleto();
        }

        if (endereco.isEmpty()) {
            endereco = clienteExistente.getEndereco();
        }

        var clienteAtualizar = new Cliente(nomeCompleto, cpf, endereco);
        listaClientes.put(cpf, clienteAtualizar);

        return clienteAtualizar;
    }

    @Override
    public List<Cliente> pesquisarClientePorNome(String nome) {
        List<Cliente> clientesEncontrados = new ArrayList<>();
        String nomePesquisa = nome.toLowerCase();

        for (Cliente cliente : listaClientes.values()) {
            if (cliente.getNomeCompleto().toLowerCase().contains(nomePesquisa)) {
                clientesEncontrados.add(cliente);
            }
        }

        return clientesEncontrados;
    }

    @Override
    public Cliente pesquisarClientePorCPF(String cpf) {
        Cliente cliente = buscarClientePorCPF(cpf);

        if (listaClientes.containsKey(cpf)) {
            return cliente;
        }

        return null;
    }
}

