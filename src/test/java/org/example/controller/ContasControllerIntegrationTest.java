package org.example.controller;

import org.example.service.ClienteServiceImpl;
import org.example.service.ContaService;
import org.example.service.ContaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.util.Scanner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ContasControllerIntegrationTest {

    private ContasController controller;
    private Scanner scanner;
    private TesteInputStream inputStream;
    private ContaService contaService;

    @Mock
    private ClienteServiceImpl clienteService; // Mock do ClienteService

    @BeforeEach
    public void setup() {
        contaService = new ContaServiceImpl(clienteService); // Passa o mock para a implementação
        inputStream = new TesteInputStream();
        scanner = new Scanner(inputStream);

        //Redireciona o System.in para p nosso inputStream
        System.setIn(this.inputStream);

        controller = new ContasController(contaService, scanner);
    }

    @Test
    public void quandoComandoEhContasEntaoExibaOpcoesDeContas() {
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
    public void quandoComandoEhContasCadastrarEntaoCadastreAsContas() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("contas cadastrar");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasDesativarEntaoDesativeAsContas() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("contas desativar");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasExtratoEntaoExibaOExtradoDasContas() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("contas extrato");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasPesquisarEntaoExibaOpcoesDeContasPesquisar() {
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
    public void quandoComandoEhContasPesquisarCpfTitularEntaoExibaAsContasEncontradas() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("contas pesquisar cpf-titular");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasPesquisarNomeTitularEntaoExibaAsContasEncontradas() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("contas pesquisar nome-titular");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasPesquisarNumeroEntaoExibaDetalhesDaConta() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("contas pesquisar numero");
        assertEquals(resultadoEsperado, resultadoReal);
    }
}
