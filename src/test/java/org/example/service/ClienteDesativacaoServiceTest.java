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

    private static final String NOME_CLIENTE = "Kevelly";
    private static final String CPF_CLIENTE = "12345678900";
    private static final String ENDERECO_CLIENTE = "Rua dos testes, 56";
    private static final boolean IS_ATIVO_CLIENTE = true;
    private static final Double SALDO_CONTA = 123.43;
    private static final String NUMERO_CONTA = "0";
    private static final boolean IS_ATIVO_CONTA = true;
    private static final List<Transacao> TRANSACAO_CONTA = new ArrayList<>();

    @Test
    public void quandoComandoEhDesativarClienteEClienteExisteEntaoDesativeClienteComSucesso() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        Conta conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);

        when(clienteService.buscarClientePorCPF(CPF_CLIENTE)).thenReturn(cliente);
        when(contaService.buscarContasPorCPF(CPF_CLIENTE)).thenReturn(List.of(conta));

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
        assertFalse(contaInativa.isAtivo());
    }

    @Test
    public void quandoComandoEhDesativarClienteENaoTiverContaEntaoApenasBloqueiOCliente() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);

        when(clienteService.buscarClientePorCPF(CPF_CLIENTE)).thenReturn(cliente);
        when(contaService.buscarContasPorCPF(CPF_CLIENTE)).thenReturn(List.of());

        var resultado = clienteDesativacaoService.desativarCliente(CPF_CLIENTE);

        assertFalse(resultado.isAtivo());
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