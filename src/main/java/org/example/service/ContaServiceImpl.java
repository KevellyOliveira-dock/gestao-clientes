package org.example.service;

import org.example.model.Cartao;
import org.example.model.Cliente;
import org.example.model.Conta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContaServiceImpl implements ContaService {
    private final Map<String, Conta> contaRepository;

    private final ClienteService clienteService;

    public ContaServiceImpl(ClienteService clienteService, Map<String, Conta> contaRepository) {
        this.clienteService = clienteService;
        this.contaRepository = contaRepository;
    }
    
    @Override
    public Conta cadastrarConta(String cpf, String saldoStr) throws Exception {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new Exception("O CPF não pode ser nulo ou vazio.\n");
        }

        if (saldoStr == null || saldoStr.isEmpty()) {
            throw new Exception("O saldo não pode ser nulo ou vazio.\n");
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

        var numeroConta = String.valueOf(contaRepository.size());

        Cliente cliente = clienteService.buscarClientePorCPF(cpf);
        if (cliente == null) {
            throw new Exception("CPF informado não encontrado. Cadastre-se e tente novamente.\n");
        }

        var conta = new Conta(numeroConta, cliente, saldo, true);
        contaRepository.put(numeroConta, conta);

        return conta;
    }

    @Override
    public Conta buscarContaPorNumero(String numeroConta) throws Exception {
        Conta conta = contaRepository.get(numeroConta);
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

        for (Conta conta : contaRepository.values()) {
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
    public List<Conta> buscarContasPorCPF(String cpf) throws Exception {
        List<Conta> contasEncontradas = new ArrayList<>();

        for (Conta conta : contaRepository.values()) {
            if (conta.getTitular().getCpf().equals(cpf) && conta.isAtivo()) {
                contasEncontradas.add(conta);
            }
        }

        if (contasEncontradas.isEmpty()) {
            throw new Exception("Conta não encontrada. Cadastre-se e tente novamente.\n");
        }

        return contasEncontradas;
    }

    @Override
    public Conta desativarConta(String numeroConta) throws Exception {
        Conta conta = buscarContaPorNumero(numeroConta);

        conta.setAtivo(false);
        List<Cartao> lista = cartaoService.buscarCartoesPorCPF(conta.getTitular().getCpf());

        // Para cada elemento da lista execute tal ação
        lista.forEach(cartao -> cartao.setBloqueado(true));

        return conta;
    }
}
