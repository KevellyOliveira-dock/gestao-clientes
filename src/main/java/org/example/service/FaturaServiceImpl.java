package org.example.service;

import org.example.model.Cartao;
import org.example.model.Fatura;
import org.example.model.Transacao;
import org.example.repository.FaturaRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

        // Pega a data de agora, se for antes do dia 10 desse mês, fechar a fatura ainda nesse mes.
        // A partir do dia 11 deve ser no proximo mês.
        LocalDate hoje = LocalDate.now();
        LocalDate dataVencimento;
        LocalDate mesAtual = hoje.withDayOfMonth(10);
        LocalDate proximoMes = hoje.plusMonths(1).withDayOfMonth(10);

        if (hoje.getDayOfMonth() <= 10) {
            // A fatura vence esse dia
            dataVencimento = mesAtual;
        } else {
            // a fatura vence no proximo mês
            dataVencimento = proximoMes;
        }

        for (Fatura fatura : faturaRepository.buscarPorNumeroCartao(numeroCartao)) {
            if (fatura.getDataVencimento().equals(dataVencimento)) {
                throw new Exception("A fatura já está fechada.\n");
            }
        }

        // "Deve ser uma nova entidade?" - transação e transferencia da no msm
        List<String> transacao = new ArrayList<>();

        // "É a soma de todas as transações?"
        double valor = 200.0;

        Fatura fatura = new Fatura(chave, transacao, dtVencimento, cartao, valor);
        faturaRepository.cadastrar(fatura);

        return fatura;
    }
}