package org.example.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FrontControllerTest {

    //declaração das variáveis e seus tipos, usadas para testes
    private FrontController controller;
    private Controller fakeCartoesController;
    private Controller fakeClientesController;
    private Controller fakeContasController;
    private Controller fakeFaturasController;

    @BeforeEach
    public void setup() {
        //Criando instâncias fake de cada controller, usadas como "substitutas" para os reais
        this.fakeCartoesController = new FakeController();
        this.fakeClientesController = new FakeController();
        this.fakeContasController = new FakeController();
        this.fakeFaturasController = new FakeController();

        //criando a instancia da FrontController, passando os controlers fakes
        controller = new FrontController(
                this.fakeCartoesController,
                this.fakeClientesController,
                this.fakeContasController,
                this.fakeFaturasController
        );
    }

    //Testes que verificam se a FrontController chama o respectivo Controller
    @Test
    public void QuandoComandoForCartoesEntaoChamarCartoesController() {
        //executa o comando
        controller.executar("cartoes");

        /**Verifica se o comando foi direcionado para a controller correta(no caso fakeCartoesController)
         faz um cast(conversão de tipo) de fakeCartoesController para o tipo FakeController e acessa
         atributo comando**/
        String comandoRecebido = ((FakeController) this.fakeCartoesController).comando;

        //Verifica se o comando recebido é o esperado
        assertEquals("cartoes", comandoRecebido);
    }

    @Test
    public void QuandoComandoForClientesEntaoChamarClientesController() {
        controller.executar("clientes");
        String comandoRecebido = ((FakeController) this.fakeClientesController).comando;
        assertEquals("clientes", comandoRecebido);
    }

    @Test
    public void QuandoComandoForContasEntaoChamarContasController() {
        controller.executar("contas");
        String comandoRecebido = ((FakeController) this.fakeContasController).comando;
        assertEquals("contas", comandoRecebido);
    }

    @Test
    public void QuandoComandoForFaturasEntaoChamarFaturasController() {
        controller.executar("faturas");
        String comandoRecebido = ((FakeController) this.fakeFaturasController).comando;
        assertEquals("faturas", comandoRecebido);
    }

    //Simula o comportamento dos Controllers reais para o teste
    class FakeController implements Controller {
        public String comando; //Armazena o comando recebido

        //implementação do metodo executar, armazena o comando recebido
        @Override
        public String executar(String comando) {
            this.comando = comando; //guarda o comando recebido
            return "";
        }
    }
}
