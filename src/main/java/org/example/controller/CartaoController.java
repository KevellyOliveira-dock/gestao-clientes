package org.example.controller;

public class CartaoController implements Controller {

    public String executar(String comando) {
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

            default:
                return "operação inválida";
        }
    }
}
