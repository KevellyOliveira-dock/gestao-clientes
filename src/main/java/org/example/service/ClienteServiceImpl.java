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

    @Override
    //Retorna Cliente para assim poder acessar Getters e Setters
    public Cliente verificarCPF(String cpf) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getCpf().equals(cpf)){
                return cliente;
            }
        }
        return null;
    }

    @Override
    public void atualizarCliente(String nomeAtualizado, String cpf, String enderecoAtualizado) {
        Cliente atualizarCliente = verificarCPF(cpf);

        if (atualizarCliente != null) {
            atualizarCliente.setNomeCompleto(nomeAtualizado);
            atualizarCliente.setEndereco(enderecoAtualizado);
        }
    }
}

