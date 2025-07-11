package org.example.service;

import org.example.model.Cliente;
import org.example.repository.ClienteRepository;
import org.example.validator.ClienteValidator;

import java.util.ArrayList;
import java.util.List;


public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente cadastrarCliente(String nomeCompleto, String cpf, String endereco) throws Exception {
        if ((nomeCompleto == null || nomeCompleto.trim().isEmpty()) ||
                (cpf == null || cpf.trim().isEmpty()) ||
                (endereco == null || endereco.trim().isEmpty())
        ) {
            throw new Exception("Preencha todos os campos");
        }

        if (clienteRepository.buscarPorCPF(cpf) != null) {
            throw new Exception("CPF já cadastrado");
        }

        var clienteCadastrar = new Cliente(nomeCompleto, cpf, endereco, true);
        clienteRepository.cadastrar(clienteCadastrar);

        return clienteCadastrar;
    }

    @Override
    public Cliente atualizarCliente(String nomeCompleto, String cpf, String endereco) throws Exception {
        Cliente clienteExistente = buscarClientePorCPF(cpf);

        ClienteValidator.validarAtivo(clienteExistente);

        if (nomeCompleto.isEmpty()) {
            nomeCompleto = clienteExistente.getNomeCompleto();
        }

        if (endereco.isEmpty()) {
            endereco = clienteExistente.getEndereco();
        }

        var clienteAtualizar = new Cliente(nomeCompleto, cpf, endereco, true);
        clienteRepository.cadastrar(clienteAtualizar);

        return clienteAtualizar;
    }

    @Override
    public Cliente buscarClientePorCPF(String cpf) throws Exception {
        Cliente cliente = clienteRepository.buscarPorCPF(cpf);
        if (cliente == null) {
            throw new Exception("Cliente não encontrado. Cadastre-se e tente novamente.\n");
        }

        return cliente;
    }

    @Override
    public List<Cliente> pesquisarClientePorNome(String nome) throws Exception {
        List<Cliente> clientesEncontrados = new ArrayList<>();
        String nomePesquisa = nome.toLowerCase();

        for (Cliente cliente : clienteRepository.buscarValores(nome)) {
            if (cliente.getNomeCompleto().toLowerCase().contains(nomePesquisa)) {
                clientesEncontrados.add(cliente);
            }
        }

        if (clientesEncontrados.isEmpty()) {
            throw new Exception("Cliente não encontrado. Cadastre-se e tente novamente.\n");
        }

        return clientesEncontrados;
    }
}