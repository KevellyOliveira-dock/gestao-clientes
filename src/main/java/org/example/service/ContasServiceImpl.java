package org.example.service;

import org.example.model.Cliente;
import org.example.model.Conta;

import java.util.HashMap;
import java.util.Map;

public class ContasServiceImpl implements ContasService {
    Map<String, Conta> contas = new HashMap<>();

    ClientesService clientesService;

    public ContasServiceImpl(ClientesService clientesService) {
        this.clientesService = clientesService;
    }

    @Override
    public Conta cadastrarConta(String cpf, double saldo) throws Exception {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new Exception("O CPF não pode ser nulo ou vazio");
        }

        var numeroConta = String.valueOf(contas.size());

        Cliente cliente = clientesService.buscarClientePorCPF(cpf);
        if (cliente == null) {
            throw new Exception("CPF informado não encontrado. Cadastre-se e tente novamente");
        }

        var conta = new Conta(numeroConta, cliente, saldo);
        contas.put(numeroConta, conta);

        return conta;
    }

    @Override
    public Conta buscarContaPorNumero(String numeroConta) throws Exception {
        if (numeroConta == null || numeroConta.trim().isEmpty()) {
            throw new Exception("A conta informada não foi encontrada. Tente novamente");
        }

        return contas.get(numeroConta);
    }
}
