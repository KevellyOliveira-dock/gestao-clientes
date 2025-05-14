package org.example.service;

import org.example.model.Cartao;
import org.example.model.Fatura;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FaturaServiceImpl implements FaturaService {
    private final HashMap<String, Fatura> faturaRepository;

    private final CartaoService cartaoService;


    public FaturaServiceImpl(CartaoService cartaoService, HashMap<String, Fatura> faturaRepository) {
        this.cartaoService = cartaoService;
        this.faturaRepository = faturaRepository;
    }

    @Override
    public Fatura fecharFatura(String numeroCartao) throws Exception {
        if (numeroCartao == null || numeroCartao.isEmpty()) {
            throw new Exception("O CPF não pode ser nulo ou vazio.\n");
        }

        Cartao cartao = cartaoService.buscarCartaoPorNumero(numeroCartao);
        if (cartao == null) {
            throw new Exception("O cartão informado não foi encontrado. Cadastre-se e tente novamente.\n");
        }

        // Pega a data de agora, se for antes do dia 10 desse mês, fechar a fatura ainda nesse mes.
        // A partir do dia 11 deve ser no proximo mês.
        LocalDateTime vencimento;

        if (LocalDateTime.now().getDayOfMonth() <= 10) {
          // A fatura vence esse dia
            vencimento = LocalDateTime.now().withDayOfMonth(10);
        } else {
            // a fatura vence no proximo mês
            vencimento = LocalDateTime.now().plusMonths(1).withDayOfMonth(10);
        }

        String dtVencimento = vencimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // "Deve ser uma nova entidade?" - transação e transferencia da no msm
        List<String> transacao = new ArrayList<>();

        // "É a soma de todas as transações?"
        double valor = 200.0;

        Fatura fatura = new Fatura(transacao, dtVencimento, cartao, valor);
        faturaRepository.put(dtVencimento, fatura);

        return fatura;
    }

    @Override
    public Fatura buscarFatura(String dtVencimento) throws Exception {
        Fatura fatura = faturaRepository.get(dtVencimento);
        if (dtVencimento == null || dtVencimento.trim().isEmpty() || fatura == null) {
            throw new Exception("O cartão informado não foi encontrado. Cadastre-se e tente novamente.\n");
        }

        return fatura;
    }
}
