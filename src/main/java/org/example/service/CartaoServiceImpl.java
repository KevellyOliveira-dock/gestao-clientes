package org.example.service;

import org.example.model.Cartao;
import org.example.model.Cliente;
import org.example.model.Conta;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;

import static java.lang.String.format;
import static java.lang.String.valueOf;

public class CartaoServiceImpl implements CartaoService {
    private final Map<String, Cartao> cartaoRepository;

    private final ClienteService clienteService;

    private final ContaService contaService;

    public CartaoServiceImpl(ClienteService clienteService, ContaService contaService, Map<String, Cartao> cartaoRepository) {
        this.clienteService = clienteService;
        this.contaService = contaService;
        this.cartaoRepository = cartaoRepository;
    }

    @Override
    public Cartao cadastrarCartao(String cpf, String numeroConta) throws Exception {
        if (cpf == null || cpf.isEmpty()) {
            throw new Exception("O CPF não pode ser nulo ou vazio.\n");
        }

        Cliente cliente = clienteService.buscarClientePorCPF(cpf);
        if (cliente == null) {
            throw new Exception("O CPF informado não foi encontrado. Cadastre-se e tente novamente.\n");
        }

        if (numeroConta == null || numeroConta.isEmpty()) {
            throw new Exception("O número da conta não pode ser nulo ou vazio.\n");
        }

        Conta conta = contaService.buscarContaPorNumero(numeroConta);
        if (conta == null) {
            throw new Exception("A conta informada não foi encontrada. Cadastre e tente novamente.\n");
        }

        if (!conta.isAtivo()) {
            throw new Exception("A conta informada não está ativa.\n");
        }

        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime data = agora.plusYears(3);
        String dtVencimento = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // LocalDateTime não tem milisegundos diretamente, ele guarda nanos então formato para 9 dígitos
        // format transforma o número inteiro em uma string de 9 digitos | substring pega os caracteres a partir de tal posição
        String cvv = format("%09d", agora.getNano()).substring(6);

        String numeroCartao;
        Random random = new Random();
        int MIN = 1000;
        int MAX = 9999;

        do {
            numeroCartao = valueOf(random.nextInt(MAX - MIN + 1) + MIN);
        } while (cartaoRepository.containsKey(numeroCartao));

        Cartao cartao = new Cartao(numeroCartao, cvv, dtVencimento, cliente, conta, false);
        cartaoRepository.put(numeroCartao, cartao);

        return cartao;
    }

    @Override
    public Cartao buscarCartaoPorNumero(String numeroCartao) throws Exception {
        Cartao cartao = cartaoRepository.get(numeroCartao);
        if (numeroCartao == null || numeroCartao.trim().isEmpty() || cartao == null) {
            throw new Exception("O cartão informado não foi encontrado. Cadastre-o e tente novamente.\n");
        }

        return cartao;
    }

    @Override
    public Cartao bloquearCartao(String numeroCartao) throws Exception {
        Cartao cartao = buscarCartaoPorNumero(numeroCartao);

        if (cartao.isBloqueado()) {
            throw new Exception("Esse cartão está bloqueado.\n");
        }

        cartao.setBloqueado(true);
        return cartao;
    }

    @Override
    public Cartao desbloquearCartao(String numeroCartao) throws Exception {
        Cartao cartao = buscarCartaoPorNumero(numeroCartao);

        if (!cartao.isBloqueado()) {
            throw new Exception("Esse cartão está desbloqueado.\n");
        }

        cartao.setBloqueado(false);
        return cartao;
    }
}
