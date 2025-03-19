package org.example.controller;

public class FrontController {
    private final CartoesController cartoesController;
    private final ClienteController clienteController;
    private final ContasController contasController;
    private final FaturasController faturasController;

    public FrontController() {
        this.cartoesController = new CartoesController();
        this.clienteController = new ClienteController();
        this.contasController = new ContasController();
        this.faturasController = new FaturasController();
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
