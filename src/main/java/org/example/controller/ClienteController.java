package org.example.controller;

public class ClienteController {

    public String executar(String comando) {
        switch (comando) {
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

            default:
                return "operação inválida";
        }
    }
}
