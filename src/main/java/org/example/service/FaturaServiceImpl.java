package org.example.service;

import org.example.model.Cartao;
import org.example.model.Conta;
import org.example.model.Fatura;
import org.example.repository.FaturaRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FaturaServiceImpl implements FaturaService {
    private final FaturaRepository faturaRepository;

    private final CartaoService cartaoService;

    public FaturaServiceImpl(FaturaRepository faturaRepository, CartaoService cartaoService) {
        this.faturaRepository = faturaRepository;
        this.cartaoService = cartaoService;
    }

    @Override
    public Fatura fecharFatura(String numeroCartao) throws Exception {
        if (numeroCartao == null || numeroCartao.isEmpty()) {
            throw new Exception("O número do cartão não pode ser nulo ou vazio.\n");
        }

        Cartao cartao = cartaoService.buscarCartaoPorNumero(numeroCartao);
        if (cartao == null) {
            throw new Exception("O cartão informado não foi encontrado.\n");
        }

        String chave = String.valueOf(faturaRepository.buscarTamanho());

        // Pega a data de agora, se for antes do dia 10 desse mês, fechar a fatura ainda nesse mes.
        // A partir do dia 11 deve ser no proximo mês.
        // LocalDate hoje = LocalDate.of(2025, 5, 1);
        LocalDate hoje = LocalDate.now();
        LocalDate vencimento;

        if (hoje.getDayOfMonth() <= 10) {
            // A fatura vence esse dia
            vencimento = hoje.withDayOfMonth(10);
        } else {
            // a fatura vence no proximo mês
            vencimento = hoje.plusMonths(1).withDayOfMonth(10);
        }
        String dtVencimento = vencimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        for (Fatura fatura : faturaRepository.buscarPorNumeroCartao(numeroCartao)) {
            if (fatura.getCartao().getNumeroCartao().equals(numeroCartao) &&
                    fatura.getDtVencimento().equals(dtVencimento)) {
                throw new Exception("A fatura já está fechada.\n");
            }
        }

        // "Deve ser uma nova entidade?" - transação e transferencia da no msm
        List<String> transacao = new ArrayList<>();

        // "É a soma de todas as transações?"
        double valor = 200.0;

        Fatura fatura = new Fatura(chave, transacao, dtVencimento, cartao, valor, false);
        faturaRepository.cadastrar(fatura);

        return fatura;
    }

    @Override
    public Collection<Fatura> pagarFatura(String numeroCartao) throws Exception {
        if (numeroCartao == null || numeroCartao.isEmpty()) {
            throw new Exception("O número do cartão não pode ser nulo ou vazio.\n");
        }

        Cartao cartao = cartaoService.buscarCartaoPorNumero(numeroCartao);
        if (cartao == null) {
            throw new Exception("O cartão informado não foi encontrado.\n");
        }

        Collection<Fatura> faturas = faturaRepository.buscarPorNumeroCartao(numeroCartao);

        for (Fatura fatura : faturas) {
            double saldo = fatura.getCartao().getConta().getSaldo();
            double valor = fatura.getValor();
            double saldoAtual = saldo - valor;

            if (fatura.isPago()) {
                throw new Exception("A fatura já foi paga.\n");
            }

            fatura.getCartao().getConta().setSaldo(saldoAtual);
            fatura.setPago(true);

            if (saldo < valor) {
                throw new Exception("A conta não possui saldo suficiente para efetuar o pagamento dessa fatura.\n");
            }

            faturaRepository.cadastrar(fatura);
        }

        return faturas;
    }
}