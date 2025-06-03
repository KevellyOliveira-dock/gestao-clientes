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

    private final CartaoService cartaoService;

    private final ClienteService clienteService;

    public ContaServiceImpl(ContaRepository contaRepository,
                            CartaoService cartaoService,
                            ClienteService clienteService) {
        this.contaRepository = contaRepository;
        this.cartaoService = cartaoService;
        this.clienteService = clienteService;
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
        if (cliente == null) {
            throw new Exception("CPF informado não encontrado. Cadastre-se e tente novamente.\n");
        }

        ClienteValidator.validarAtivo(cliente);

        List<Transacao> todaTransacao = new ArrayList<>();

        var conta = new Conta(numeroConta, cliente, saldo, todaTransacao, true);
        contaRepository.cadastrar(conta);

        return conta;
    }

    @Override
    public Conta buscarContaPorNumero(String numeroConta) throws Exception {
        Conta conta = contaRepository.buscarPorNumero(numeroConta);

        if (numeroConta == null || numeroConta.trim().isEmpty() || conta == null) {
            throw new Exception("A conta informada não foi encontrada. Cadastre-se e tente novamente.\n");
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

        if (contasEncontradas.isEmpty()) {
            throw new Exception("Conta não encontrada. Cadastre-se e tente novamente.\n");
        }

        return contasEncontradas;
    }

    @Override
    public List<Conta> buscarContasPorCPF(String cpf) {
        List<Conta> contasEncontradas = new ArrayList<>();

        for (Conta conta : contaRepository.buscarValores(cpf)) {
            if (conta.getTitular().getCpf().equals(cpf) && conta.isAtivo()) {
                contasEncontradas.add(conta);
            }
        }

        return contasEncontradas;
    }

    @Override
    public Conta desativarConta(String numeroConta) throws Exception {
        Conta conta = buscarContaPorNumero(numeroConta);

        List<Cartao> cartoes = cartaoService.buscarCartaoPorCPF(conta.getTitular());

        for (Cartao cartao : cartoes) {
            if (!cartao.isBloqueado()) {
                cartaoService.bloquearCartao(cartao.getNumeroCartao());
            }
        }

        contaRepository.cadastrar(conta);
        conta.setAtivo(false);

        return conta;
    }
}
