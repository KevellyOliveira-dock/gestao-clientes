package org.example.controller;

import java.util.Scanner;

import org.example.service.ClienteService;
import org.example.service.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClienteControllerIntegrationTest {
    private ClienteController controller;
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

        controller = new ClienteController(clienteService, scanner);
    }

    @Test
    public void quandoComandoEhClientesEntaoExibaOpcoesDeClientes() throws Exception {
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
    public void quandoComandoEhClientesCadastrarEntaoCadastreOsClientes() throws Exception {
        //o \n é um delimitador para o Scanner(espaço e tabulação também),
        //sempre q ele lê sabe acabou e passa para a próxima linha
        this.inputStream.setInputs("Kevelly\n0123456789\nRua Ficticia 123\n");

        var resultadoEsperado = "Cliente cadastrado com sucesso\n";
        var resultadoReal = controller.executar("clientes cadastrar");
        assertEquals(resultadoEsperado, resultadoReal);

        var cliente = clienteService.buscarClientePorCPF("0123456789");
        assertEquals("Kevelly", cliente.getNomeCompleto());
        assertEquals("0123456789", cliente.getCpf());
        assertEquals("Rua Ficticia 123", cliente.getEndereco());
    }

    @Test
    public void quandoComandoEhClientesAtualizarEntaoAtualizeOsClientes() throws Exception {
        this.inputStream.setInputs("Kevelly\n0123456789\nRua Fictícia 123\n");
        controller.executar("clientes cadastrar");

        this.inputStream.setInputs("Kevelly\nRua Teste 234\n");
        var resultadoEsperado = "Cliente atualizado com sucesso\n";

        var resultadoReal = controller.executar("clientes atualizar 0123456789");

        assertEquals(resultadoEsperado, resultadoReal);
        var cliente = clienteService.buscarClientePorCPF("0123456789");
        assertEquals("Kevelly", cliente.getNomeCompleto());
        assertEquals("0123456789", cliente.getCpf());
        assertEquals("Rua Teste 234", cliente.getEndereco());
    }

    @Test
    public void quandoComandoEhClientesAtualizarECpfNaoForCadastradoEntaoNaoAtualizeOCLiente() throws Exception {
        this.inputStream.setInputs("Kevelly\n0123456789\nRua Fictícia 123\n");
        controller.executar("clientes cadastrar");

        // arrange
        var resultadoEsperado = "CPF não cadastrado. Cadastre-se e tente novamente.\n";

        // act
        var resultadoReal = controller.executar("clientes atualizar 0000000000");

        // assert
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhClientesAtualizarECpfNaoEhInformadoEntaoRetorneErro() throws Exception {
        var resultadoEsperado = "Para atualizar é necessário informar o CPF. Ex: clientes atualizar 12345678901.\n";

        var resultadoReal = controller.executar("clientes atualizar");

        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhClientesDesativarEntaoDesativeOsClientes() throws Exception {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("clientes desativar");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhClientesPesquisarEntaoExibaOpcoesDeClientesPesquisar() throws Exception {
        var resultadoEsperado = """
                --------------------------
                | cpf {cpf do cliente}   |
                | nome {nome do cliente} |
                -------------------------""";
        var resultadoReal = controller.executar("clientes pesquisar");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhClientesPesquisarNomeEEncontrarClientesEntaoExibaListaDeClientes() throws Exception {
        this.inputStream.setInputs("Kevelly\n0987654321\nRua Ficticia 123\n");
        controller.executar("clientes cadastrar");

        this.inputStream.setInputs("Kevelly\n1234567890\nRua Ficticia 123\n");
        controller.executar("clientes cadastrar");

        clienteService.pesquisarClientePorNome("Kevelly");

        var resultadoEsperado = "Clientes encontrados: \n" +
                "Cliente Kevelly, de CPF 1234567890 e endereço Rua Ficticia 123.\n" +
                "Cliente Kevelly, de CPF 0987654321 e endereço Rua Ficticia 123.\n";

        var resultadoReal = controller.executar("clientes pesquisar nome Kevelly");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhClientesPesquisarNomeENaoEncontrarClientesEntaoRetorneErro() throws Exception {
        clienteService.pesquisarClientePorNome("Kevelly");

        var resultadoEsperado = "Cliente não encontrado. Cadastre-se e tente novamente.\n";

        var resultadoReal = controller.executar("clientes pesquisar nome Kevelly");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhClientesPesquisarCpfEEncontrarClienteEntaoRetorneSuasInformacoes() throws Exception {
        quandoComandoEhClientesCadastrarEntaoCadastreOsClientes();

        clienteService.buscarClientePorCPF("0123456789");

        var resultadoEsperado = "Cliente Kevelly, de CPF 0123456789 e endereço Rua Ficticia 123.";

        var resultadoReal = controller.executar("clientes pesquisar cpf 0123456789");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhClientesPesquisarCpfENaoEncontrarUmClienteEntaoRetorneErro() throws Exception {
        clienteService.buscarClientePorCPF("0123456789");

        var resultadoEsperado = "Cliente não encontrado. Cadastre-se e tente novamente.\n";

        var resultadoReal = controller.executar("clientes pesquisar cpf 0123456789");
        assertEquals(resultadoEsperado, resultadoReal);
    }

}

