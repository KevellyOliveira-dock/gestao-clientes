package org.example.controller;

public class ContasController implements Controller {

    public String executar(String comando) {
        switch (comando) {
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
