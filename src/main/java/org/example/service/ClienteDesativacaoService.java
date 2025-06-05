package org.example.service;

import org.example.model.Cartao;
import org.example.model.Cliente;
import org.example.model.Conta;

import java.util.ArrayList;
import java.util.List;

public class ClienteDesativacaoService {
    private final ClienteService clienteService;
    private final ContaService contaService;

    public ClienteDesativacaoService(ClienteService clienteService, ContaService contaService) {
        this.clienteService = clienteService;
        this.contaService = contaService;
    }

    public Cliente desativarCliente(String cpf) throws Exception {
        Cliente cliente = clienteService.buscarClientePorCPF(cpf);

        if (cliente == null) {
            throw new Exception("Cliente não encontrado. Cadastre-se e tente novamente.\n");
        }

        // Caso a lista seja vazia ele lança uma exceção, é necessario repensar
        List<Conta> contas;
        try {
            contas = contaService.buscarContasPorCPF(cpf);
            for (Conta conta : contas) {
                if (conta.isAtivo()) {
                    contaService.desativarConta(conta.getNumeroConta());
                }
            }
        } catch (Exception e) {
            contas = new ArrayList<>();
        }

        cliente.setAtivo(false);

        return cliente;
    }
}