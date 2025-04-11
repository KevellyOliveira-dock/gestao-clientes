package org.example.service;

import org.example.model.Cliente;
import org.example.model.Conta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContasServiceImplTest {

    private ContasServiceImpl contaServiceImpl;

    // cria uma instância de uma classe, porém Mockada
    @Mock
    private ClientesService clientesService;

    @BeforeEach
    public void setup() {
        contaServiceImpl = new ContasServiceImpl(clientesService); // Passa o mock para a implementação
    }

    @Test
    public void quandoComandoForCadastrarContaVerifiqueSeOCpfFoiCadastradoEntaoCadastreComSucesso() throws Exception {
        Cliente cliente = new Cliente("Kevelly", "5689778", "Rua teste");

        // Após um mock ser criado, você pode configurar ações na chamada e o retorno.
        when(clientesService.buscarClientePorCPF("5689778")).thenReturn(cliente);

        Conta resultadoReal = contaServiceImpl.cadastrarConta("5689778", 123.34);

        assertEquals("5689778", resultadoReal.getTitular().getCpf());
        assertEquals(123.34, resultadoReal.getSaldo());
    }
}
