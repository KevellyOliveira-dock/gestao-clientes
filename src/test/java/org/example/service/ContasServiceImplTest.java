package org.example.service;

import org.example.model.Cliente;
import org.example.model.Conta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
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

        Conta resultadoReal = contaServiceImpl.cadastrarConta("5689778", String.valueOf(123.34));

        assertEquals("5689778", resultadoReal.getTitular().getCpf());
        assertEquals(123.34, resultadoReal.getSaldo());
    }

    @ParameterizedTest
    @NullAndEmptySource //quando a função for executada passa null e depois vazia
    public void quandoContasCadastrarECpfForVazioOuNuloEntaoExibaMensagem(String numeroConta) throws Exception {

        Exception exception = assertThrows(Exception.class, () ->
                contaServiceImpl.cadastrarConta(numeroConta, String.valueOf(234.243))
        );
        assertEquals("O CPF não pode ser nulo ou vazio.\n", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void quandoContasCadastrarESaldoForMenorQueZeroOuNaoNumeroEntaoExibaMensagem
            (String saldoStr) throws Exception {

        Exception exception = assertThrows(Exception.class, () ->
                contaServiceImpl.cadastrarConta("12345678900", saldoStr)
        );
        assertEquals("O saldo não pode ser nulo ou vazio.\n", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaa", "abc", "123abc", "@"})
    public void quandoContasCadastrarESaldoForValorInvalidoEntaoExibaMensagem(String saldoStr) throws Exception {

        Exception exception = assertThrows(Exception.class, () -> {
            contaServiceImpl.cadastrarConta("12345678900", saldoStr);

        });
        assertEquals("O saldo deve ser um número válido.\n", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, Double.NaN})
    public void quandoContasCadastrarESaldoForVazioOuNuloEntaoExibaMensagem(Double saldo) throws Exception {

        Exception exception = assertThrows(Exception.class, () -> {
            contaServiceImpl.cadastrarConta("12345678900", String.valueOf(saldo));

        });
        assertEquals("O saldo deve ser um número maior que zero.\n", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource //quando a função for executada passa null e depois vazia
    public void quandoContasPesquisarNumeroContaForVazioOuNuloEntaoExibaMensagem(String numeroConta) throws Exception {

        Exception exception = assertThrows(Exception.class, () ->
                contaServiceImpl.buscarContaPorNumero(numeroConta)
        );
        assertEquals("A conta informada não foi encontrada. Cadastre-se e tente novamente.\n",
                exception.getMessage());
    }
}
