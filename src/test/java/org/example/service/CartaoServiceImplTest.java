package org.example.service;

import org.example.model.Cartao;
import org.example.model.Cliente;
import org.example.model.Conta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartaoServiceImplTest {

    private CartaoServiceImpl cartaoServiceImpl;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ContaService contaService;

    private static final String NOME_CLIENTE = "Kevelly";
    private static final String CPF_CLIENTE = "12345678900";
    private static final String ENDERECO_CLIENTE = "Rua dos testes, 56";
    private static final Double SALDO_CONTA = 123.43;
    private static final String NUMERO_CONTA = "0";

    @BeforeEach
    public void setup() {
        cartaoServiceImpl = new CartaoServiceImpl(clienteService, contaService);
    }

    @Test
    public void quandoCadastrarCartaoVerifiqueSeCPFEstaCadastradoSeNaoEntaoRetorneMensagem() throws Exception {
        when(clienteService.buscarClientePorCPF(CPF_CLIENTE)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () ->
                cartaoServiceImpl.cadastrarCartao(CPF_CLIENTE, NUMERO_CONTA)
        );
        assertEquals("O CPF informado não foi encontrado. Cadastre-se e tente novamente.\n", exception.getMessage());
    }

    @Test
    public void quandoCadastrarCartaoVerifiqueSeContaEstaCadastradaSeNaoEntaoRetorneMensagem() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);

        when(clienteService.buscarClientePorCPF(CPF_CLIENTE)).thenReturn(cliente);
        when(contaService.buscarContaPorNumero(NUMERO_CONTA)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () ->
                cartaoServiceImpl.cadastrarCartao(CPF_CLIENTE, NUMERO_CONTA)
        );
        assertEquals("A conta informada não foi encontrada. Cadastre e tente novamente.\n", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource //quando a função for executada passa null e depois vazia
    public void quandoCadastrarCartaoENumeroContaForVazioOuNuloEntaoRetorneMensagem(String numeroConta) throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);

        when(clienteService.buscarClientePorCPF(CPF_CLIENTE)).thenReturn(cliente);

        Exception exception = assertThrows(Exception.class, () ->
                cartaoServiceImpl.cadastrarCartao(CPF_CLIENTE, numeroConta)
        );
        assertEquals("O número da conta não pode ser nulo ou vazio.\n", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource //quando a função for executada passa null e depois vazia
    public void quandoCadastrarCartaoECpfVazioOuNuloEntaoRetorneMensagem(String cpf) {
        Exception exception = assertThrows(Exception.class, () ->
                cartaoServiceImpl.cadastrarCartao(cpf, NUMERO_CONTA)
        );
        assertEquals("O CPF não pode ser nulo ou vazio.\n", exception.getMessage());
    }

    @Test
    public void quandoCadastrarCartaoEContaNaaoEstiverAtivaEntaoExibaMensagem() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);
        Conta conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, false);

        when(clienteService.buscarClientePorCPF(CPF_CLIENTE)).thenReturn(cliente);
        when(contaService.buscarContaPorNumero(NUMERO_CONTA)).thenReturn(conta);

        Exception exception = assertThrows(Exception.class, () ->
                cartaoServiceImpl.cadastrarCartao(CPF_CLIENTE, NUMERO_CONTA)
        );
        assertEquals("A conta informada não está ativa.\n", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource //quando a função for executada passa null e depois vazia
    public void quandoCartaoPesquisarNumeroCartaoForVazioOuNuloEntaoExibaMensagem(String numeroCartao) {
        Exception exception = assertThrows(Exception.class, () ->
                cartaoServiceImpl.buscarCartaoPorNumero(numeroCartao)
        );
        assertEquals("O cartão informado não foi encontrado. Cadastre-o e tente novamente.\n",
                exception.getMessage());
    }
}
