package org.example.service;

import org.example.model.*;
import org.example.repository.CartaoRepository;
import org.example.repository.ClienteRepository;
import org.example.repository.ContaRepository;
import org.example.repository.FaturaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteDesativacaoServiceTest {

    private ClienteDesativacaoService clienteDesativacaoService;

    private ClienteService clienteService;

    private ContaService contaService;

    private CartaoService cartaoService;

    private FaturaService faturaService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private CartaoRepository cartaoRepository;

    @Mock
    private FaturaRepository faturaRepository;

    private static final String NOME_CLIENTE = "Kevelly";
    private static final String CPF_CLIENTE = "12345678900";
    private static final String ENDERECO_CLIENTE = "Rua dos testes, 56";
    private static final boolean IS_ATIVO_CLIENTE = true;
    private static final Double SALDO_CONTA = 123.43;
    private static final String NUMERO_CONTA = "0";
    private static final boolean IS_ATIVO_CONTA = true;
    private static final List<Transacao> TRANSACAO_CONTA = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clienteService = new ClienteServiceImpl(clienteRepository);
        cartaoService = new CartaoServiceImpl(cartaoRepository);
        faturaService = new FaturaServiceImpl(faturaRepository);
        contaService = new ContaServiceImpl(contaRepository, cartaoService, clienteService, faturaService);
        clienteDesativacaoService = new ClienteDesativacaoService(clienteService, contaService, clienteRepository);
    }

    @Test
    public void quandoComandoEhDesativarClienteEClienteExisteEntaoDesativeClienteESuasContasComSucesso() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        Conta conta1 = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);
        Conta conta2 = new Conta("1", cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);

        when(clienteRepository.buscarPorCPF(CPF_CLIENTE)).thenReturn(cliente);
        clienteService.buscarClientePorCPF(CPF_CLIENTE);

        when(contaRepository.buscarPorCPF(CPF_CLIENTE)).thenReturn(List.of(conta1, conta2));
        List<Conta> listaContas = contaService.buscarContasPorCPF(CPF_CLIENTE);
        for (Conta conta : listaContas) {
            when(contaRepository.buscarPorNumero(conta.getNumeroConta())).thenReturn(conta);
        }

        var resultado = clienteDesativacaoService.desativarCliente(CPF_CLIENTE);

        assertFalse(resultado.isAtivo());
        assertFalse(listaContas.get(0).isAtivo());
        assertFalse(listaContas.get(1).isAtivo());
    }

    @Test
    public void quandoComandoEhDesativarClienteENaoTiverContaEntaoApenasBloqueiOCliente() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);

        when(clienteRepository.buscarPorCPF(CPF_CLIENTE)).thenReturn(cliente);
        clienteService.buscarClientePorCPF(CPF_CLIENTE);

        when(contaRepository.buscarPorCPF(CPF_CLIENTE)).thenReturn(emptyList());
        List<Conta> listaContas = contaService.buscarContasPorCPF(CPF_CLIENTE);

        var resultado = clienteDesativacaoService.desativarCliente(CPF_CLIENTE);

        assertFalse(resultado.isAtivo());
        assertTrue(listaContas.isEmpty());
    }

    @Test
    public void quandoComandoEhDesativarClienteEClienteNaoForEncontradoEntaoExibaMensagem() {
        Exception exception = assertThrows(Exception.class, () ->
                clienteService.buscarClientePorCPF(CPF_CLIENTE)
        );
        assertEquals("Cliente não encontrado. Cadastre-se e tente novamente.\n", exception.getMessage());
    }

    @Test
    public void quandoComandoEhDesativarClienteEClienteJaEstaDesativadoEntaoExibaMensagem() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, false);

        when(clienteRepository.buscarPorCPF(CPF_CLIENTE)).thenReturn(cliente);
        clienteService.buscarClientePorCPF(CPF_CLIENTE);

        Exception exception = assertThrows(Exception.class, () ->
                clienteDesativacaoService.desativarCliente(CPF_CLIENTE)
        );
        assertEquals("Esse cliente já está desativado.\n", exception.getMessage());
    }

}