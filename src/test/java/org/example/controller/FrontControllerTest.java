package org.example.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FrontControllerTest {
    private FrontController controller;

    @BeforeEach
    public void setup() {
        var cartoesController = new CartoesController();
        var clientesController = new ClientesController();
        var contasController = new ContasController();
        var faturasController = new FaturasController();

        controller = new FrontController(
                cartoesController,
                clientesController,
                contasController,
                faturasController
        );
    }
}
