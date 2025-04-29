package org.example.controller;

import org.example.service.CartaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CartaoControllerIntegrationTest {
    @InjectMocks
    private CartaoController controller;

    private Scanner scanner;
    private TesteInputStream inputStream;

    @Mock
    private CartaoService cartaoService;

    @BeforeEach
    public void setup() {
        inputStream = new TesteInputStream();
        scanner = new Scanner(inputStream);

        //Redireciona o System.in para p nosso inputStream
        System.setIn(this.inputStream);

        controller = new CartaoController(cartaoService, scanner);
    }

    @Test
    public void quandoComandoEhCartoesEntaoExibaOpcoesDeCartoes() throws Exception {
        var resultadoEsperado = """
                -------------------------------
                | Bloquear {número do cartao} |
                | Cadastrar                   |
                -------------------------------""";
        var resultadoReal = controller.executar("cartoes");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhCartoesBloquearEntaoBloqueieOsCartoes() throws Exception {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("cartoes bloquear");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhCartoesCadastrarEntaoCadastreOsCartoes() throws Exception {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("cartoes cadastrar");
        assertEquals(resultadoEsperado, resultadoReal);
    }
}
