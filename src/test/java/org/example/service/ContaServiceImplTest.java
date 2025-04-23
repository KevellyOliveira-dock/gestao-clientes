package org.example.service;

import org.example.model.Cliente;
import org.example.model.Conta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContaServiceImplTest {

    private ContaServiceImpl contaServiceImpl;

    @Mock
    private ContaService contaService;

    // cria uma instância de uma classe, porém Mockada
    @Mock
    private ClienteService clienteService;

    private static final String NOME_CLIENTE = "Kevelly";
    private static final String CPF_CLIENTE = "12345678900";
    private static final String ENDERECO_CLIENTE = "Rua dos testes, 56";
    private static final Double SALDO_CONTA = 123.43;
    private static final String NUMERO_CONTA = "0";

    @BeforeEach
    public void setup() {
        contaServiceImpl = new ContaServiceImpl(clienteService); // Passa o mock para a implementação
    }

    @Test
    public void quandoComandoForCadastrarContaVerifiqueSeOCpfFoiCadastradoEntaoCadastreComSucesso() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);

        // Após um mock ser criado, você pode configurar ações na chamada e o retorno.
        when(clienteService.buscarClientePorCPF(CPF_CLIENTE)).thenReturn(cliente);

        Conta resultadoReal = contaServiceImpl.cadastrarConta(CPF_CLIENTE, String.valueOf(SALDO_CONTA));

        assertEquals(CPF_CLIENTE, resultadoReal.getTitular().getCpf());
        assertEquals(SALDO_CONTA, resultadoReal.getSaldo());
    }

    @ParameterizedTest
    @NullAndEmptySource //quando a função for executada passa null e depois vazia
    public void quandoContasCadastrarECpfForVazioOuNuloEntaoExibaMensagem(String numeroConta) throws Exception {

        Exception exception = assertThrows(Exception.class, () ->
                contaServiceImpl.cadastrarConta(numeroConta, String.valueOf(SALDO_CONTA))
        );
        assertEquals("O CPF não pode ser nulo ou vazio.\n", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void quandoContasCadastrarESaldoForMenorQueZeroOuNaoNumeroEntaoExibaMensagem
            (String saldoStr) throws Exception {

        Exception exception = assertThrows(Exception.class, () ->
                contaServiceImpl.cadastrarConta(CPF_CLIENTE, saldoStr)
        );
        assertEquals("O saldo não pode ser nulo ou vazio.\n", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaa", "abc", "123abc", "@"})
    public void quandoContasCadastrarESaldoForValorInvalidoEntaoExibaMensagem(String saldoStr) throws Exception {

        Exception exception = assertThrows(Exception.class, () -> {
            contaServiceImpl.cadastrarConta(CPF_CLIENTE, saldoStr);

        });
        assertEquals("O saldo deve ser um número válido.\n", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, Double.NaN})
    public void quandoContasCadastrarESaldoForVazioOuNuloEntaoExibaMensagem(Double saldo) throws Exception {

        Exception exception = assertThrows(Exception.class, () -> {
            contaServiceImpl.cadastrarConta(CPF_CLIENTE, String.valueOf(saldo));

        });
        assertEquals("O saldo deve ser um número maior que zero.\n", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource //quando a função for executada passa null e depois vazia
    public void quandoContasPesquisarNumeroContaForVazioOuNuloEntaoExibaMensagem(String numeroConta) throws Exception {
        Exception exception = assertThrows(Exception.class, () ->
                contaServiceImpl.buscarContaPorNumero(numeroConta)
        );
        assertEquals("A conta informada não foi encontrada. Cadastre-se e tente novamente.\n",
                exception.getMessage());
    }

    @Test
    public void quandoContasPesquisarNomeTitularENaoEncontrarEntaoMensagemAdequada() throws Exception {
        Exception exception = assertThrows(Exception.class, () ->
                contaServiceImpl.buscarContasPorTitular(NOME_CLIENTE));
        assertEquals("Conta não encontrada. Cadastre-se e tente novamente.\n", exception.getMessage());
    }

    @Test
    public void quandoContasPesquisarNomeTitularEEncontrarEntaoAdicioneNaLista() throws Exception {
        var cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);
        var conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA);

        List<Conta> contas = new ArrayList<>();
        contas.add(conta);

        // Mocka o comportamento
        when(contaService.buscarContasPorTitular(NOME_CLIENTE)).thenReturn(contas);

        // Chama o metodo mockado
        List<Conta> resultado = contaService.buscarContasPorTitular(NOME_CLIENTE);

        assertEquals(1, resultado.size());
        assertEquals(NOME_CLIENTE, resultado.get(0).getTitular().getNomeCompleto());
    }

    @Test
    public void quandoContasPesquisarCPFTitularENaoEncontrarEntaoMensagemAdequada() {
        Exception exception = assertThrows(Exception.class, () ->
                contaServiceImpl.buscarContasPorCPF(CPF_CLIENTE));
        assertEquals("Conta não encontrada. Cadastre-se e tente novamente.\n", exception.getMessage());
    }

    @Test
    public void quandoContasPesquisarCPFTitularEEncontrarEntaoAdicioneNaLista() throws Exception {
        var cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);
        var conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA);

        List<Conta> contas = new ArrayList<>();
        contas.add(conta);

        when(contaService.buscarContasPorCPF(CPF_CLIENTE)).thenReturn(contas);

        List<Conta> resultado = contaService.buscarContasPorCPF(CPF_CLIENTE);

        assertEquals(1, resultado.size());
        assertEquals(CPF_CLIENTE, resultado.get(0).getTitular().getCpf());
    }
}
