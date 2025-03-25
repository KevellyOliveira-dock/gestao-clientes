package org.example.service;

import org.example.model.Cliente;

import java.util.HashSet;
import java.util.Set;

public class ClienteServiceImpl implements ClienteService {
    Set<Cliente> listaClientes = new HashSet<>();

    @Override
    public void cadastrarCliente(String nomeCompleto, String cpf, String endereco) {
        //Cria um novo cliente do tipo cliente com os dados fornecidos
        Cliente novoCliente = new Cliente(nomeCompleto, cpf, endereco);
        listaClientes.add(novoCliente);
    }
}

