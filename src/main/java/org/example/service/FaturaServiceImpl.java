package org.example.service;

import org.example.model.Cartao;
import org.example.model.Fatura;
import org.example.model.Transacao;
import org.example.repository.FaturaRepository;
import org.example.validator.ClienteValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FaturaServiceImpl implements FaturaService {
    private final FaturaRepository faturaRepository;

    public FaturaServiceImpl(FaturaRepository faturaRepository) {
        this.faturaRepository = faturaRepository;
    }

    @Override
    public Fatura fecharFatura(Cartao cartao) throws Exception {
        ClienteValidator.validarAtivo(cartao.getConta().getTitular());

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

        for (Fatura fatura : faturaRepository.buscarFaturaPorNumeroCartao(cartao)) {
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

        double valor = 0;
        for (Transacao transacao : transacaoMes) {
            valor += transacao.getValor();
        }

        String chave = String.valueOf(faturaRepository.buscarTamanho());

        Fatura fatura = new Fatura(chave, transacaoMes, dataVencimento, cartao, valor, false);
        faturaRepository.cadastrar(fatura);

        return fatura;
    }

    @Override
    public Fatura pagarFatura(Cartao cartao) throws Exception {
        List<Fatura> faturas = faturaRepository.buscarFaturaPorNumeroCartao(cartao);

        ClienteValidator.validarAtivo(cartao.getConta().getTitular());

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

        Transacao pagamentoTransacao = new Transacao(LocalDate.now(), "Pagamento de fatura", valor);

        fatura.getCartao().getConta().getTransacao().add(pagamentoTransacao);
        return fatura;
    }
}