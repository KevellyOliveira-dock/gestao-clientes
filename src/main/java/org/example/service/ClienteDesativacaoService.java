package org.example.service;

import org.example.model.Cartao;
import org.example.model.Cliente;
import org.example.model.Conta;

import java.util.List;

public class ClienteDesativacaoService {
    private final ClienteService clienteService;
    private final ContaService contaService;
    private final CartaoService cartaoService;
    private final FaturaService faturaService;

    public ClienteDesativacaoService(ClienteService clienteService, ContaService contaService,
                                     CartaoService cartaoService, FaturaService faturaService) {
        this.clienteService = clienteService;
        this.contaService = contaService;
        this.cartaoService = cartaoService;
        this.faturaService = faturaService;
    }


    public Cliente desativarCliente(String cpf) throws Exception {
        Cliente cliente = clienteService.buscarClientePorCPF(cpf);

        // Caso a lista seja vazia ele lança uma exceção, é necessario repensar
        List<Conta> contas = contaService.buscarContasPorCPF(cpf);
        for (Conta conta : contas) {
            if (conta.isAtivo()) {
                conta.setAtivo(false);
            }
        }

        List<Cartao> cartoes = cartaoService.buscarCartaoPorCPF(cliente);
        for (Cartao cartao : cartoes) {
            if (!cartao.isBloqueado()) {
                cartao.setBloqueado(true);
                faturaService.fecharFatura(cartao);
            }
        }

        if (cliente == null) {
            throw new Exception("Cliente não encontrado. Cadastre-se e tente novamente.\n");
        }

        cliente.setAtivo(false);

        return cliente;
    }
}