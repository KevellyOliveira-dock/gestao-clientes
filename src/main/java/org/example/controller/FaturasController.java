package org.example.controller;

public class FaturasController {

    public String executar(String comando) {

        switch (comando) {
            case "faturas":
                return """
                        -----------------------------
                        | fechar {número do cartão} |
                        -----------------------------""";

            case "faturas fechar":
                return "não implementado";

            default:
                return "operação inválida";
        }
    }
}
