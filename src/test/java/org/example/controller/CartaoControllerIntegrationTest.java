package org.example.controller;

import org.example.model.Cartao;
import org.example.model.Cliente;
import org.example.model.Conta;
import org.example.service.CartaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartaoControllerIntegrationTest {
    @InjectMocks
    private CartaoController controller;

    private Scanner scanner;
    private TesteInputStream inputStream;

    @Mock
    private CartaoService cartaoService;

    private static final String NOME_CLIENTE = "Kevelly";
    private static final String CPF_CLIENTE = "12345678900";
    private static final String ENDERECO_CLIENTE = "Rua dos testes, 56";
    private static final Double SALDO_CONTA = 123.43;
    private static final String NUMERO_CONTA = "0";
    private static final boolean IS_ATIVO_CONTA = true;
    private static final String NUMERO_CARTAO = "1234";
    private static final String CVV_CARTAO = "123";
    private static final String DT_VENCIMENTO_CARTAO = "12/12/2028";
    private static final boolean IS_BLOQUEADO_CARTAO = true;

    @BeforeEach
    public void setup() {
        inputStream = new TesteInputStream();
        scanner = new Scanner(inputStream);

        //Redireciona o System.in para p nosso inputStream
        System.setIn(this.inputStream);

        controller = new CartaoController(cartaoService, scanner);
    }

    @Test
    public void quandoComandoEhCartoesEntaoExibaOpcoesDeCartoes() throws Exception {
        var resultadoEsperado = """
                ----------------------------------
                | Bloquear {número do cartao}    |
                | Desbloquear {número do cartao} |
                | Cadastrar                      |
                ----------------------------------""";
        var resultadoReal = controller.executar("cartoes");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhCartoesBloquearEntaoBloqueieOsCartoes() throws Exception {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("cartoes bloquear");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhCartoesDesbloquearEntaoBloqueieOsCartoes() throws Exception {
        var cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);
        var conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, IS_ATIVO_CONTA);
        Cartao cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, cliente, conta, false);

        when(cartaoService.buscarCartaoPorNumero(NUMERO_CARTAO)).thenReturn(cartao);

        var resultadoEsperado = "Seu cartão foi desbloqueado com sucesso!\n";
        this.inputStream.setInputs("S\n");
        var resultadoReal = controller.executar("cartoes desbloquear 1234");

        assertEquals(resultadoEsperado, resultadoReal);
        assertFalse(cartao.isBloqueado());
    }

    @Test
    public void quandoComandoEhCartoesDesbloquearEDesistirEntaoExibaMensagem() throws Exception {
        var cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);
        var conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, IS_ATIVO_CONTA);
        Cartao cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, cliente, conta, IS_BLOQUEADO_CARTAO);

        when(cartaoService.buscarCartaoPorNumero(NUMERO_CARTAO)).thenReturn(cartao);

        var resultadoEsperado = "Operação cancelada\n";
        this.inputStream.setInputs("N\n");
        var resultadoReal = controller.executar("cartoes desbloquear 1234");

        assertEquals(resultadoEsperado, resultadoReal);
        assertTrue(cartao.isBloqueado());
    }

    @Test
    public void quandoComandoEhCartoesCadastrarEntaoCadastreOsCartoes() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);
        Conta conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, IS_ATIVO_CONTA);
        Cartao cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, cliente, conta, IS_BLOQUEADO_CARTAO);

        when(cartaoService.cadastrarCartao(CPF_CLIENTE, NUMERO_CONTA)).thenReturn(cartao);

        this.inputStream.setInputs("12345678900\n0\n");
        var resultadoEsperado = "Cartão criado com sucesso!\n" +
                "O cliente Kevelly, de conta número 0, acionou um novo cartão: " +
                "\nData de vencimento: 12/12/2028.\n" +
                "Número do cartão: 1234.\n" +
                "CVV: 123.\n";
        var resultadoReal = controller.executar("cartoes cadastrar");

        assertEquals(resultadoEsperado, resultadoReal);
    }
}
