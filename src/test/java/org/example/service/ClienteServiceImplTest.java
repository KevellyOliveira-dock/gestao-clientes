package org.example.service;

import org.example.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

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
        var resultado = clienteServiceImpl.cadastrarCliente(
                "Kevelly",
                "5689778",
                "Rua teste");
        Cliente cliente = clienteServiceImpl.buscarClientePorCPF("5689778");

        var resultadoEsperado = "Cliente cadastrado com sucesso";

        assertEquals(resultadoEsperado, resultado);
        assertNotNull(cliente);
        assertEquals("Kevelly", cliente.getNomeCompleto());
        assertEquals("5689778", cliente.getCpf());
        assertEquals("Rua teste", cliente.getEndereco());
    }

    @ParameterizedTest
    @NullAndEmptySource //quando a função for executada passa null e depois vazia
    public void quandoBuscarClientePorCpfForVaziaEntaoNaoCadastra(String cpf) {
        var resultado = clienteServiceImpl.cadastrarCliente("Kevelly", cpf, "Rua teste");
        Cliente cliente = clienteServiceImpl.buscarClientePorCPF(cpf);

        assertEquals("CPF não pode ser nulo ou vazio", resultado);
        assertNull(cliente);
    }

    @Test
    public void quandoBuscarClientePorCpfEntaoVerifiqueSeCadastrouChaveAntes() {
        clienteServiceImpl.cadastrarCliente("Kevelly", "5689778", "Rua teste");
        var resultado = clienteServiceImpl.cadastrarCliente(
                "Kevelly",
                "5689778",
                "Rua teste");

        assertEquals("CPF já cadastrado", resultado);
    }

    @Test
    public void quandoBuscarClientePorCpfECpfNaoExistirEntaoRetorneNulo() {
        Cliente cliente = clienteServiceImpl.buscarClientePorCPF("");
        assertNull(cliente);
    }
}
