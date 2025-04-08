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
    public String cadastrarConta(String numeroConta, String cpf, double saldo) {
        if (numeroConta == null || numeroConta.trim().isEmpty()) {
            return "O número da conta não pode ser nulo ou vazio";
        }

        if (contas.containsKey(numeroConta)) {
            return "Conta já cadastrado";
        }

        Cliente cliente = clienteService.pesquisarClientePorCPF(cpf);
        if (cliente == null) {
            return "CPF informado não encontrado. Cadastre-se e tente novamente";
        }

        contas.put(numeroConta, new Conta(numeroConta, cliente, saldo));

        return "Conta cadastrada com sucesso";
    }
}
