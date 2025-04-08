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
    public Conta cadastrarConta(String numeroConta, String cpf, double saldo) throws Exception {
        if (numeroConta == null || numeroConta.trim().isEmpty()) {
            throw new Exception("O número da conta não pode ser nulo ou vazio");
        }

        if (contas.containsKey(numeroConta)) {
            throw new Exception("Conta já cadastrado");
        }

        Cliente cliente = clienteService.pesquisarClientePorCPF(cpf);
        if (cliente == null) {
            throw new Exception("CPF informado não encontrado. Cadastre-se e tente novamente");
        }

        var conta = new Conta(numeroConta, cliente, saldo);
        contas.put(numeroConta, conta);

        return conta;
    }
}
