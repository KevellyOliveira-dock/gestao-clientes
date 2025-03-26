package org.example.service;

import org.example.controller.ClientesController;
import org.example.controller.TesteInputStream;
import org.example.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteServiceImplTest {
    private ClienteServiceImpl clienteServiceImpl;

    @BeforeEach
    public void setup() {
        clienteServiceImpl = new ClienteServiceImpl();
    }

    @Test
    public void quandoCadastrarClienteVerifiqueSeOCpfJaFoiCadastradoEntaoCadastreComSucesso() {
        //cria um usuario para teste
        var resultado = clienteServiceImpl.cadastrarCliente("Kevelly", "5689778", "Rua teste");
        Cliente cliente = clienteServiceImpl.verificarCPF("5689778");

        var resultadoEsperado = "Cliente cadastrado com sucesso";

        assertEquals(resultadoEsperado, resultado);
        assertNotNull(cliente);
        assertEquals("Kevelly", cliente.getNomeCompleto());
        assertEquals("Rua teste", cliente.getEndereco());
    }

    @Test
    public void quandoVerificarCpfForNulaEntaoNaoCadastra() {
        var resultado = clienteServiceImpl.cadastrarCliente("Kevelly", null, "Rua teste");
        Cliente cliente = clienteServiceImpl.verificarCPF(null);

        assertEquals("CPF não pode ser nulo ou vazio", resultado);
        assertNull(cliente);
    }

    @Test
    public void quandoVerificarCpfForVaziaEntaoNaoCadastra() {
        var resultado = clienteServiceImpl.cadastrarCliente("Kevelly", "", "Rua teste");
        Cliente cliente = clienteServiceImpl.verificarCPF("");

        assertEquals("CPF não pode ser nulo ou vazio", resultado);
        assertNull(cliente);
    }

    @Test
    public void quandoVerificarCpfEntaoVerifiqueSeCadastrouChaveAntes() {
        clienteServiceImpl.cadastrarCliente("Kevelly", "5689778", "Rua teste");
        var resultado = clienteServiceImpl.cadastrarCliente("Kevelly", "5689778", "Rua teste");

        assertEquals("CPF já cadastrado", resultado);
    }

    @Test
    public void quandoVerificarCpfECpfNaoExistirEntaoRetorneNulo() {
        Cliente cliente = clienteServiceImpl.verificarCPF("");
        assertNull(cliente);
    }
}
