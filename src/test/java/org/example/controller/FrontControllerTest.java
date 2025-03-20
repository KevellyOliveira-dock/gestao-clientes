package org.example.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FrontControllerTest {
    private FrontController controller;
    private Controller fakeCartoesController;
    private Controller fakeClientesController;
    private Controller fakeContasController;
    private Controller fakeFaturasController;

    @BeforeEach
    public void setup() {
        this.fakeCartoesController = new FakeController();
        this.fakeClientesController = new FakeController();
        this.fakeContasController = new FakeController();
        this.fakeFaturasController = new FakeController();

        controller = new FrontController(
                this.fakeCartoesController,
                this.fakeClientesController,
                this.fakeContasController,
                this.fakeFaturasController
        );
    }

    @Test
    public void QuandoComandoForCartoesEntaoChamarCartoesController() {
        controller.executar("cartoes aaaa");
        assertEquals("cartoes aaaa", (FakeController) this.fakeCartoesController.comando);
    }

    class FakeController implements Controller {
        public String comando;

        @Override
        public String executar(String comando) {
            this.comando = comando;
            return "";
        }
    }
}
