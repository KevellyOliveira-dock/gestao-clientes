package org.example.controller;

import java.util.Scanner;

import org.example.service.ClienteService;
import org.example.service.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientesControllerIntegrationTest {
    private ClientesController controller;
    private Scanner scanner;
    private TesteInputStream inputStream;
    private ClienteService clienteService;

    @BeforeEach
    public void setup() {
        clienteService = new ClienteServiceImpl();
        inputStream = new TesteInputStream();
        scanner = new Scanner(inputStream);

        //Redireciona o System.in para p nosso inputStream
        System.setIn(this.inputStream);

        controller = new ClientesController(clienteService, scanner);
    }

    @Test
    public void quandoComandoEhClientesEntaoExibaOpcoesDeClientes() {
        var resultadoEsperado = """
                ------------------------------
                | atualizar {cpf do cliente} |
                | cadastrar                  |
                | desativar {cpf do cliente} |
                | pesquisar                  |
                ------------------------------""";
        var resultadoReal = controller.executar("clientes");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhClientesAtualizarEntaoAtualizeOsClientes() {
        this.inputStream.setInputs("Kevelly\n0123456789\nRua Fictícia 123\n");
        controller.executar("clientes cadastrar");

        // arrange
        this.inputStream.setInputs("Kevelly\nRua Teste 234\n");
        var resultadoEsperado = "Cliente atualizado com sucesso";

        // act
        var resultadoReal = controller.executar("clientes atualizar 0123456789");

        // assert
        assertEquals(resultadoEsperado, resultadoReal);
        var cliente = clienteService.buscarClientePorCPF("0123456789");
        assertEquals("Kevelly", cliente.getNomeCompleto());
        assertEquals("0123456789", cliente.getCpf());
        assertEquals("Rua Teste 234", cliente.getEndereco());
    }

    @Test
    public void quandoComandoEhClientesAtualizarECpfNaoForCadastradoEntaoNaoAtualizeOCLiente() {
        this.inputStream.setInputs("Kevelly\n0123456789\nRua Fictícia 123\n");
        controller.executar("clientes cadastrar");

        // arrange
        var resultadoEsperado = "CPF não cadastrado";

        // act
        var resultadoReal = controller.executar("clientes atualizar 0000000000");

        // assert
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhClientesAtualizarECpfNaoEhInformadoEntaoRetorneErro() {
        // arrange
        var resultadoEsperado = "Para atualizar é necessário informar o CPF. Ex: clientes atualizar 12345678901";

        // act
        var resultadoReal = controller.executar("clientes atualizar");

        // assert
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhClientesCadastrarEntaoCadastreOsClientes() {
        //o \n é um delimitador para o Scanner(espaço e tabulação também),
        //sempre q ele lê sabe acabou e passa para a próxima linha
        this.inputStream.setInputs("Kevelly\n0123456789\nRua Ficticia 123\n");

        var resultadoEsperado = "Cliente cadastrado com sucesso";
        var resultadoReal = controller.executar("clientes cadastrar");
        assertEquals(resultadoEsperado, resultadoReal);

        var cliente = clienteService.buscarClientePorCPF("0123456789");
        assertEquals("Kevelly", cliente.getNomeCompleto());
        assertEquals("0123456789", cliente.getCpf());
        assertEquals("Rua Ficticia 123", cliente.getEndereco());
    }

    @Test
    public void quandoComandoEhClientesDesativarEntaoDesativeOsClientes() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("clientes desativar");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhClientesPesquisarEntaoExibaOpcoesDeClientesPesquisar() {
        var resultadoEsperado = """
                --------------------------
                | cpf {cpf do cliente}   |
                | nome {nome do cliente} |
                -------------------------""";
        var resultadoReal = controller.executar("clientes pesquisar");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhClientesPesquisarCpfEntaoExibaDetalhesDosClientes() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("clientes pesquisar cpf");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhClientesPesquisarNomeEntaoExibaDetalhesDosClientes() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("clientes pesquisar nome");
        assertEquals(resultadoEsperado, resultadoReal);
    }
}
