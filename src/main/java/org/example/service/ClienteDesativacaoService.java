package org.example.service;

import org.example.model.Cliente;
import org.example.model.Conta;
import org.example.repository.ClienteRepository;

import java.util.List;

public class ClienteDesativacaoService {
    private final ClienteService clienteService;
    private final ContaService contaService;
    private final ClienteRepository clienteRepository;

    public ClienteDesativacaoService(ClienteService clienteService,
                                     ContaService contaService,
                                     ClienteRepository clienteRepository) {
        this.clienteService = clienteService;
        this.contaService = contaService;
        this.clienteRepository = clienteRepository;
    }

    public Cliente desativarCliente(String cpf) throws Exception {
        Cliente cliente = clienteService.buscarClientePorCPF(cpf);

        if (cliente == null) {
            throw new Exception("Cliente não encontrado. Cadastre-se e tente novamente.\n");
        }

        List<Conta> contas = contaService.buscarContasPorCPF(cpf);
        for (Conta conta : contas) {
            if (conta.isAtivo()) {
                contaService.desativarConta(conta.getNumeroConta());
            }
        }

        cliente.setAtivo(false);
        clienteRepository.cadastrar(cliente);

        return cliente;
    }
}