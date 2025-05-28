package org.example.controller;

import org.example.model.Cartao;
import org.example.model.Cliente;
import org.example.model.Conta;
import org.example.model.Fatura;
import org.example.model.Transacao;
import org.example.service.FaturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FaturaControllerIntegrationTest {
    @InjectMocks
    private FaturaController controller;

    private Scanner scanner;

    private TesteInputStream inputStream;

    @Mock
    private FaturaService faturaService;

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

    @BeforeEach
    public void setup() {
        inputStream = new TesteInputStream();

        scanner = new Scanner(inputStream);

        System.setIn(this.inputStream);

        controller = new FaturaController(faturaService, scanner);
    }

    @Test
    public void quandoComandoEhFaturasEntaoExibaOpcoesDeFaturas() {
        var resultadoEsperado = """
                -----------------------------
                | fechar {número do cartão} |
                | pagar {número do cartão}  |
                -----------------------------""";
        var resultadoReal = controller.executar("faturas");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhFaturasFecharEntaoFecheAFatura() throws Exception {
        var cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);
        var conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);
        var cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, cliente, conta, IS_BLOQUEADO_CARTAO);
        Fatura fatura = new Fatura(CHAVE_FATURA, LISTA_DE_FATURA, DT_VENCIMENTO_FATURA, cartao, VALOR_FATURA, IS_PAGO_FATURA);

        when(faturaService.fecharFatura(NUMERO_CARTAO)).thenReturn(fatura);

        this.inputStream.setInputs("S\n");
        var resultadoEsperado = "Sua fatura foi fechada com sucesso! Pague até dia 10/06/2025.\n" +
                "Cartão de número 1234, valido até 12/12/2028, Titularidade de Kevelly, portador do CPF 12345678900.\n";
        var resultadoReal = controller.executar("faturas fechar 1234");

        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhFaturasFecharEDesistirEntaoExibaMensagem() {
        this.inputStream.setInputs("N\n");
        var resultadoEsperado = "Operação cancelada\n";
        var resultadoReal = controller.executar("faturas fechar 1234");

        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhFaturasPagarSaldoSerSuficienteEAFaturaAbertaEntaoPagueAFatura() throws Exception {
        var cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);
        var conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);
        var cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, cliente, conta, IS_BLOQUEADO_CARTAO);
        Fatura fatura = new Fatura(CHAVE_FATURA, LISTA_DE_FATURA, DT_VENCIMENTO_FATURA, cartao, VALOR_FATURA, IS_PAGO_FATURA);

        when(faturaService.pagarFatura(NUMERO_CARTAO)).thenReturn(fatura);

        this.inputStream.setInputs("S\n");
        var resultadoEsperado = "Sua fatura foi paga com sucesso! Pague até dia 10/06/2025.\n" +
                "Cartão de número 1234, valido até 12/12/2028, Titularidade de Kevelly, portador do CPF 12345678900.\n";
        var resultadoReal = controller.executar("faturas pagar 1234");

        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhFaturasPagarEDesistirEntaoExibaMensagem() {
        this.inputStream.setInputs("N\n");
        var resultadoEsperado = "Operação cancelada\n";
        var resultadoReal = controller.executar("faturas pagar 1234");

        assertEquals(resultadoEsperado, resultadoReal);
    }
}