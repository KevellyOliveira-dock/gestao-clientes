package org.example.service;

import org.example.model.Cartao;
import org.example.model.Cliente;
import org.example.model.Conta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartaoServiceImplTest {

    @InjectMocks
    private CartaoServiceImpl cartaoServiceImpl;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ContaService contaService;

    @Mock
    private HashMap<String, Cartao> mockHashMapCartao;

    private static final String NOME_CLIENTE = "Kevelly";
    private static final String CPF_CLIENTE = "12345678900";
    private static final String ENDERECO_CLIENTE = "Rua dos testes, 56";
    private static final Double SALDO_CONTA = 123.43;
    private static final String NUMERO_CONTA = "0";
    private static final boolean IS_ATIVO_CONTA = true;
    private static final String NUMERO_CARTAO = "1234";
    private static final String CVV_CARTAO = "123";
    private static final String DT_VENCIMENTO_CARTAO = "12/12/2028";
    private static final boolean IS_BLOQUEADO_CARTAO = false;

    @Test
    public void quandoComandoForCadastrarCartaoVerifiqueSeOCpfEAContaFoiCadastradoEntaoCadastreComSucesso()
            throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);
        Conta conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, IS_ATIVO_CONTA);

        when(clienteService.buscarClientePorCPF(CPF_CLIENTE)).thenReturn(cliente);
        when(contaService.buscarContaPorNumero(NUMERO_CONTA)).thenReturn(conta);

        Cartao resultadoReal = cartaoServiceImpl.cadastrarCartao(CPF_CLIENTE, NUMERO_CONTA);

        assertEquals(cliente, resultadoReal.getCliente());
        assertEquals(conta, resultadoReal.getConta());
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
    @NullAndEmptySource
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

    @Test
    public void quandoCartaoPesquisarNumeroCartaoEntaoRetorneBuscaComSucesso() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);
        Conta conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, IS_ATIVO_CONTA);
        Cartao cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, cliente, conta, IS_BLOQUEADO_CARTAO);

        when(mockHashMapCartao.get(NUMERO_CARTAO)).thenReturn(cartao);

        Cartao resultadoReal = cartaoServiceImpl.buscarCartaoPorNumero(NUMERO_CARTAO);

        assertEquals(CPF_CLIENTE, resultadoReal.getCliente().getCpf());
        assertEquals(NUMERO_CONTA, resultadoReal.getConta().getNumeroConta());
        assertEquals(NUMERO_CARTAO, resultadoReal.getNumeroCartao());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void quandoCartaoPesquisarNumeroCartaoForVazioOuNuloEntaoExibaMensagem(String numeroCartao) {
        Exception exception = assertThrows(Exception.class, () ->
                cartaoServiceImpl.buscarCartaoPorNumero(numeroCartao)
        );
        assertEquals("O cartão informado não foi encontrado. Cadastre-o e tente novamente.\n",
                exception.getMessage());
    }

    @Test
    public void quandoCartaoPesquisarNumeroCartaoVerifiqueSeCartaoEstaBloqueadoEntaoExibaMensagem() {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);
        Conta conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, IS_ATIVO_CONTA);
        Cartao cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, cliente, conta, true);

        when(mockHashMapCartao.get(NUMERO_CARTAO)).thenReturn(cartao);

        Exception exception = assertThrows(Exception.class, () ->
                cartaoServiceImpl.buscarCartaoPorNumero(NUMERO_CARTAO)
        );
        assertEquals("Esse cartão está bloqueado.\n",
                exception.getMessage());
    }

    @Test
    public void quandoCartoesBloquearNumeroCartaoEEncontrarEntaoBloqueieCartao()
            throws Exception {
        var cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);
        var conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, IS_ATIVO_CONTA);
        var cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, cliente, conta, IS_BLOQUEADO_CARTAO);

        when(mockHashMapCartao.get(NUMERO_CARTAO)).thenReturn(cartao);

        Cartao resultado = cartaoServiceImpl.bloquearCartao(NUMERO_CARTAO);

        assertEquals(CPF_CLIENTE, resultado.getCliente().getCpf());
        assertTrue(resultado.isBloqueado());
    }
}
