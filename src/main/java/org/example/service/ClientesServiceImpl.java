package org.example.service;

import org.example.model.Cliente;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClientesServiceImpl implements ClientesService {
    Map<String, Cliente> listaClientes = new HashMap<>();


    @Override
    public Cliente cadastrarCliente(String nomeCompleto, String cpf, String endereco) throws Exception {
        if ((nomeCompleto == null || nomeCompleto.trim().isEmpty()) ||
                (cpf == null || cpf.trim().isEmpty()) ||
                (endereco == null || endereco.trim().isEmpty())
        ) {
            throw new Exception("Preencha todos os campos");
        }

        if (listaClientes.containsKey(cpf)) {
            throw new Exception("CPF já cadastrado");
        }

        var clienteCadastrar = new Cliente(nomeCompleto, cpf, endereco);
        listaClientes.put(cpf, clienteCadastrar);

        return clienteCadastrar;
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
    public Cliente buscarClientePorCPF(String cpf) throws Exception {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new Exception("O CPF informado não foi encontrado. Tente novamente");
        }

        return listaClientes.get(cpf);
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
}