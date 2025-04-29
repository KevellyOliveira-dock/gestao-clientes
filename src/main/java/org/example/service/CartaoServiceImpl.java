package org.example.service;

import org.example.model.Cartao;
import org.example.model.Conta;

import java.util.HashMap;
import java.util.Map;

public class CartaoServiceImpl implements CartaoService {
    Map<String, Cartao> cartoes = new HashMap<>();

    ClienteService clienteService;
    ContaService contaService;

    public CartaoServiceImpl(ClienteService clienteService, ContaService contaService) {
        this.clienteService = clienteService;
        this.contaService = contaService;
    }

    @Override
    public Conta cadastrarConta(String cpf, String numeroConta) throws Exception {

        return null;
    }
}
