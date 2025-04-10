package org.example.service;

import org.example.model.Cliente;
import org.example.model.Conta;

import java.util.HashMap;
import java.util.Map;

public class ContaServiceImpl implements ContaService {
    Map<String, Conta> contas = new HashMap<>();

    ClienteService clienteService;

    public ContaServiceImpl(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Override
    public Conta cadastrarConta(String cpf, double saldo) throws Exception {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new Exception("O CPF não pode ser nulo ou vazio");
        }

        var numeroConta = String.valueOf(contas.size());

        Cliente cliente = clienteService.buscarClientePorCPF(cpf);
        if (cliente == null) {
            throw new Exception("CPF informado não encontrado. Cadastre-se e tente novamente");
        }

        var conta = new Conta(numeroConta, cliente, saldo);
        contas.put(numeroConta, conta);

        return conta;
    }

    @Override
    public Conta buscarContaPorNumeroConta(String numeroConta) {
        if (numeroConta == null || numeroConta.trim().isEmpty()) {
            return null;
        }

        return contas.get(numeroConta);
    }
}
