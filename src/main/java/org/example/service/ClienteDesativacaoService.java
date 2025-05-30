package org.example.service;

import org.example.model.*;
import org.example.repository.CartaoRepository;
import org.example.repository.ClienteRepository;
import org.example.repository.ContaRepository;
import org.example.repository.FaturaRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDesativacaoService {
    private final ClienteRepository clienteRepository;
    private final ContaRepository contaRepository;
    private final CartaoRepository cartaoRepository;
    private final FaturaRepository faturaRepository;


    public ClienteDesativacaoService(
            ClienteRepository clienteRepository,
            ContaRepository contaRepository,
            CartaoRepository cartaoRepository,
            FaturaRepository faturaRepository
    ) {
        this.clienteRepository = clienteRepository;
        this.contaRepository = contaRepository;
        this.cartaoRepository = cartaoRepository;
        this.faturaRepository = faturaRepository;
    }


    public void desativarCliente(String cpf) throws Exception {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new Exception("O CPF informado não foi encontrado. Tente novamente");
        }

        Cliente cliente = clienteRepository.buscarPorCPF(cpf);
        if (cliente == null) {
            throw new Exception("Cliente não encontrado. Cadastre-se e tente novamente.\n");
        }

        // Validação de contas
        for (Conta conta : contaRepository.buscarValores(cpf)) {
            if (conta.getTitular().getCpf().equals(cpf) && conta.isAtivo()) {
                conta.setAtivo(false);
            }
        }

        // Validação de cartões
        List<Cartao> cartoes = cartaoRepository.buscarPorCPF(cpf);
        for (Cartao cartao : cartoes) {
            cartao.setBloqueado(true);
        }

        // Fechar faturas desses cartoes
        for (Cartao cartaoExistente : cartoes) {
            Cartao cartao = cartaoRepository.buscarPorNumero(cartaoExistente.getNumeroCartao());

            LocalDate hoje = LocalDate.now();
            LocalDate dataVencimento;
            LocalDate mesAtual = hoje.withDayOfMonth(10);
            LocalDate proximoMes = hoje.plusMonths(1).withDayOfMonth(10);

            if (hoje.getDayOfMonth() <= 10) {
                // A fatura vence esse dia
                dataVencimento = mesAtual;
            } else {
                // a fatura vence no proximo mês
                dataVencimento = proximoMes;
            }
            List<Transacao> transacaoMes = new ArrayList<>();
            List<Transacao> todaTransacao = cartao.getConta().getTransacao();

            for (Transacao transacao : todaTransacao) {
                LocalDate dia = transacao.getDataTransacao();

                if (dia.equals(mesAtual) || dia.isAfter(mesAtual) &&
                        dia.equals(proximoMes) || dia.isBefore(proximoMes)) {
                    transacaoMes.add(transacao);
                }
            }

            double valor = 0;
            for (Transacao transacao : transacaoMes) {
                valor += transacao.getValor();
            }

            String chave = String.valueOf(faturaRepository.buscarTamanho());

            Fatura fatura = new Fatura(chave, transacaoMes, dataVencimento, cartao, valor);
            faturaRepository.cadastrar(fatura);
        }

        if (!cliente.isAtivo()) {
            throw new Exception("Esse cliente está desativado. Suas permissões foram revogadas.\n");
        }

        cliente.setAtivo(false);

    }
}