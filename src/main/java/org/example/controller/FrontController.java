package org.example.controller;

public class FrontController {

    public String executar(String comando) {

        if (!comando.isEmpty()) {
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

                case "faturas":
                    FaturasController faturas = new FaturasController();
                    return faturas.executar(comando);

                case "faturas fechar":
                    FaturasController faturasFechar = new FaturasController();
                    return faturasFechar.executar(comando);

                default:
                    return "operação inválida";
            }
        } else {
            return """
                    ------------
                    | cartoes  |
                    | clientes |
                    | contas   |
                    | faturas  |
                    ------------""";
        }
    }
}
