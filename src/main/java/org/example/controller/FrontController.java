package org.example.controller;

public class FrontController {
    private final FaturasController faturasController;

    public FrontController() {
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

        if ("faturas".equals(inicioComando)) {
            return this.faturasController.executar(comando);
        }

        switch (comando) {
            case "cartoes":
                return """
                        -------------------------------
                        | Bloquear {número do cartao} |
                        | Cadastrar                   |
                        -------------------------------""";

            case "cartoes bloquear":
                return "não implementado";

            case "cartoes cadastrar":
                return "não implementado";

            case "clientes":
                return """
                        ------------------------------
                        | atualizar {cpf do cliente} |
                        | cadastrar                  |
                        | desativar {cpf do cliente} |
                        | pesquisar                  |
                        ------------------------------""";

            case "clientes atualizar":
                return "não implementado";

            case "clientes cadastrar":
                return "não implementado";

            case "clientes desativar":
                return "não implementado";

            case "clientes pesquisar":
                return """
                        --------------------------
                        | cpf {cpf do cliente}   |
                        | nome {nome do cliente} |
                        -------------------------""";

            case "clientes pesquisar cpf":
                return "não implementado";

            case "clientes pesquisar nome":
                return "não implementado";

            case "contas":
                return """
                        -------------------------------
                        | cadastrar                   |
                        | desativar {numero da conta} |
                        | extrato {numero da conta}   |
                        | pesquisar                   |
                        -------------------------------""";

            case "contas cadastrar":
                return "não implementado";

            case "contas desativar":
                return "não implementado";

            case "contas extrato":
                return "não implementado";

            case "contas pesquisar":
                return """
                        ----------------------------------
                        | cpf-titular {cpf do cliente}   |
                        | nome-titular {nome do cliente} |
                        | numero {número da conta}       |
                        ----------------------------------""";

            case "contas pesquisar cpf-titular":
                return "não implementado";

            case "contas pesquisar nome-titular":
                return "não implementado";

            case "contas pesquisar numero":
                return "não implementado";

            default:
                return "operação inválida";
        }
    }
}
