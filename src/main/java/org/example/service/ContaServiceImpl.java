package org.example.service;

import org.example.model.Cartao;
import org.example.model.Cliente;
import org.example.model.Conta;
import org.example.model.Transacao;
import org.example.repository.ContaRepository;
import org.example.validator.ClienteValidator;

import java.util.ArrayList;
import java.util.List;

public class ContaServiceImpl implements ContaService {
    private final ContaRepository contaRepository;

    private final ClienteService clienteService;

    private final CartaoService cartaoService;

    private final FaturaService faturaService;

    public ContaServiceImpl(ContaRepository contaRepository,
                            CartaoService cartaoService,
                            ClienteService clienteService,
                            FaturaService faturaService) {
        this.contaRepository = contaRepository;
        this.cartaoService = cartaoService;
        this.clienteService = clienteService;
        this.faturaService = faturaService;
    }

    @Override
    public Conta cadastrarConta(String cpf, String saldoStr) throws Exception {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new Exception("O CPF não pode ser nulo.\n");
        }

        if (saldoStr == null || saldoStr.isEmpty()) {
            throw new Exception("O saldo não pode ser nulo.\n");
        }

        Double saldo;
        try {
            saldo = Double.valueOf(saldoStr);
        } catch (NumberFormatException e) {
            throw new Exception("O saldo deve ser um número válido.\n");
        }

        if (saldo < 0 || saldo.isNaN()) {
            throw new Exception("O saldo deve ser um número maior que zero.\n");
        }

        var numeroConta = String.valueOf(contaRepository.buscarTamanho());

        Cliente cliente = clienteService.buscarClientePorCPF(cpf);

        ClienteValidator.validarAtivo(cliente);

        ClienteValidator.validarAtivo(cliente);

        List<Transacao> todaTransacao = new ArrayList<>();

        var conta = new Conta(numeroConta, cliente, saldo, todaTransacao, true);
        contaRepository.cadastrar(conta);

        return conta;
    }

    @Override
    public Conta buscarContaPorNumero(String numeroConta) throws Exception {
        Conta conta = contaRepository.buscarPorNumero(numeroConta);

        if (conta == null) {
            throw new Exception("Conta não encontrada.\n");
        }

        if (!conta.isAtivo()) {
            throw new Exception("Essa conta está desativada.\n");
        }

        return conta;
    }

    @Override
    public List<Conta> buscarContasPorTitular(String nomeCompleto) throws Exception {
        List<Conta> contasEncontradas = new ArrayList<>();

        for (Conta conta : contaRepository.buscarValores(nomeCompleto)) {
            if (conta.getTitular().getNomeCompleto().equals(nomeCompleto) && conta.isAtivo()) {
                contasEncontradas.add(conta);
            }
        }

        return contasEncontradas;
    }

    @Override
    public List<Conta> buscarContasPorCPF(String cpf) throws Exception {
        List<Conta> contas = contaRepository.buscarValores(cpf);

        for (Conta conta : contas) {
            if (!conta.getTitular().getCpf().equals(cpf) || !conta.isAtivo()) {
                throw new Exception("Essa conta está desativada.\n");
            }
        }

        return contas;
    }

    @Override
    public Conta desativarConta(String numeroConta) throws Exception {
        Conta conta = buscarContaPorNumero(numeroConta);

        ClienteValidator.validarAtivo(conta.getTitular());

        List<Cartao> cartoes = cartaoService.buscarCartaoPorCPF(conta.getTitular());

        for (Cartao cartao : cartoes) {
            if (!cartao.isBloqueado()) {
                cartaoService.bloquearCartao(cartao.getNumeroCartao());
                faturaService.fecharFatura(cartao);
            }
        }

        conta.setAtivo(false);
        contaRepository.cadastrar(conta);

        return conta;
    }
}