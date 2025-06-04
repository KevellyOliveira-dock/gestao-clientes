package org.example.service;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteDesativacaoServiceTest {
    @InjectMocks
    private ClienteDesativacaoService clienteDesativacaoService;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ContaService contaService;

    @Mock
    private CartaoService cartaoService;

    @Mock
    private FaturaService faturaService;

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
    private static final String CHAVE_FATURA = "0";
    private static final List<Transacao> LISTA_DE_FATURA = new ArrayList<>();
    private static final LocalDate DT_VENCIMENTO_FATURA = LocalDate.of(2025, 6, 10);
    private static final double VALOR_FATURA = 110.0;
    private static final boolean IS_PAGO_FATURA = false;

    @Test
    public void quandoComandoEhDesativarClienteEClienteExisteEntaoDesativeClienteComSucesso() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        Conta conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);
        Cartao cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, conta, IS_BLOQUEADO_CARTAO);
        Fatura fatura = new Fatura(CHAVE_FATURA, LISTA_DE_FATURA, DT_VENCIMENTO_FATURA, cartao, VALOR_FATURA, IS_PAGO_FATURA);

        when(clienteService.buscarClientePorCPF(CPF_CLIENTE)).thenReturn(cliente);
        when(contaService.buscarContasPorCPF(CPF_CLIENTE)).thenReturn(List.of(conta));
        when(cartaoService.buscarCartaoPorCPF(cliente)).thenReturn(List.of(cartao));
        when(faturaService.fecharFatura(cartao)).thenReturn(fatura);

        var resultado = clienteDesativacaoService.desativarCliente(CPF_CLIENTE);

        assertFalse(resultado.isAtivo());
    }

    @Test
    public void quandoComandoEhDesativarClienteEBuscarContasPorCpfEntaoDesativeTodasAsContas() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        Conta contaAtiva = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);
        Conta contaInativa = new Conta("1", cliente, SALDO_CONTA, new ArrayList<>(), false);

        when(clienteService.buscarClientePorCPF(CPF_CLIENTE)).thenReturn(cliente);
        when(contaService.buscarContasPorCPF(CPF_CLIENTE)).thenReturn(List.of(contaAtiva, contaInativa));

        clienteDesativacaoService.desativarCliente(CPF_CLIENTE);

        assertFalse(contaAtiva.isAtivo());
        assertFalse(contaInativa.isAtivo());;
    }

    @Test
    public void quandoComandoEhDesativarClienteEBuscarCartaoPorCpfEntaoBloqueieTodosOsCartoes() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        Conta conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);
        Cartao cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, conta, IS_BLOQUEADO_CARTAO);
        Fatura fatura = new Fatura(CHAVE_FATURA, LISTA_DE_FATURA, DT_VENCIMENTO_FATURA, cartao, VALOR_FATURA, IS_PAGO_FATURA);

        when(clienteService.buscarClientePorCPF(CPF_CLIENTE)).thenReturn(cliente);
        when(contaService.buscarContasPorCPF(CPF_CLIENTE)).thenReturn(List.of(conta));
        when(cartaoService.buscarCartaoPorCPF(cliente)).thenReturn(List.of(cartao));
        when(faturaService.fecharFatura(cartao)).thenReturn(fatura);

        clienteDesativacaoService.desativarCliente(CPF_CLIENTE);

        assertTrue(cartao.isBloqueado());
    }

    @Test
    public void quandoComandoEhDesativarClienteEBuscarCartaoPorCpfEJaEstaBloqueadoEntaoPermanessaBloqueado() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        Conta conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);
        Cartao cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, conta, true);

        when(clienteService.buscarClientePorCPF(CPF_CLIENTE)).thenReturn(cliente);
        when(contaService.buscarContasPorCPF(CPF_CLIENTE)).thenReturn(List.of(conta));
        when(cartaoService.buscarCartaoPorCPF(cliente)).thenReturn(List.of(cartao));

        clienteDesativacaoService.desativarCliente(CPF_CLIENTE);

        assertTrue(cartao.isBloqueado());
    }

    @Test
    public void quandoComandoEhDesativarClienteENaoTiverContaOuCartaoEntaoApenasBloqueiOCliente() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);

        when(clienteService.buscarClientePorCPF(CPF_CLIENTE)).thenReturn(cliente);
        when(contaService.buscarContasPorCPF(CPF_CLIENTE)).thenReturn(List.of());
        when(cartaoService.buscarCartaoPorCPF(cliente)).thenReturn(List.of());

        var resultaodo = clienteDesativacaoService.desativarCliente(CPF_CLIENTE);

        assertFalse(resultaodo.isAtivo());
    }

    @Test
    public void quandoComandoEhDesativarClienteEClienteNaoForEncontradoEntaoExibaMensagem() throws Exception {
        when(clienteService.buscarClientePorCPF(CPF_CLIENTE)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () ->
                clienteDesativacaoService.desativarCliente(CPF_CLIENTE)
        );
        assertEquals("Cliente não encontrado. Cadastre-se e tente novamente.\n", exception.getMessage());
    }
}