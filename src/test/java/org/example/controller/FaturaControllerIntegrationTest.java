package org.example.controller;

import org.example.service.FaturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FaturaControllerIntegrationTest {
    @InjectMocks
    private FaturaController controller;

    private Scanner scanner;
    private TesteInputStream inputStream;

    @Mock
    private FaturaService faturaService;

    @BeforeEach
    public void setup() {
        inputStream = new TesteInputStream();
        scanner = new Scanner(inputStream);

        System.setIn(this.inputStream);

        controller = new FaturaController(faturaService, scanner);
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
