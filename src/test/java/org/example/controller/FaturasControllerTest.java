package org.example.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FaturasControllerTest {
    private FaturasController controller;

    @BeforeEach
    public void setup() {
        controller = new FaturasController();
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

    @Test
    public void quandoComandoEhFaturasFecharEntaoFecheAFatura() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("faturas fechar");
        assertEquals(resultadoEsperado, resultadoReal);
    }
}
