package org.example.service;

import org.example.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ContaServiceImplTest {

    // cria uma intancia e injeta as dependências necessárias que estão anotadas com @Mock
   // @InjectMocks
    private ContaServiceImpl contaServiceImpl;

    // cria uma instancia de uma classe, porém Mockada, não chama o metodo real
    @Mock
    private ClienteService clienteService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks

        contaServiceImpl = new ContaServiceImpl(clienteService); // Passa o mock para a implementação
    }

    @Test
    public void quandoComandoForCadastrarContaVerifiqueSeOCpfFoiCadastradoEntaoCadastreComSucesso() {
        Cliente cliente = new Cliente("Kevelly", "5689778", "Rua teste");

        // Após um mock ser criado, você pode configurar ações na chamada e o retorno.
        when(clienteService.pesquisarClientePorCPF("5689778")).thenReturn(cliente);

        var resultadoReal = contaServiceImpl.cadastrarConta("1234567890123", "5689778", 123.34);
        var resultadoEsperado = "Conta cadastrada com sucesso";

        assertEquals(resultadoEsperado, resultadoReal);
//        assertEquals("1234567890123", resultadoReal);
//        assertEquals("5689778", cliente.getCpf());
//        assertEquals("Rua teste", cliente.getEndereco());
    }
}
