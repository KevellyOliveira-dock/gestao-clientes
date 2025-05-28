package org.example.service;

import org.example.model.Cartao;
import org.example.model.Cliente;
import org.example.model.Conta;
import java.util.List;

public class ClienteOperacoesService {
    private final ClienteService clienteService;
    private final ContaService contaService;
    private final CartaoService cartaoService;
    private final FaturaService faturaService;

    public ClienteOperacoesService(ClienteService clienteService, ContaService contaService,
                                   CartaoService cartaoService, FaturaService faturaService) {
        this.clienteService = clienteService;
        this.contaService = contaService;
        this.cartaoService = cartaoService;
        this.faturaService = faturaService;
    }


    public Cliente DesativarCliente(String cpf) throws Exception {
        if (cpf == null || cpf.isEmpty()) {
            throw new Exception("O CPF não pode ser nulo ou vazio.\n");
        }

        Cliente clienteExistente = clienteService.buscarClientePorCPF(cpf);
        if (clienteExistente == null) {
            throw new Exception("CPF não cadastrado");
        }

        List<Conta> contas = contaService.buscarContasPorCPF(cpf);
        for (Conta conta : contas) {
            conta.setAtivo(false);
        }

        List<Cartao> cartoes = cartaoService.buscarCartaoPorCPF(cpf);
        for (Cartao cartao : cartoes) {
            cartao.setBloqueado(true);
        }

        for (int i = 0; i < cartoes.size(); i++) {
           faturaService.fecharFatura(cartoes.get(i).getNumeroCartao());
        }

        clienteExistente.setAtivo(false);

        return clienteExistente;
    }
}