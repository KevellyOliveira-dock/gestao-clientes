package org.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.example.model.Cliente;
import org.example.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerIntegrationTest {
    @InjectMocks
    private ClienteController controller;

    private Scanner scanner;

    private TesteInputStream inputStream;

    @Mock
    private ClienteService clienteService;

    private static final String NOME_CLIENTE = "Kevelly";
    private static final String CPF_CLIENTE = "12345678900";
    private static final String ENDERECO_CLIENTE = "Rua dos testes, 56";

    @BeforeEach
    public void setup() {
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
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);

        when(clienteService.cadastrarCliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE)).thenReturn(cliente);
        when(clienteService.buscarClientePorCPF(CPF_CLIENTE)).thenReturn(cliente);

        this.inputStream.setInputs("Kevelly\n12345678900\nRua dos testes, 56\n");
        var resultadoEsperado = "Cliente cadastrado com sucesso\n";
        var resultadoReal = controller.executar("clientes cadastrar");

        assertEquals(resultadoEsperado, resultadoReal);

        var resultado = clienteService.buscarClientePorCPF(CPF_CLIENTE);

        assertEquals(NOME_CLIENTE, resultado.getNomeCompleto());
        assertEquals(CPF_CLIENTE, resultado.getCpf());
        assertEquals(ENDERECO_CLIENTE, resultado.getEndereco());
    }

    @Test
    public void quandoComandoEhClientesAtualizarEntaoAtualizeOsClientes() throws Exception {
        Cliente clienteAtualizado = new Cliente("Joice", CPF_CLIENTE, "Rua Teste 234");

        when(clienteService.atualizarCliente("Joice", CPF_CLIENTE, "Rua Teste 234")).
                thenReturn(clienteAtualizado);
        when(clienteService.buscarClientePorCPF(CPF_CLIENTE)).thenReturn(clienteAtualizado);

        this.inputStream.setInputs("Joice\nRua Teste 234\n");
        var resultadoEsperado = "Cliente atualizado com sucesso\n";
        var resultadoReal = controller.executar("clientes atualizar 12345678900");

        assertEquals(resultadoEsperado, resultadoReal);

        var resultado = clienteService.buscarClientePorCPF(CPF_CLIENTE);

        assertEquals("Joice", resultado.getNomeCompleto());
        assertEquals(CPF_CLIENTE, resultado.getCpf());
        assertEquals("Rua Teste 234", resultado.getEndereco());
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
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);
        Cliente cliente2 = new Cliente(NOME_CLIENTE, "98765432100", ENDERECO_CLIENTE);

        List<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente);
        clientes.add(cliente2);

        when(clienteService.pesquisarClientePorNome("Kevelly")).thenReturn(clientes);

        var resultadoEsperado = "Clientes encontrados: \n" +
                "Cliente Kevelly, de CPF 12345678900 e endereço Rua dos testes, 56.\n" +
                "Cliente Kevelly, de CPF 98765432100 e endereço Rua dos testes, 56.\n";
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
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);

        when(clienteService.buscarClientePorCPF(CPF_CLIENTE)).thenReturn(cliente);

        var resultadoEsperado = "Cliente Kevelly, de CPF 12345678900 e endereço Rua dos testes, 56.";
        var resultadoReal = controller.executar("clientes pesquisar cpf 12345678900");

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

