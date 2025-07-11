package org.example.service;

import org.example.model.Cartao;
import org.example.model.Cliente;
import org.example.model.Conta;
import org.example.repository.CartaoRepository;
import org.example.validator.ClienteValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static java.lang.String.format;
import static java.lang.String.valueOf;

public class CartaoServiceImpl implements CartaoService {
    private final CartaoRepository cartaoRepository;

    public CartaoServiceImpl(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @Override
    public Cartao cadastrarCartao(Conta conta) throws Exception {
        ClienteValidator.validarAtivo(conta.getTitular());

        if (!conta.isAtivo()) {
            throw new Exception("A conta informada não está ativa.\n");
        }

        // LocalDateTime não tem milisegundos diretamente, ele guarda nanos então formato para 9 dígitos
        // format transforma o número inteiro em uma string de 9 digitos | substring pega os caracteres a partir de tal posição
        String cvv = format("%09d", LocalDateTime.now().getNano()).substring(6);

        String numeroCartao;
        Random random = new Random();
        int MIN = 1000;
        int MAX = 9999;

        do {
            numeroCartao = valueOf(random.nextInt(MAX - MIN + 1) + MIN);
        } while (cartaoRepository.buscarPorNumero(numeroCartao) != null);

        LocalDate dataVencimento = LocalDate.now().plusYears(3);

        Cartao cartao = new Cartao(numeroCartao, cvv, dataVencimento, conta, false);
        cartaoRepository.cadastrar(cartao);

        return cartao;
    }

    @Override
    public Cartao buscarCartaoPorNumero(String numeroCartao) throws Exception {
        Cartao cartao = cartaoRepository.buscarPorNumero(numeroCartao);
        if (!cartao.getConta().isAtivo()) {
            throw new Exception("A conta associada ao cartão está desativada.\n");
        }

        return cartao;
    }

    @Override
    public Cartao bloquearCartao(String numeroCartao) throws Exception {
        Cartao cartao = buscarCartaoPorNumero(numeroCartao);

        ClienteValidator.validarAtivo(cartao.getConta().getTitular());

        if (cartao.isBloqueado()) {
            throw new Exception("Esse cartão já está bloqueado.\n");
        }

        cartao.setBloqueado(true);
        cartaoRepository.cadastrar(cartao);
        return cartao;
    }

    @Override
    public Cartao desbloquearCartao(String numeroCartao) throws Exception {
        Cartao cartao = buscarCartaoPorNumero(numeroCartao);

        ClienteValidator.validarAtivo(cartao.getConta().getTitular());

        if (!cartao.isBloqueado()) {
            throw new Exception("Esse cartão já está desbloqueado.\n");
        }

        cartao.setBloqueado(false);
        cartaoRepository.cadastrar(cartao);
        return cartao;
    }

    @Override
    public List<Cartao> buscarCartaoPorCPF(Cliente titular) throws Exception {
        List<Cartao> cartoes = cartaoRepository.buscarPorCPF(titular.getCpf());

        for (Cartao cartao : cartoes) {
            if (!cartao.getConta().isAtivo()) {
                throw new Exception("A conta associada ao cartão está desativada.\n");
            }
        }

        return cartoes;
    }
}
