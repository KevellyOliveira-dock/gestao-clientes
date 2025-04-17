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
        var cliente = new Cliente("Kevelly", "0123456789", "Rua teste, 123");

        var conta = new Conta("0", cliente, 123.43);
        when(contaService.cadastrarConta("0123456789", String.valueOf(123.43))).thenReturn(conta);

        var resultadoEsperado = "Conta cadastrada com sucesso!\n" +
                "Conta de número: 0\n" +
                "Saldo: 123.43.\n" +
                "Pertence ao titular: Kevelly.\n" +
                "CPF: 0123456789.\n" +
                "Endereço: Rua teste, 123.\n";

        this.inputStream.setInputs("0123456789\n123.43\n");
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
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("contas pesquisar cpf-titular");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasPesquisarNomeTitularEntaoExibaAsContasEncontradas() throws Exception {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("contas pesquisar nome-titular");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasPesquisarNumeroEntaoExibaDetalhesDaConta() throws Exception {
        var cliente = new Cliente("Kevelly", "12345678910", "Rua teste, 123");

        var conta = new Conta("0", cliente, 123.43);
        when(contaService.buscarContaPorNumero("0")).thenReturn(conta);

        var resultadoEsperado = "Conta encontrada: \n" +
                "Conta de número: 0\n" +
                "Saldo: 123.43.\n" +
                "Pertence ao titular: Kevelly.\n" +
                "CPF: 12345678910.\n" +
                "Endereço: Rua teste, 123.\n";
        var resultadoReal = controller.executar("contas pesquisar numero 0");

        assertEquals(resultadoEsperado, resultadoReal);
    }
}
