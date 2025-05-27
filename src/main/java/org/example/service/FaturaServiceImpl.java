package org.example.service;

import org.example.model.Cartao;
import org.example.model.Fatura;
import org.example.model.Transacao;
import org.example.repository.FaturaRepository;

import java.time.LocalDate;
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

        List<Transacao> transacaoMes = new ArrayList<>();
        List<Transacao> todaTransacao = cartao.getConta().getTransacao();

        for (Transacao transacao : todaTransacao) {
            LocalDate dia = transacao.getDataTransacao();

            if (dia.equals(mesAtual) || dia.isAfter(mesAtual) &&
                    dia.equals(proximoMes) || dia.isBefore(proximoMes)) {
                transacaoMes.add(transacao);
            }
        }

        double valor = 200;
        for (Transacao transacao : transacaoMes) {
            valor += transacao.getValor();
        }

        String chave = String.valueOf(faturaRepository.buscarTamanho());

        Fatura fatura = new Fatura(chave, transacaoMes, dataVencimento, cartao, valor, false);
        faturaRepository.cadastrar(fatura);

        return fatura;
    }

    @Override
    public Fatura pagarFatura(String numeroCartao) throws Exception {
        if (numeroCartao == null || numeroCartao.isEmpty()) {
            throw new Exception("O número do cartão não pode ser nulo ou vazio.\n");
        }

        Cartao cartao = cartaoService.buscarCartaoPorNumero(numeroCartao);
        if (cartao == null) {
            throw new Exception("O cartão informado não foi encontrado.\n");
        }

        List<Fatura> faturas = faturaRepository.buscarPorNumeroCartao(numeroCartao);

        Fatura fatura = null;
        LocalDate hoje = LocalDate.now();
        for (Fatura fat : faturas) {
            if (!fat.isPago() && !hoje.isAfter(fat.getDataVencimento())) {
                if (fatura == null || fat.getDataVencimento().isBefore(fatura.getDataVencimento())) {
                    fatura = fat;
                }
            }
        }

        if (fatura == null) {
            throw new Exception("Nenhuma fatura disponível para pagamento.\n");
        }

        double valor = fatura.getValor();
        double saldo = fatura.getCartao().getConta().getSaldo();

        if (saldo < valor) {
            throw new Exception("Saldo insuficiente para pagar a fatura.\n");
        }

        fatura.setPago(true);
        fatura.getCartao().getConta().setSaldo(saldo - valor);
        return fatura;
    }
}