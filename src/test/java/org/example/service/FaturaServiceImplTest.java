package org.example.service;

import org.example.model.Cartao;
import org.example.model.Cliente;
import org.example.model.Conta;
import org.example.model.Fatura;
import org.example.model.Transacao;
import org.example.repository.FaturaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FaturaServiceImplTest {
    @InjectMocks
    private FaturaServiceImpl faturaServiceImpl;

    @Mock
    private CartaoService cartaoService;

    @Mock
    private FaturaRepository faturaRepository;

    private static final String NOME_CLIENTE = "Kevelly";
    private static final String CPF_CLIENTE = "12345678900";
    private static final String ENDERECO_CLIENTE = "Rua dos testes, 56";
    private static final Double SALDO_CONTA = 123.43;
    private static final String NUMERO_CONTA = "0";
    private static final boolean IS_ATIVO_CONTA = true;
    private static final String NUMERO_CARTAO = "1234";
    private static final String CVV_CARTAO = "123";
    private static final LocalDate DT_VENCIMENTO_CARTAO = LocalDate.of(2028, 12, 12);
    private static final List<Transacao> TRANSACAO_CONTA = new ArrayList<>();
    private static final boolean IS_BLOQUEADO_CARTAO = false;
    private static final String CHAVE_FATURA = "0";
    private static final List<Transacao> LISTA_DE_FATURA = new ArrayList<>();
    private static final LocalDate DT_VENCIMENTO_FATURA = LocalDate.of(2025, 6, 10);
    private static final double VALOR_FATURA = 200.0;
    private static final boolean IS_PAGO_FATURA = false;

    @Test
    public void quandoFaturaFecharVefiriqueSeCartaoExisteEntaoRetorneFatura() throws Exception {
        var cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);
        var conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);
        var cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, cliente, conta, IS_BLOQUEADO_CARTAO);

        when(cartaoService.buscarCartaoPorNumero(NUMERO_CARTAO)).thenReturn(cartao);

        Fatura resultadoReal = faturaServiceImpl.fecharFatura(NUMERO_CARTAO);

        assertEquals(NUMERO_CARTAO, resultadoReal.getCartao().getNumeroCartao());
        assertEquals(DT_VENCIMENTO_FATURA, resultadoReal.getDataVencimento());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void quandoFaturaFecharENumeroCartaoForVazioOuNuloEntaoRetorneMensagem(String numeroCartao) {
        Exception exception = assertThrows(Exception.class, () ->
                faturaServiceImpl.fecharFatura(numeroCartao)
        );
        assertEquals("O número do cartão não pode ser nulo ou vazio.\n", exception.getMessage());
    }

    @Test
    public void quandoFaturaFecharVerifiqueSeCartaoExisteSeNaoEntaoRetorneMensagem() throws Exception {
        when(cartaoService.buscarCartaoPorNumero(NUMERO_CARTAO)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () ->
                faturaServiceImpl.fecharFatura(NUMERO_CARTAO)
        );
        assertEquals("O cartão informado não foi encontrado.\n", exception.getMessage());
    }

    @Test
    public void quandoFaturaFecharEBuscarPorCartaoEEncontrarEntaoRetorneMensagem() throws Exception {
        var cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);
        var conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);
        var cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, cliente, conta, IS_BLOQUEADO_CARTAO);
        Fatura fatura = new Fatura(CHAVE_FATURA, LISTA_DE_FATURA, DT_VENCIMENTO_FATURA, cartao, VALOR_FATURA, IS_PAGO_FATURA);

        when(cartaoService.buscarCartaoPorNumero(NUMERO_CARTAO)).thenReturn(cartao);
        when(faturaRepository.buscarPorNumeroCartao(NUMERO_CARTAO)).thenReturn(List.of(fatura));

        Exception exception = assertThrows(Exception.class, () ->
                faturaServiceImpl.fecharFatura(NUMERO_CARTAO)
        );
        assertEquals("A fatura já está fechada.\n", exception.getMessage());
    }

    @Test
    public void quandoFaturaFecharAntesDoDiaDezEntaoFecheNoMesAtual() throws Exception {
        var cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);
        var conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);
        var cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, cliente, conta, IS_BLOQUEADO_CARTAO);

        when(cartaoService.buscarCartaoPorNumero(NUMERO_CARTAO)).thenReturn(cartao);

        Fatura resultadoReal = faturaServiceImpl.fecharFatura(NUMERO_CARTAO);

        LocalDate hoje = LocalDate.now();
        LocalDate vencimento;
        if (hoje.getDayOfMonth() < 10) {
            vencimento = hoje.withDayOfMonth(10);
        } else {
            vencimento = hoje.plusMonths(1).withDayOfMonth(10);
        }

        assertEquals(vencimento, resultadoReal.getDataVencimento());
    }
}