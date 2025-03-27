package org.example.service;

import org.example.model.Cliente;
import java.util.HashMap;
import java.util.Map;


public class ClienteServiceImpl implements ClienteService {
    Map<String, Cliente> listaClientes = new HashMap<>();

    @Override
    public String cadastrarCliente(String nomeCompleto, String cpf, String endereco) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return "CPF não pode ser nulo ou vazio";
        }

        if (listaClientes.containsKey(cpf)) {
            return "CPF já cadastrado";
        }

        //Cria um novo cliente do tipo cliente com os dados fornecidos
        listaClientes.put(cpf, new Cliente(nomeCompleto, cpf, endereco));

        return "Cliente cadastrado com sucesso";
    }

    @Override
    public Cliente buscarClientePorCPF(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return null;
        }

        return listaClientes.get(cpf);
    }

    @Override
    public String atualizarCliente(String nomeCompleto, String cpf, String endereco) {

//        if (!listaClientes.containsKey(cpf)) {
//            return "CPF não encontrado";
//        }
        //Cria um novo cliente do tipo cliente com os dados fornecidos
        listaClientes.put(cpf, new Cliente(nomeCompleto, cpf, endereco));

        return "Dados atualizados com sucesso";
    }
}

