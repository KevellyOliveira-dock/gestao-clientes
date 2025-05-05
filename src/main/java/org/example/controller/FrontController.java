package org.example.controller;

public class FrontController implements Controller {
    private final Controller cartaoController;
    private final Controller clienteController;
    private final Controller contaController;
    private final Controller faturaController;

    public FrontController(Controller cartaoController,
                           Controller clienteController,
                           Controller contaController,
                           Controller faturaController) {
        this.cartaoController = cartaoController;
        this.clienteController = clienteController;
        this.contaController = contaController;
        this.faturaController = faturaController;
    }

    public String executar(String comando) throws Exception {
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
            return this.cartaoController.executar(comando);
        }

        if ("clientes".equals(inicioComando)) {
            return this.clienteController.executar(comando);
        }

        if ("contas".equals(inicioComando)) {
            return this.contaController.executar(comando);
        }

        if ("faturas".equals(inicioComando)) {
            return this.faturaController.executar(comando);
        }

        return "operação inválida";
    }
}
