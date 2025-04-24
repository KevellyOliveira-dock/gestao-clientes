package org.example.controller;

import org.example.model.Cliente;
import org.example.model.Conta;
import org.example.service.ClienteService;
import org.example.service.ContaService;
import org.example.service.ContaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContaControllerIntegrationTest {

    @InjectMocks
    private ContaController controller;

    private Scanner scanner;
    private TesteInputStream inputStream;

    @Mock
    private ContaService contaService;

    @Mock
    private ClienteService clienteService; // Mock do ClienteService

    private static final String NOME_CLIENTE = "Kevelly";
    private static final String CPF_CLIENTE = "12345678900";
    private static final String ENDERECO_CLIENTE = "Rua dos testes, 56";
    private static final Double SALDO_CONTA = 123.43;
    private static final String NUMERO_CONTA = "0";

    @BeforeEach
    public void setup() {
//        contaService = new ContaServiceImpl(clienteService); // Passa o mock para a implementação
        inputStream = new TesteInputStream();
        scanner = new Scanner(inputStream);

        //Redireciona o System.in para p nosso inputStream
        System.setIn(this.inputStream);

        controller = new ContaController(contaService, scanner);
    }

    @Test
    public void quandoComandoEhContasEntaoExibaOpcoesDeContas() throws Exception {
        var resultadoEsperado = """
                -------------------------------
                | cadastrar                   |
                | desativar {numero da conta} |
                | extrato {numero da conta}   |
                | pesquisar                   |
                -------------------------------""";
        var resultadoReal = controller.executar("contas");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasCadastrarEntaoCadastreAsContas() throws Exception {
        var cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);

        var conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA);
        when(contaService.cadastrarConta(CPF_CLIENTE, String.valueOf(SALDO_CONTA))).thenReturn(conta);

        var resultadoEsperado = "Conta cadastrada com sucesso!\n" +
                "Conta de número: 0\n" +
                "Saldo: 123.43.\n" +
                "Pertence ao titular: Kevelly.\n" +
                "CPF: 12345678900.\n" +
                "Endereço: Rua dos testes, 56.\n";

        this.inputStream.setInputs("12345678900\n123.43\n");
        var resultadoReal = controller.executar("contas cadastrar");

        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasDesativarEntaoDesativeAsContas() throws Exception {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("contas desativar");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasExtratoEntaoExibaOExtradoDasContas() throws Exception {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("contas extrato");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasPesquisarEntaoExibaOpcoesDeContasPesquisar() throws Exception {
        var resultadoEsperado = """
                ----------------------------------
                | cpf-titular {cpf do cliente}   |
                | nome-titular {nome do cliente} |
                | numero {número da conta}       |
                ----------------------------------""";
        var resultadoReal = controller.executar("contas pesquisar");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasPesquisarCpfTitularEntaoExibaAsContasEncontradas() throws Exception {
        var cliente1 = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);

        var clienteConta1 = new Conta(NUMERO_CONTA, cliente1, SALDO_CONTA);
        var clienteConta2 = new Conta("1", cliente1, 5000.0);

        List<Conta> listaContas = new ArrayList<>();
        listaContas.add(clienteConta1);
        listaContas.add(clienteConta2);

        when(contaService.buscarContasPorCPF(CPF_CLIENTE)).thenReturn(listaContas);

        var resultadoEsperado = "Contas encontradas: \n" +
                "Conta de número: 0\n" +
                "Saldo: 123.43.\n" +
                "Pertence ao titular: Kevelly.\n" +
                "CPF: 12345678900.\n" +
                "Endereço: Rua dos testes, 56.\n" +
                "\n" +
                "Conta de número: 1\n" +
                "Saldo: 5000.0.\n" +
                "Pertence ao titular: Kevelly.\n" +
                "CPF: 12345678900.\n" +
                "Endereço: Rua dos testes, 56.\n" +
                "\n";
        var resultadoReal = controller.executar("contas pesquisar cpf-titular 12345678900");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasPesquisarNomeTitularEntaoExibaAsContasEncontradas() throws Exception {
        var cliente1 = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);

        var clienteConta1 = new Conta(NUMERO_CONTA, cliente1, SALDO_CONTA);
        var clienteConta2 = new Conta("1", cliente1, 5000.0);

        List<Conta> listaContas = new ArrayList<>();
        listaContas.add(clienteConta1);
        listaContas.add(clienteConta2);

        when(contaService.buscarContasPorTitular(NOME_CLIENTE)).thenReturn(listaContas);

        var resultadoEsperado = "Contas encontradas: \n" +
                "Conta de número: 0\n" +
                "Saldo: 123.43.\n" +
                "Pertence ao titular: Kevelly.\n" +
                "CPF: 12345678900.\n" +
                "Endereço: Rua dos testes, 56.\n" +
                "\n" +
                "Conta de número: 1\n" +
                "Saldo: 5000.0.\n" +
                "Pertence ao titular: Kevelly.\n" +
                "CPF: 12345678900.\n" +
                "Endereço: Rua dos testes, 56.\n" +
                "\n";
        var resultadoReal = controller.executar("contas pesquisar nome-titular Kevelly");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasPesquisarNumeroEntaoExibaDetalhesDaConta() throws Exception {
        var cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);

        var conta = new Conta(NUMERO_CONTA, cliente, SALDO_CONTA);
        when(contaService.buscarContaPorNumero(NUMERO_CONTA)).thenReturn(conta);

        var resultadoEsperado = "Conta encontrada: \n" +
                "Conta de número: 0\n" +
                "Saldo: 123.43.\n" +
                "Pertence ao titular: Kevelly.\n" +
                "CPF: 12345678900.\n" +
                "Endereço: Rua dos testes, 56.\n";
        var resultadoReal = controller.executar("contas pesquisar numero 0");

        assertEquals(resultadoEsperado, resultadoReal);
    }
}
