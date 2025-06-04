package org.example.service;

import org.example.model.*;
import org.example.repository.ContaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContaServiceImplTest {
    @InjectMocks
    private ContaServiceImpl contaServiceImpl;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private ClienteService clienteService;

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
    private static final List<Transacao> TRANSACAO_CONTA = new ArrayList<>();
    private static final boolean IS_ATIVO_CONTA = true;
    private static final String NUMERO_CARTAO = "1234";
    private static final String CVV_CARTAO = "123";
    private static final LocalDate DT_VENCIMENTO_CARTAO = LocalDate.of(2028, 12, 12);
    private static final boolean IS_BLOQUEADO_CARTAO = false;
    private static final String CHAVE_FATURA = "0";
    private static final List<Transacao> LISTA_DE_FATURA = new ArrayList<>();
    private static final LocalDate DT_VENCIMENTO_FATURA = LocalDate.of(2025, 6, 10);
    private static final double VALOR_FATURA = 110.0;
    private static final boolean IS_PAGO_FATURA = false;

    @Test
    public void quandoComandoEhContaCadastrarVerifiqueSeOCpfFoiCadastradoEntaoCadastreComSucesso() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);

        when(clienteService.buscarClientePorCPF(CPF_CLIENTE)).thenReturn(cliente);

        Conta resultadoReal = contaServiceImpl.cadastrarConta(CPF_CLIENTE, String.valueOf(SALDO_CONTA));

        assertEquals(CPF_CLIENTE, resultadoReal.getTitular().getCpf());
        assertEquals(SALDO_CONTA, resultadoReal.getSaldo());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void quandoComandoEhContaCadastrarECpfForVazioOuNuloEntaoExibaMensagem(String numeroConta) {
        Exception exception = assertThrows(Exception.class, () ->
                contaServiceImpl.cadastrarConta(numeroConta, String.valueOf(SALDO_CONTA))
        );
        assertEquals("O CPF não pode ser nulo.\n", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void quandoComandoEhContaCadastrarESaldoForMenorQueZeroOuNaoNumeroEntaoExibaMensagem(String saldoStr) {
        Exception exception = assertThrows(Exception.class, () ->
                contaServiceImpl.cadastrarConta(CPF_CLIENTE, saldoStr)
        );
        assertEquals("O saldo não pode ser nulo.\n", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaa", "abc", "123abc", "@"})
    public void quandoContasCadastrarESaldoForValorInvalidoEntaoExibaMensagem(String saldoStr) {
        Exception exception = assertThrows(Exception.class, () ->
                contaServiceImpl.cadastrarConta(CPF_CLIENTE, saldoStr)
        );
        assertEquals("O saldo deve ser um número válido.\n", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, Double.NaN})
    public void quandoComandoEhContaCadastrarESaldoForVazioOuNuloEntaoExibaMensagem(Double saldo) {

        Exception exception = assertThrows(Exception.class, () ->
                contaServiceImpl.cadastrarConta(CPF_CLIENTE, String.valueOf(saldo))
        );
        assertEquals("O saldo deve ser um número maior que zero.\n", exception.getMessage());
    }

    @Test
    public void quandoContaPesquisarNumeroContaoVerifiqueSeContaEstaBloqueadoEntaoExibaMensagem() {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        Conta conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, false);

        when(contaRepository.buscarPorNumero(NUMERO_CONTA)).thenReturn(conta);

        Exception exception = assertThrows(Exception.class, () ->
                contaServiceImpl.buscarContaPorNumero(NUMERO_CONTA)
        );
        assertEquals("Essa conta está desativada.\n",
                exception.getMessage());
    }

    @Test
    public void quandoContasPesquisarNomeTitularENaoEncontrarEntaoMensagemAdequada() {
        Exception exception = assertThrows(Exception.class, () ->
                contaServiceImpl.buscarContasPorTitular(NOME_CLIENTE)
        );
        assertEquals("Nenhuma conta encontrada para o nome informado.\n", exception.getMessage());
    }

    @Test
    public void quandoContasPesquisarNomeTitularEEncontrarEntaoAdicioneNaLista() throws Exception {
        var cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        var conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);

        when(contaRepository.buscarValores(NOME_CLIENTE)).thenReturn(List.of(conta));

        // Chama o metodo mockado
        List<Conta> resultado = contaServiceImpl.buscarContasPorTitular(NOME_CLIENTE);

        assertEquals(1, resultado.size());
        assertEquals(NOME_CLIENTE, resultado.get(0).getTitular().getNomeCompleto());
    }

    @Test
    public void quandoContasPesquisarCPFTitularENaoEncontrarEntaoMensagemAdequada() {
        Exception exception = assertThrows(Exception.class, () ->
                contaServiceImpl.buscarContasPorCPF(CPF_CLIENTE)
        );
        assertEquals("Nenhuma conta encontrada para o CPF informado.\n", exception.getMessage());
    }

    @Test
    public void quandoContasPesquisarCPFTitularEEncontrarEntaoAdicioneNaLista() throws Exception {
        var cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        var conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);

        when(contaRepository.buscarValores(CPF_CLIENTE)).thenReturn(List.of(conta));

        List<Conta> resultado = contaServiceImpl.buscarContasPorCPF(CPF_CLIENTE);

        assertEquals(1, resultado.size());
        assertEquals(CPF_CLIENTE, resultado.get(0).getTitular().getCpf());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void quandoContasPesquisarCPFTitularENaoEncontrarEntaoRetorneListaVazia(String cpf) throws Exception {
        List<Conta> contas = new ArrayList<>();

        List<Conta> resultado = contaServiceImpl.buscarContasPorCPF(cpf);

        assertEquals(contas, resultado);
    }

    @Test
    public void quandoContasDesativarNumeroContaEEncontrarEntaoDesativeConta()
            throws Exception {
        var cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        var conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA, TRANSACAO_CONTA, IS_ATIVO_CONTA);
        var cartao = new Cartao(NUMERO_CARTAO, CVV_CARTAO, DT_VENCIMENTO_CARTAO, conta, IS_BLOQUEADO_CARTAO);
        Fatura fatura = new Fatura(CHAVE_FATURA, LISTA_DE_FATURA, DT_VENCIMENTO_FATURA, cartao, VALOR_FATURA, IS_PAGO_FATURA);

        when(contaRepository.buscarPorNumero(NUMERO_CONTA)).thenReturn(conta);
        when(cartaoService.buscarCartaoPorCPF(cliente)).thenReturn(List.of(cartao));
        when(faturaService.fecharFatura(cartao)).thenReturn(fatura);

        Conta resultado = contaServiceImpl.desativarConta(NUMERO_CONTA);

        assertEquals(CPF_CLIENTE, resultado.getTitular().getCpf());
        assertFalse(resultado.isAtivo());
    }
}
