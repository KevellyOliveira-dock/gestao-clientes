package org.example.controller;

public class FrontController implements Controller {
    private final Controller cartoesController;
    private final Controller clienteController;
    private final Controller contasController;
    private final Controller faturasController;

    public FrontController(Controller cartoesController,
                           Controller clientesController,
                           Controller contasController,
                           Controller faturasController) {
        this.cartoesController = cartoesController;
        this.clienteController = clientesController;
        this.contasController = contasController;
        this.faturasController = faturasController;
    }

    public String executar(String comando) {
        comando = comando.trim();

        if (comando.isEmpty()) {
            return """
                    ------------
                    | cartoes  |
                    | clientes |
                    | contas   |
                    | faturas  |
                    ------------""";
        }

        var inicioComando = comando.split(" ")[0];

        if ("cartoes".equals(inicioComando)) {
            return this.cartoesController.executar(comando);
        }

        if ("clientes".equals(inicioComando)) {
            return this.clienteController.executar(comando);
        }

        if ("contas".equals(inicioComando)) {
            return this.contasController.executar(comando);
        }

        if ("faturas".equals(inicioComando)) {
            return this.faturasController.executar(comando);
        }

        return "operação inválida";
    }
}
