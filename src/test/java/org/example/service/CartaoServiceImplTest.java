package org.example.service;

import org.example.model.Cartao;
import org.example.model.Cliente;
import org.example.model.Conta;
import org.example.model.Transacao;
import org.example.repository.CartaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartaoServiceImplTest {
    @InjectMocks
    private CartaoServiceImpl cartaoServiceImpl;

    @Mock
    private CartaoRepository cartaoRepository;

    private static final String NOME_CLIENTE = "Kevelly";
    private static final String CPF_CLIENTE = "12345678900";
    private static final String ENDERECO_CLIENTE = "Rua dos testes, 56";
    private static final boolean IS_ATIVO_CLIENTE = true;
    private static final Double SALDO_CONTA = 123.43;
    private static final String NUMERO_CONTA = "0";
    private static final boolean IS_ATIVO_CONTA = true;
    private static final String NUMERO_CARTAO = "1234";
    private static final String CVV_CARTAO = "123";
    private static final LocalDate DT_VENCIMENTO_CARTAO = LocalDate.of(2028, 12, 12);
    private static final List<Transacao> TRANSACAO_CONTA = new ArrayList<>();
    private static final boolean IS_BLOQUEADO_CARTAO = false;

    @Test
    public void quandoComandoEhCadastrarCartaoVerifiqueSeOCpfEAContaFoiCadastradoEntaoCadastreComSucesso()
            throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        Conta conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);

        Cartao resultadoReal = cartaoServiceImpl.cadastrarCartao(conta);

        assertEquals(cliente, resultadoReal.getConta().getTitular());
        assertEquals(conta, resultadoReal.getConta());
    }

    @Test
    public void quandoCadastrarCartaoEContaNaoEstiverAtivaEntaoExibaMensagem() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        Conta conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, false);

        Exception exception = assertThrows(Exception.class, () ->
                cartaoServiceImpl.cadastrarCartao(conta)
        );
        assertEquals("A conta informada não está ativa.\n", exception.getMessage());
    }

    @Test
    public void quandoCartaoPesquisarNumeroCartaoEntaoRetorneBuscaComSucesso() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        Conta conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);
        Cartao cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, conta, IS_BLOQUEADO_CARTAO);

        when(cartaoRepository.buscarPorNumero(NUMERO_CARTAO)).thenReturn(cartao);

        Cartao resultadoReal = cartaoServiceImpl.buscarCartaoPorNumero(NUMERO_CARTAO);

        assertEquals(CPF_CLIENTE, resultadoReal.getConta().getTitular().getCpf());
        assertEquals(NUMERO_CONTA, resultadoReal.getConta().getNumeroConta());
        assertEquals(NUMERO_CARTAO, resultadoReal.getNumeroCartao());
    }

    @Test
    public void quandoCartaoPesquisarNumeroCartaoEContaEstiverDesativadaEntaoRetorneBuscaComSucesso() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        Conta conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, false);
        Cartao cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, conta, true);

        when(cartaoRepository.buscarPorNumero(NUMERO_CARTAO)).thenReturn(cartao);

        Exception exception = assertThrows(Exception.class, () ->
                cartaoServiceImpl.buscarCartaoPorNumero(NUMERO_CARTAO)
        );
        assertEquals("A conta associada ao cartão está desativada.\n", exception.getMessage());
    }

    @Test
    public void quandoCartaoBloquearNumeroCartaoVerifiqueSeCartaoEstaBloqueadoEntaoExibaMensagem() {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        Conta conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);
        Cartao cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, conta, true);

        when(cartaoRepository.buscarPorNumero(NUMERO_CARTAO)).thenReturn(cartao);

        Exception exception = assertThrows(Exception.class, () ->
                cartaoServiceImpl.bloquearCartao(NUMERO_CARTAO)
        );
        assertEquals("Esse cartão já está bloqueado.\n", exception.getMessage());
    }

    @Test
    public void quandoCartoesBloquearNumeroCartaoEEncontrarEntaoBloqueieCartao()
            throws Exception {
        var cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        var conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);
        var cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, conta, IS_BLOQUEADO_CARTAO);

        when(cartaoRepository.buscarPorNumero(NUMERO_CARTAO)).thenReturn(cartao);

        Cartao resultado = cartaoServiceImpl.bloquearCartao(NUMERO_CARTAO);

        assertEquals(CPF_CLIENTE, resultado.getConta().getTitular().getCpf());
        assertTrue(resultado.isBloqueado());
    }

    @Test
    public void quandoCartaoDesbloquearNumeroCartaoVerifiqueSeCartaoEstaBloqueadoEntaoExibaMensagem() {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        Conta conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);
        Cartao cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, conta, IS_BLOQUEADO_CARTAO);

        when(cartaoRepository.buscarPorNumero(NUMERO_CARTAO)).thenReturn(cartao);

        Exception exception = assertThrows(Exception.class, () ->
                cartaoServiceImpl.desbloquearCartao(NUMERO_CARTAO)
        );
        assertEquals("Esse cartão já está desbloqueado.\n", exception.getMessage());
    }

    @Test
    public void quandoCartoesDesbloquearNumeroCartaoEEncontrarEntaoDesbloqueieCartao()
            throws Exception {
        var cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        var conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);
        var cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, conta, true);

        when(cartaoRepository.buscarPorNumero(NUMERO_CARTAO)).thenReturn(cartao);

        Cartao resultado = cartaoServiceImpl.desbloquearCartao(NUMERO_CARTAO);

        assertEquals(CPF_CLIENTE, resultado.getConta().getTitular().getCpf());
        assertFalse(resultado.isBloqueado());
    }

    @Test
    public void quandoBuscarCartaoPorCPFEContaEstiverDesativadaEntaoNaoDesbloquearCartao() {
        var cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        var conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, false);
        var cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, conta, true);

        when(cartaoRepository.buscarPorCPF(CPF_CLIENTE)).thenReturn(List.of(cartao));

        Exception exception = assertThrows(Exception.class, () ->
                cartaoServiceImpl.buscarCartaoPorCPF(cliente)
        );
        assertEquals("A conta associada ao cartão está desativada.\n", exception.getMessage());
    }

    @Test
    public void quandoBuscarCartaoPorCPFEEntaoRetorneListaDeCartoes() throws Exception {
        var cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        var conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);
        var cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, conta, IS_BLOQUEADO_CARTAO);

        when(cartaoRepository.buscarPorCPF(CPF_CLIENTE)).thenReturn(List.of(cartao));

        var resultado = cartaoServiceImpl.buscarCartaoPorCPF(cliente);

        assertEquals(List.of(cartao), resultado);
    }
}
