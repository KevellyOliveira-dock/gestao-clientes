package org.example.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartaoControllerTest {

    private CartaoController controller;

    @BeforeEach
    public void setup() {
        controller = new CartaoController();
    }

    @Test
    public void quandoComandoEhCartoesEntaoExibaOpcoesDeCartoes() {
        var resultadoEsperado = """
                -------------------------------
                | Bloquear {número do cartao} |
                | Cadastrar                   |
                -------------------------------""";
        var resultadoReal = controller.executar("cartoes");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhCartoesBloquearEntaoBloqueieOsCartoes() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("cartoes bloquear");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhCartoesCadastrarEntaoCadastreOsCartoes() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("cartoes cadastrar");
        assertEquals(resultadoEsperado, resultadoReal);
    }
}
