package org.example.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FrontControllerTest {
    private FrontController controller;

    @BeforeEach
    public void setup() {
        controller = new FrontController();
    }

    @Test
    public void quandoComandoEhCartoesEntaoExibaOpcoesDeCartoes() {
        var resultadoEsperado = """
            -------------------------------
            | Bloquear {número do cartao} |
            | Cadastrar                   |
            -------------------------------
        """;
        var resultadoReal = controller.executar("cartoes");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhFaturasEntaoExibaOpcoesDeFaturas() {
        var resultadoEsperado = """
            -----------------------------
            | fechar {número do cartão} |
            -----------------------------""";
        var resultadoReal = controller.executar("faturas");
        assertEquals(resultadoEsperado, resultadoReal);
    }
}
