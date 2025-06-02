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


    public void desativarCliente(String cpf) throws Exception {
        if (cpf == null || cpf.isEmpty()) {
            throw new Exception("O CPF não pode ser nulo ou vazio.\n");
        }

        Cliente clienteExistente = clienteService.buscarClientePorCPF(cpf);
        if (clienteExistente == null) {
            throw new Exception("CPF não cadastrado");
        }

        // Caso a lista seja vazia ele lança uma exceção, é necessario repensar
        List<Conta> contas = contaService.buscarContasPorCPF(cpf);
        for (Conta conta : contas) {
            conta.setAtivo(false);
        }

        List<Cartao> cartoes = cartaoService.buscarCartaoPorCPF(clienteExistente);
        for (Cartao cartao : cartoes) {
            cartao.setBloqueado(true);
        }

        for (int i = 0; i < cartoes.size(); i++) {
            faturaService.fecharFatura(cartoes.get(i));
        }

        Cliente cliente = clienteService.buscarClientePorCPF(cpf);

        if (cliente == null) {
            throw new Exception("Cliente não encontrado. Cadastre-se e tente novamente.\n");
        }

        if (!cliente.isAtivo()) {
            throw new Exception("Esse cliente está desativado. Suas permissões foram revogadas.\n");
        }

        clienteExistente.setAtivo(false);

    }
}